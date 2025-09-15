package com.example.careloop;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;

import java.io.InputStream;

public class UploadReportActivity extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;

    private ClipDrawable clipDrawable;
    private ImageView ivProgress;
    private boolean isUploading = false;

    private Uri selectedPdfUri;
    private boolean extractionDone = false;
    private boolean animationDone = false;
    private String extractedText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        PDFBoxResourceLoader.init(getApplicationContext());

        ivProgress = findViewById(R.id.ivProgress);
        Button btnUpload = findViewById(R.id.btnUpload);

        Drawable drawable = ivProgress.getDrawable();
        if (drawable instanceof ClipDrawable) {
            clipDrawable = (ClipDrawable) drawable;
        }

        btnUpload.setOnClickListener(v -> openFilePicker());
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedPdfUri = data.getData();
            if (selectedPdfUri != null) {
                // Start silhouette animation
                startFillingAnimation();

                // Start PDF extraction in background
                new Thread(() -> {
                    extractedText = extractTextFromPdf(selectedPdfUri);
                    SharedPrefHelper.savePdfText(UploadReportActivity.this, extractedText);
                    SharedPrefHelper.savePdfUri(UploadReportActivity.this, selectedPdfUri.toString());
                    extractionDone = true;

                    // if animation already finished, proceed to CategoryActivity
                    runOnUiThread(this::checkCompletion);
                }).start();
            }
        }
    }

    private void startFillingAnimation() {
        if (isUploading) return;
        isUploading = true;

        ValueAnimator animator = ValueAnimator.ofInt(0, 10000);
        animator.setDuration(5000); // 5 seconds animation
        animator.addUpdateListener(animation -> {
            int level = (int) animation.getAnimatedValue();
            clipDrawable.setLevel(level);

            // Stop animation if internet lost
            if (!isInternetAvailable()) {
                animator.cancel();
                showErrorSilhouette();
            }
        });

        animator.addUpdateListener(animation -> {
            int level = (int) animation.getAnimatedValue();
            clipDrawable.setLevel(level);

            // Stop animation if internet lost
            if (!isInternetAvailable()) {
                animator.cancel();
                showErrorSilhouette();
            }

            // When animation reaches end, mark completion
            if (level >= 10000) {
                animationDone = true;
                checkCompletion();
            }
        });

        animator.start();
    }

    private void checkCompletion() {
        if (animationDone && extractionDone) {
            showSuccessSilhouette();
            startActivity(new Intent(UploadReportActivity.this, ReportsActivity.class));
            finish();
        }
    }

    private void showErrorSilhouette() {
        ivProgress.setImageResource(R.drawable.error); // red silhouette
        Toast.makeText(this, "Upload failed! Internet lost.", Toast.LENGTH_SHORT).show();
        isUploading = false;
    }

    private void showSuccessSilhouette() {
        ivProgress.setImageResource(R.drawable.filled); // green silhouette
        Toast.makeText(this, "PDF processed successfully!", Toast.LENGTH_SHORT).show();
        isUploading = false;
        startActivity(new Intent(UploadReportActivity.this, ReportsActivity.class));
        finish();
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
    }

    private String extractTextFromPdf(Uri pdfUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(pdfUri);
            PDDocument document = PDDocument.load(inputStream);

            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            document.close();
            inputStream.close();

            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error extracting text: " + e.getMessage();
        }
    }
}
