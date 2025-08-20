package com.example.careloop;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class RelationProfileAdd extends AppCompatActivity {

    EditText editName, editDob, editEmail, editPhone;
    Spinner spinnerBlood;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Views
        editName = findViewById(R.id.editName);
        editDob = findViewById(R.id.editDob);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        spinnerBlood = findViewById(R.id.spinnerBlood);
        btnDelete = findViewById(R.id.btnDelete);

        // Setup Spinner values
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, bloodTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBlood.setAdapter(adapter);

        // Handle Delete Button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RelationProfileAdd.this,
                        "Account Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle DOB click (calendar can be added)
        editDob.setOnClickListener(v -> {
            Toast.makeText(RelationProfileAdd.this, "Open Date Picker", Toast.LENGTH_SHORT).show();
        });
    }
}
