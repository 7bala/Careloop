package com.example.careloop;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView tvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetails = findViewById(R.id.tvDetails);

        // Get which category button was pressed
        String category = getIntent().getStringExtra("category");
        setTitle(category);

        // Get the full PDF text
        String pdfText = SharedPrefHelper.getPdfText(this);

        // Filter only that category’s values
        String details = extractCategoryData(category, pdfText);

        tvDetails.setText(details);
    }

    private String extractCategoryData(String category, String pdfText) {
        if (pdfText == null || pdfText.isEmpty()) return "No data found in PDF.";

        switch (category) {
            case "Hemogram / CBC":
                return findLines(pdfText, new String[]{"Hemoglobin", "RBC", "WBC", "Platelet", "MCV", "MCH", "ESR"});
            case "Lipid Profile":
                return findLines(pdfText, new String[]{"Cholesterol", "Triglyceride", "HDL", "LDL", "VLDL"});
            case "Diabetes Panel":
                return findLines(pdfText, new String[]{"Fasting Blood Sugar", "HbA1c", "Mean Blood Glucose"});
            case "Electrolytes Panel":
                return findLines(pdfText, new String[]{"Sodium", "Potassium", "Chloride"});
            case "Thyroid Function":
                return findLines(pdfText, new String[]{"T3", "T4", "TSH"});
            case "Kidney Function":
                return findLines(pdfText, new String[]{"Creatinine", "Urea", "Blood Urea Nitrogen", "Uric Acid", "Microalbumin"});
            case "Vitamin D":
                return findLines(pdfText, new String[]{"Vitamin D"});
            case "Vitamin B12":
                return findLines(pdfText, new String[]{"Vitamin B12"});
            case "Liver Function":
                return findLines(pdfText, new String[]{"Total Protein", "Albumin", "Globulin", "Bilirubin", "SGPT", "SGOT"});
            case "Cholesterol Total":
                return findLines(pdfText, new String[]{"Cholesterol"});
            case "Calcium Total":
                return findLines(pdfText, new String[]{"Calcium"});
            case "Complete Hemogram":
                return findLines(pdfText, new String[]{"Hemogram"});
            default:
                return "No matching values found.";
        }
    }

    private String findLines(String text, String[] keywords) {
        StringBuilder result = new StringBuilder();
        String[] lines = text.split("\n");

        for (String line : lines) {
            for (String key : keywords) {
                if (line.toLowerCase().contains(key.toLowerCase())) {
                    result.append(line.trim()).append("\n");
                }
            }
        }

        if (result.length() == 0) return "No matching values found for this category.";
        return result.toString();
    }
}
