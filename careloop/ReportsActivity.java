package com.example.careloop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReportsActivity extends AppCompatActivity {

    ImageButton btnBack, btnShare;

    // Categories
    String[] categories = {
            "Hemogram / CBC", "Lipid Profile", "Diabetes Panel",
            "Electrolytes Panel", "Thyroid Function", "Kidney Function",
            "Vitamin D", "Vitamin B12", "Liver Function",
            "Cholesterol Total", "Calcium Total", "Complete Hemogram"
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_report_main);  // ✅ fixed typo

        // Back & Share
        btnBack = findViewById(R.id.btn_back);
        btnShare = findViewById(R.id.btn_share);

        btnBack.setOnClickListener(v -> onBackPressed());

        btnShare.setOnClickListener(v ->
                Toast.makeText(ReportsActivity.this, "Share clicked!", Toast.LENGTH_SHORT).show()
        );

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(ReportsActivity.this, DashboardActivity.class));
                    return true;
                case R.id.nav_reports:
                    Toast.makeText(ReportsActivity.this, "You Are In Report Screen", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_health:
                    startActivity(new Intent(ReportsActivity.this, UploadReportActivity.class));
                    return true;
                case R.id.nav_info:
                    startActivity(new Intent(ReportsActivity.this, InfoActivity.class));
                    return true;
                case R.id.nav_chat:
                    startActivity(new Intent(ReportsActivity.this, ChatActivity.class));
                    return true;
            }
            return false;
        });

        // Category Cards
        int[] cardIds = {
                R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8,
                R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12
        };

        for (int i = 0; i < cardIds.length; i++) {
            int finalI = i;
            CardView card = findViewById(cardIds[i]);
            card.setOnClickListener(v -> {
                Intent intent = new Intent(ReportsActivity.this, DetailActivity.class);
                intent.putExtra("category", categories[finalI]);
                startActivity(intent);
            });
        }
    }
}
