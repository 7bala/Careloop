package com.example.careloop;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {

    EditText editFirstName, editLastName, editDob, editEmail, editPhone;
    Spinner spinnerBlood, spinnerGender;
    Button btnDelete, btnUpdate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Views
        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editName);
        editDob = findViewById(R.id.editDob);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerBlood = findViewById(R.id.spinnerBlood);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        // ----------------- Setup Spinners with custom color and hint -----------------
        String[] genderOptions = {"Select Gender", "Male", "Female", "Other"};
        String[] bloodTypes = {"Select Blood Type", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genderOptions) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setTextColor(Color.parseColor("#315f4f")); // color for selected item / hint
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setTextColor(Color.parseColor("#315f4f")); // color for dropdown items
                return view;
            }
        };
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);
        spinnerGender.setSelection(0); // show hint initially

        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bloodTypes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setTextColor(Color.parseColor("#315f4f"));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                view.setTextColor(Color.parseColor("#315f4f"));
                return view;
            }
        };
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBlood.setAdapter(bloodAdapter);
        spinnerBlood.setSelection(0); // show hint initially

        // ----------------- Delete / Update Buttons -----------------
        btnDelete.setOnClickListener(v ->
                Toast.makeText(EditProfileActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show()
        );

        btnUpdate.setOnClickListener(v ->
                Toast.makeText(EditProfileActivity.this, "Account Updated", Toast.LENGTH_SHORT).show()
        );

        // ----------------- Toolbar -----------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        // ----------------- DOB click (calendar) -----------------
        editDob.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    EditProfileActivity.this,
                    (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) ->
                            editDob.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear),
                    year, month, day);
            datePickerDialog.show();
        });
    }
}
