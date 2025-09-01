package com.example.careloop;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GettingStarted extends AppCompatActivity {

    ConstraintLayout btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started); // linking with getting_started.xml

        // "Get Started" button (ConstraintLayout)
        btnGetStarted = findViewById(R.id.getStartedButton);

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to LoginActivity
                Intent intent = new Intent(GettingStarted.this, LoginActivity.class);
                startActivity(intent);
                finish(); // close getting started screen
            }
        });
    }
}
