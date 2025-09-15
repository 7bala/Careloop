package com.example.careloop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvLoginError, tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvLoginError = findViewById(R.id.tvLoginError);
        tvSignup = findViewById(R.id.tvSignup);

        btnLogin.setOnClickListener(v -> {
            String inputUsername = etUsername.getText().toString().trim();
            String inputPassword = etPassword.getText().toString().trim();

            SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            String savedUsername = sharedPref.getString("username", "");
            String savedPassword = sharedPref.getString("password", "");

            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                tvLoginError.setText("Please enter username and password");
                tvLoginError.setVisibility(TextView.VISIBLE);
            } else if (inputUsername.equals(savedUsername) && inputPassword.equals(savedPassword)) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            } else {
                tvLoginError.setText("Invalid username or password");
                tvLoginError.setVisibility(TextView.VISIBLE);
            }
        });

        tvSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }
}
