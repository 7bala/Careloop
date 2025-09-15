package com.example.careloop;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        // Close button
        ImageView btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> finish());

        // Card 1 example
        TextView card1Title = findViewById(R.id.card1).findViewById(R.id.tvReportTitle);
        TextView card1Date = findViewById(R.id.card1).findViewById(R.id.tvDate);
        TextView card1Centre = findViewById(R.id.card1).findViewById(R.id.tvCentre);
        TextView card1Score = findViewById(R.id.card1).findViewById(R.id.tvScore);
        ImageView card1Download = findViewById(R.id.card1).findViewById(R.id.ivDownload);

        card1Title.setText("A Comprehensive Health Analysis Report");
        card1Date.setText("Date: 02 Dec 2022");
        card1Centre.setText("Inner Health Diagnostic Centre");
        card1Score.setText("Health Score: 63");

        card1Download.setOnClickListener(v ->
                Toast.makeText(this, "Download PDF for Card 1", Toast.LENGTH_SHORT).show()
        );

        // Card 2 example
        TextView card2Title = findViewById(R.id.card2).findViewById(R.id.tvReportTitle);
        TextView card2Date = findViewById(R.id.card2).findViewById(R.id.tvDate);
        TextView card2Centre = findViewById(R.id.card2).findViewById(R.id.tvCentre);
        TextView card2Score = findViewById(R.id.card2).findViewById(R.id.tvScore);
        ImageView card2Download = findViewById(R.id.card2).findViewById(R.id.ivDownload);

        card2Title.setText("Monthly Routine Checkup by Dr. Robert Dunes");
        card2Date.setText("Date: 22 Nov 2022");
        card2Centre.setText("Inner Health Diagnostic Centre");
        card2Score.setText("Health Score: 81");

        card2Download.setOnClickListener(v ->
                Toast.makeText(this, "Download PDF for Card 2", Toast.LENGTH_SHORT).show()
        );

        // Card 3 example
        TextView card3Title = findViewById(R.id.card3).findViewById(R.id.tvReportTitle);
        TextView card3Date = findViewById(R.id.card3).findViewById(R.id.tvDate);
        TextView card3Centre = findViewById(R.id.card3).findViewById(R.id.tvCentre);
        TextView card3Score = findViewById(R.id.card3).findViewById(R.id.tvScore);
        ImageView card3Download = findViewById(R.id.card3).findViewById(R.id.ivDownload);

        card3Title.setText("Weekly Fitness & Diet Review");
        card3Date.setText("Date: 15 Nov 2022");
        card3Centre.setText("Inner Health Diagnostic Centre");
        card3Score.setText("Health Score: 72");

        card3Download.setOnClickListener(v ->
                Toast.makeText(this, "Download PDF for Card 3", Toast.LENGTH_SHORT).show()
        );
    }
}
