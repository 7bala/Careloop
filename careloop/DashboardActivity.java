package com.example.careloop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.HorizontalScrollView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    ImageButton hamburger;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hamburger = findViewById(R.id.btnSettings);
        hamburger.setOnClickListener(v -> {
            showPopupMenu(v);
        });
// make sure your XML has this ID


        // ✅ Upload Report Button
        Button uploadBtn = findViewById(R.id.btnUploadReport);
        if (uploadBtn != null) {
            uploadBtn.setOnClickListener(v -> {
                startActivity(new Intent(DashboardActivity.this, UploadReportActivity.class));
            });
        }

        ImageButton  btnAddCare = findViewById(R.id.addCareButton);
        btnAddCare.setOnClickListener(v -> {
            Toast.makeText(this, "Add new Care Network", Toast.LENGTH_SHORT).show();
        });
        CircleImageView profileImage = findViewById(R.id.profileImage);

        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });


        // ✅ Bottom Navigation
        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(DashboardActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_reports:
                    startActivity(new Intent(DashboardActivity.this, ReportsActivity.class));
                    return true;
                case R.id.nav_health:
                    startActivity(new Intent(DashboardActivity.this, HealthActivity.class));
                    return true;
                case R.id.nav_info:
                    startActivity(new Intent(DashboardActivity.this, InfoActivity.class));
                    return true;
                case R.id.nav_chat:
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
            // Hide the whole Recent Reports section
            recentReportsTitle.setVisibility(View.GONE);
            recentReportsScroll.setVisibility(View.GONE);
        } else {
            // Show section
            recentReportsTitle.setVisibility(View.VISIBLE);
            recentReportsScroll.setVisibility(View.VISIBLE);

            // Add report items
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
            TextView careName = item.findViewById(R.id.tvRelation); // assuming you have this TextView in item_care.xml
            careName.setText(care);
            careContainer.addView(item);
        }
    }
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(DashboardActivity.this, view);
        popup.getMenuInflater().inflate(R.menu.pop_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch(item.getItemId()) {
                case R.id.menu_profile:
                    Toast.makeText(DashboardActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_settings:
                    Toast.makeText(DashboardActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_logout:
                    Toast.makeText(DashboardActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.care:
                    Toast.makeText(DashboardActivity.this, "My Care clicked", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.recent:
                    Toast.makeText(DashboardActivity.this, "Recent clicked", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.emergency:
                    Toast.makeText(DashboardActivity.this, "Emergency clicked", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        });

        popup.show();
    }

    // ✅ Dummy reports
    private ArrayList<String> getDummyReports() {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }

    // ✅ Dummy care members
    private ArrayList<String> getDummyCare() {
        ArrayList<String> list = new ArrayList<>();
        return list;
    }
}
