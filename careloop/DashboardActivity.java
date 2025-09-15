package com.example.careloop;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    ImageButton hamburger;
    TextView tvName, tvAgeGender; // ✅ Added TextViews for name and age/gender

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ Initialize new TextViews
        tvName = findViewById(R.id.tvName); // Make sure to add in XML
        tvAgeGender = findViewById(R.id.tvAgeGender);

        // ✅ Get data from RegisterActivity
        // SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);

// Get data from intent first
        String firstName = getIntent().getStringExtra("FIRST_NAME");
        String lastName = getIntent().getStringExtra("LAST_NAME");
        String gender = getIntent().getStringExtra("GENDER");
        String dob = getIntent().getStringExtra("DOB");

// Fallback to SharedPreferences if intent values are null
        if (firstName == null) firstName = sharedPref.getString("first_name", "John");
        if (lastName == null) lastName = sharedPref.getString("last_name", "Doe");
        if (gender == null) gender = sharedPref.getString("gender", "Not Specified");
        if (dob == null) dob = sharedPref.getString("dob", "01/01/2000");

// Update UI
        tvName.setText(firstName + " " + lastName);
        tvAgeGender.setText(calculateAge(dob) + " years, " + gender);

        // Combine first and last name
        String fullName = firstName + " " + lastName;

// Save to SharedPreferences
        SharedPreferences prefs = getSharedPreferences("CareloopPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userFullName", fullName);
        editor.apply();


        hamburger = findViewById(R.id.btnSettings);
        hamburger.setOnClickListener(v -> showPopupMenu(v));

        // ✅ Upload Report Button
        Button uploadBtn = findViewById(R.id.btnUploadReport);
        if (uploadBtn != null) {
            uploadBtn.setOnClickListener(v -> {
                startActivity(new Intent(DashboardActivity.this, UploadReportActivity.class));
            });
        }

        ImageButton btnAddCare = findViewById(R.id.addCareButton);
        btnAddCare.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, AddCare.class));
            Toast.makeText(this, "Add new Care Network", Toast.LENGTH_SHORT).show();
        });

        CircleImageView profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(v -> {
            Intent intent1 = new Intent(DashboardActivity.this, EditProfileActivity.class);
            startActivity(intent1);
        });

        // ✅ Bottom Navigation
        bottomNav = findViewById(R.id.bottomNavigationView);

        // 🔹 Highlight "Home" by default since this is Dashboard
        setBoldMenuItem(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(DashboardActivity.this, "You Are In Home Screen", Toast.LENGTH_SHORT).show();
                    setBoldMenuItem(R.id.nav_home);
                    return true;
                case R.id.nav_reports:
                    setBoldMenuItem(R.id.nav_reports);
                    startActivity(new Intent(DashboardActivity.this, ReportsActivity.class));
                    return true;
                case R.id.nav_health:
                    setBoldMenuItem(R.id.nav_health);
                    startActivity(new Intent(DashboardActivity.this, UploadReportActivity.class));
                    return true;
                case R.id.nav_info:
                    setBoldMenuItem(R.id.nav_info);
                    startActivity(new Intent(DashboardActivity.this, InfoActivity.class));
                    return true;
                case R.id.nav_chat:
                    setBoldMenuItem(R.id.nav_chat);
                    startActivity(new Intent(DashboardActivity.this, ChatActivity.class));
                    return true;
            }
            return false;
        });

        // ✅ Inflate Reports in HorizontalScrollView
        LinearLayout reportsContainer = findViewById(R.id.recentReportsContainer);
        TextView recentReportsTitle = findViewById(R.id.recentReportsTitle);
        HorizontalScrollView recentReportsScroll = findViewById(R.id.recentReportsScroll);

        ArrayList<String> reports = getDummyReports();

        if (reports.isEmpty()) {
            recentReportsTitle.setVisibility(View.GONE);
            recentReportsScroll.setVisibility(View.GONE);
        } else {
            recentReportsTitle.setVisibility(View.VISIBLE);
            recentReportsScroll.setVisibility(View.VISIBLE);

            for (String report : reports) {
                View item = LayoutInflater.from(this).inflate(R.layout.item_report, reportsContainer, false);
                TextView reportName = item.findViewById(R.id.tvReportTitle);
                reportName.setText(report);
                reportsContainer.addView(item);
            }
        }

        // ✅ Inflate Care Network in HorizontalScrollView
        LinearLayout careContainer = findViewById(R.id.careNetworkContainer);
        for (String care : getDummyCare()) {
            View item = LayoutInflater.from(this).inflate(R.layout.item_care, careContainer, false);
            TextView careName = item.findViewById(R.id.tvRelation);
            careName.setText(care);
            careContainer.addView(item);
        }
    }

    // ✅ Popup menu
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(DashboardActivity.this, view);
        popup.getMenuInflater().inflate(R.menu.pop_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch(item.getItemId()) {
                case R.id.menu_profile:
                    setBoldMenuItem(R.id.menu_profile);
                    startActivity(new Intent(DashboardActivity.this, EditProfileActivity.class));
                    return true;
                case R.id.menu_logout:
                    Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();return true;
                case R.id.care:
                    setBoldMenuItem(R.id.care);
                    startActivity(new Intent(DashboardActivity.this, InfoActivity.class));
                    return true;
                case R.id.recent:
                    setBoldMenuItem(R.id.recent);
                    startActivity(new Intent(DashboardActivity.this, RecentActivity.class));
                    return true;
                case R.id.emergency:
                    setBoldMenuItem(R.id.emergency);
                    startActivity(new Intent(DashboardActivity.this, MedicalFormActivity.class));
                    return true;
            }
            return false;
        });

        popup.show();
    }

    // ✅ Bold BottomNav item
    private void setBoldMenuItem(int selectedItemId) {
        if (bottomNav == null) return;

        for (int i = 0; i < bottomNav.getMenu().size(); i++) {
            android.view.MenuItem menuItem = bottomNav.getMenu().getItem(i);
            SpannableString spanString = new SpannableString(menuItem.getTitle());

            if (menuItem.getItemId() == selectedItemId) {
                spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
            } else {
                spanString.setSpan(new StyleSpan(Typeface.NORMAL), 0, spanString.length(), 0);
            }
            menuItem.setTitle(spanString);
        }
    }

    // ✅ Age calculation
    private int calculateAge(String dobString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dob = sdf.parse(dobString);
            Calendar dobCalendar = Calendar.getInstance();
            dobCalendar.setTime(dob);

            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);

            if (today.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ✅ Dummy reports
    private ArrayList<String> getDummyReports() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Blood Test - Jan 2025");
        list.add("X-Ray - Feb 2025");
        list.add("ECG - Mar 2025");
        return list;
    }

    // ✅ Dummy care members
    private ArrayList<String> getDummyCare() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Father");
        list.add("Mother");
        list.add("Brother");
        return list;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "EmergencyReminder";
            String description = "Channel for Emergency Reminder";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("emergency_channel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void scheduleEmergencyReminder() {
        new android.os.Handler().postDelayed(() -> showNotification(), 30000); // 30 seconds
    }
    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "emergency_channel")
                .setSmallIcon(R.drawable.ic_notification) // your notification icon
                .setContentTitle("Emergency Reminder")
                .setContentText("Please check your emergency contact details!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

}

