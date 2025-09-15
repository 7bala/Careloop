package com.example.careloop;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {
    private static final String PREF_NAME = "Reports";

    // 🔹 Save PDF Uri
    public static void savePdfUri(Context context, String uri) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString("pdf_uri", uri).apply();
    }

    // 🔹 Get PDF Uri
    public static String getPdfUri(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString("pdf_uri", null);
    }

    // 🔹 Save Extracted PDF Text
    public static void savePdfText(Context context, String text) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString("pdf_text", text).apply();
    }

    // 🔹 Get Extracted PDF Text
    public static String getPdfText(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString("pdf_text", "");
    }
}
