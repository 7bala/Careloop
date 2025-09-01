package com.example.careloop;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UploadReportActivity extends AppCompatActivity {

    private ClipDrawable clipDrawable;
    private ImageView ivProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        ivProgress = findViewById(R.id.ivProgress);
        Button btnUpload = findViewById(R.id.btnUpload);

        Drawable drawable = ivProgress.getDrawable();
        clipDrawable = (ClipDrawable) drawable;

        btnUpload.setOnClickListener(v -> {
            if (isInternetAvailable()) {
                startFillingAnimation();
            } else {
                showErrorSilhouette();
            }
        });
    }

    /** Progress bar filling animation */
    private void startFillingAnimation() {
        new Thread(() -> {
            for (int i = 0; i <= 10000; i += 200) { // 0 to 10000 = 0% to 100%
                final int level = i;
                runOnUiThread(() -> clipDrawable.setLevel(level));
                try {
                    Thread.sleep(100); // filling speed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 🔹 Simulate internet loss mid-upload
                if (!isInternetAvailable()) {
                    runOnUiThread(this::showErrorSilhouette);
                    return; // stop filling
                }
            }
        }).start();
    }

    /** Replace with full red silhouette */
    private void showErrorSilhouette() {
        ivProgress.setImageResource(R.drawable.error); // 🔴 red silhouette drawable
        Toast.makeText(this, "Upload failed! Internet lost.", Toast.LENGTH_SHORT).show();
    }

    /** Check if internet is available */
    private boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }
}
