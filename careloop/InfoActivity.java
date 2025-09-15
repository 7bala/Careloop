package com.example.careloop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        // Setup Toolbar with back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Call & Message Father
        setupContactActions(R.id.btnCallFather, R.id.btnMessageFather, "+911234567890");

        // Call & Message Mother
        setupContactActions(R.id.btnCallMother, R.id.btnMessageMother, "+911234567891");

        // Call & Message Brother
        setupContactActions(R.id.btnCallBrother, R.id.btnMessageBrother, "+919876543210");

        // Call & Message Sister
        setupContactActions(R.id.btnCallSister, R.id.btnMessageSister, "+918765432109");

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(InfoActivity.this, DashboardActivity.class));
                    return true;
                case R.id.nav_info:
                    Toast.makeText(InfoActivity.this, "You Are In Info Screen", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_health:
                    startActivity(new Intent(InfoActivity.this, UploadReportActivity.class));
                    return true;
                case R.id.nav_reports :
                    startActivity(new Intent( InfoActivity .this, ReportsActivity.class));
                    return true;
                case R.id.nav_chat:
                    startActivity(new Intent(InfoActivity.this, ChatActivity.class));
                    return true;
            }
            return false;
        });
    }

    // Helper method to handle Call and Message buttons
    private void setupContactActions(int callBtnId, int msgBtnId, String phoneNumber) {
        Button callBtn = findViewById(callBtnId);
        Button msgBtn = findViewById(msgBtnId);

        callBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        });

        msgBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
            startActivity(intent);
        });
    }
}