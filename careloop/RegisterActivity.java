package com.example.careloop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etFirstName, etLastName, etDob, etPhone, etEmail, etPassword, etConfirmPassword;
    AutoCompleteTextView etGender;
    Button btnRegister;
    TextView tvPasswordError, tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize fields
        etUsername = findViewById(R.id.etUsername);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etDob = findViewById(R.id.etDOB);
        etGender = findViewById(R.id.etGender);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnSignUp);
        tvPasswordError = findViewById(R.id.tvPasswordError);
        tvLogin = findViewById(R.id.tvLogin);

        // Gender dropdown
        String[] genders = {"Male", "Female", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genders);
        etGender.setAdapter(adapter);

        // Date picker for DOB
        etDob.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            new android.app.DatePickerDialog(RegisterActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) ->
                            etDob.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear),
                    year, month, day).show();
        });

        // Register button click
        btnRegister.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String dob = etDob.getText().toString().trim();
            String gender = etGender.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            // -------------------- Validation --------------------
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Username & Password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 7) {
                tvPasswordError.setVisibility(TextView.VISIBLE);
                tvPasswordError.setText("Password must be at least 7 characters!");
                return;
            } else {
                tvPasswordError.setVisibility(TextView.GONE);
            }

            if (!password.equals(confirmPassword)) {
                tvPasswordError.setVisibility(TextView.VISIBLE);
                tvPasswordError.setText("Passwords do not match!");
                return;
            }

            if (!phone.matches("\\d{10}")) {
                Toast.makeText(this, "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            // -----------------------------------------------------

            // Save all data in SharedPreferences
            SharedPreferences sharedPref = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putString("first_name", firstName);
            editor.putString("last_name", lastName);
            editor.putString("dob", dob);
            editor.putString("gender", gender);
            editor.putString("phone", phone);
            editor.putString("email", email);
            editor.apply();

            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();

            // Go to Dashboard
            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
            intent.putExtra("FIRST_NAME", firstName);
            intent.putExtra("LAST_NAME", lastName);
            intent.putExtra("DOB", dob);
            intent.putExtra("GENDER", gender);
            startActivity(intent);
            finish();
        });

        // Redirect to login if already have account
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}
