package com.example.careloop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MedicalFormActivity extends AppCompatActivity {

    private EditText etName, etPhone, etBloodGroup, etDietHabits, etGeneticHistory;
    private Spinner spinnerYesNo1, spinnerYesNo2;
    private MaterialButton btnSave;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "EmergencyPrefs";

    private static final String KEY_SAVED = "isEmergencySaved";
    private static final String KEY_NAME = "emergencyName";
    private static final String KEY_PHONE = "emergencyPhone";
    private static final String KEY_BLOOD = "bloodGroup";
    private static final String KEY_DIET = "dietHabits";
    private static final String KEY_GENETIC = "geneticHistory";
    private static final String KEY_SMOKE = "smokeAnswer";
    private static final String KEY_ALCOHOL = "alcoholAnswer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_form);

        // Initialize views
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etBloodGroup = findViewById(R.id.etBloodGroup);
        etDietHabits = findViewById(R.id.etDietHabits);
        etGeneticHistory = findViewById(R.id.etGeneticHistory);

        spinnerYesNo1 = findViewById(R.id.spinnerYesNo1);
        spinnerYesNo2 = findViewById(R.id.spinnerYesNo2);

        btnSave = findViewById(R.id.btnSave);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Populate Yes/No spinners
        String[] yesNoOptions = {"Yes", "No"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yesNoOptions);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYesNo1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yesNoOptions);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYesNo2.setAdapter(adapter2);

        // Load saved data if present
        loadEmergencyData();

        // Back button
        findViewById(R.id.btnClose).setOnClickListener(v -> finish());

        // Save button click
        btnSave.setOnClickListener(v -> showWarningDialog());
    }

    private void loadEmergencyData() {
        boolean isSaved = sharedPreferences.getBoolean(KEY_SAVED, false);
        if (isSaved) {
            etName.setText(sharedPreferences.getString(KEY_NAME, ""));
            etPhone.setText(sharedPreferences.getString(KEY_PHONE, ""));
            etBloodGroup.setText(sharedPreferences.getString(KEY_BLOOD, ""));
            etDietHabits.setText(sharedPreferences.getString(KEY_DIET, ""));
            etGeneticHistory.setText(sharedPreferences.getString(KEY_GENETIC, ""));
            spinnerYesNo1.setSelection(sharedPreferences.getInt(KEY_SMOKE, 0));
            spinnerYesNo2.setSelection(sharedPreferences.getInt(KEY_ALCOHOL, 0));

            lockAllFields();
        }
    }

    private void showWarningDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Fill carefully. Once saved, Emergency Contact cannot be changed.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> saveEmergencyData());
        builder.show();
    }

    private void saveEmergencyData() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill Name and Phone fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_SAVED, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_BLOOD, etBloodGroup.getText().toString().trim());
        editor.putString(KEY_DIET, etDietHabits.getText().toString().trim());
        editor.putString(KEY_GENETIC, etGeneticHistory.getText().toString().trim());
        editor.putInt(KEY_SMOKE, spinnerYesNo1.getSelectedItemPosition());
        editor.putInt(KEY_ALCOHOL, spinnerYesNo2.getSelectedItemPosition());
        editor.apply();

        lockAllFields();
        Toast.makeText(this, "Emergency Contact saved successfully", Toast.LENGTH_LONG).show();
    }

    private void lockAllFields() {
        etName.setEnabled(false);
        etPhone.setEnabled(false);
        etBloodGroup.setEnabled(false);
        etDietHabits.setEnabled(false);
        etGeneticHistory.setEnabled(false);
        spinnerYesNo1.setEnabled(false);
        spinnerYesNo2.setEnabled(false);
        btnSave.setVisibility(View.GONE);
    }
}
