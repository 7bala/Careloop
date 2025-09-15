package com.example.careloop;

import java.util.*;

public class QAData {

    public static Map<String, List<Question>> categoryMap = new HashMap<>();

    static {
        // ================= Account Information =================
        List<Question> accountQuestions = new ArrayList<>();

        // Q1: Profile
        accountQuestions.add(new Question("Profile", Arrays.asList(
                new Answer("Go to Profile → Edit Profile → Update your details and save.",
                        "Here, you can modify information like your name, contact number, or address. Once you make the changes, make sure to tap the Save button to ensure your updated information is recorded in the system."),
                new Answer("Tap your profile picture → Personal Information → Make changes and confirm.",
                        "This section provides an easy way to review your personal details. After updating, double-check all fields and press Confirm to save you edits."),
                new Answer("Open Profile settings → Select “Update Info” → Edit the fields you want.",
                        "You can update multiple details at once, including email and phone number. The app will notify you when changes are saved.")
        )));

        // Q2: Contact
        accountQuestions.add(new Question("Contact", Arrays.asList(
                new Answer("Go to Contact → Edit Contact → Update your phone number or email.",
                        "Modify outdated info and tap Save to securely store your new contact details."),
                new Answer("Tap your profile → Contact Information → Make necessary updates.",
                        "Review and confirm updates to keep your contact info accurate."),
                new Answer("Open Settings → Contact Settings → Update Info.",
                        "Change multiple contact fields like secondary email or emergency contacts. Confirmation will appear after saving.")
        )));

        // Q3: Password
        accountQuestions.add(new Question("Password", Arrays.asList(
                new Answer("Go to Settings → Password → Reset Password.",
                        "Enter your current password, then type your new one twice to confirm."),
                new Answer("Tap your profile → Account Settings → Change Password.",
                        "Enter your existing password, create a new one, then save."),
                new Answer("Open Login Screen → Forgot Password → Follow Reset Instructions.",
                        "Use the email/SMS reset link to safely regain access.")
        )));

        categoryMap.put("Account Information", accountQuestions);

        // ================= Add New Person =================
        List<Question> addPersonQuestions = new ArrayList<>();

        addPersonQuestions.add(new Question("Add Person", Arrays.asList(
                new Answer("Go to Add Person → Fill details → Save.",
                        "Provide essential info like name, contact, DOB, relationship."),
                new Answer("Tap Profile → Add Person → Enter Details.",
                        "Include optional info like email or medical details."),
                new Answer("Open Dashboard → Add Person → Complete the Form.",
                        "Add multiple people one by one, confirm when done.")
        )));

        addPersonQuestions.add(new Question("Details", Arrays.asList(
                new Answer("Provide basic info like name, DOB, gender.",
                        "These are essential for identifying the person correctly."),
                new Answer("Contact info like phone, email, address is required.",
                        "Helps with communication and record accuracy."),
                new Answer("Optionally add relationship, medical info, emergency contacts.",
                        "This makes the service more personalized and useful.")
        )));

        addPersonQuestions.add(new Question("Edit Person", Arrays.asList(
                new Answer("Go to Person List → Select person → Edit.",
                        "Update name, contact, or address and Save."),
                new Answer("Tap Profile → Manage People → Choose a Person → Update Details.",
                        "Modify both basic and advanced info, then Confirm."),
                new Answer("Open Dashboard → People → Edit.",
                        "Make multiple updates including medical/emergency contacts.")
        )));

        categoryMap.put("Add Person", addPersonQuestions);
        // Report
        List<Question> reportquestion = new ArrayList<>();

        reportquestion.add(new Question("View", Arrays.asList(
                new Answer("Go to Reports → Select a Report → View Details.",
                        "You can see all the relevant information such as test results, dates, and comments. Make sure to scroll through the report to review each section carefully."),
                new Answer("Tap Dashboard → Reports → Open a Report. ",
                        "This view provides a structured summary of the report, including any observations or alerts noted by the system or medical staff."),
                new Answer("Open Profile → Reports → Choose Report → Details.",
                        "Here, you can also access additional information such as attached documents, images, or charts related to the report, giving you a comprehensive overview.")
        )));

        reportquestion.add(new Question("Download", Arrays.asList(
                new Answer("Go to Reports → Select a Report → Download/Export.",
                        "You can choose to download the report as a PDF or Excel file. Once the file is downloaded, it will be available in your device’s default download folder."),
                new Answer("Tap Dashboard → Reports → Open a Report → Export.",
                        "The app allows you to share or save the report securely. Make sure to select the correct format before exporting to ensure compatibility with other applications."),
                new Answer("Open Profile → Reports → Select the Report → Download.",
                        "After clicking download, the app will confirm that the report has been successfully saved. You can then access it anytime offline or share it with others if needed.")
        )));

        reportquestion.add(new Question("Summary", Arrays.asList(
                new Answer("Go to Reports → Select a Report → Summary.",
                        "This section provides a concise overview of the key findings, important dates, and any critical observations without having to read the entire report."),
                new Answer("Tap Dashboard → Reports → Open Report → View Summary.",
                        "The summary highlights essential details like test results trends, recommendations, and alerts, giving you a quick understanding of the report."),
                new Answer("Open Profile → Reports → Choose a Report → Summary.",
                        "Here, the app may also provide visual charts or highlights to make it easier to interpret the most important information at a glance..")
        )));

        categoryMap.put("Report", reportquestion);

        // ================= Health Trends =================
        List<Question> healthTrendsQuestions = new ArrayList<>();

        healthTrendsQuestions.add(new Question("View Trends", Arrays.asList(
                new Answer("Go to Health Trends → Select a Metric → View Trend.",
                        "You can track changes over time for key health indicators like blood pressure, sugar levels, or weight. The app displays this data in charts to help you easily spot patterns."),
                new Answer("Tap Dashboard → Health Trends → Choose a Parameter.",
                        "This view provides a clear timeline showing how the selected health metric has changed over days, weeks, or months, helping you monitor progress effectively."),
                new Answer("Open Profile → Health Trends → Select Trend.",
                        "You can also compare multiple metrics side by side and receive insights on improvements or areas that need attention, making it easier to manage your health proactively.")
        )));

        healthTrendsQuestions.add(new Question("Info", Arrays.asList(
                new Answer("Health trends show key metrics over time, such as blood pressure, heart rate, blood sugar levels, and weight.",
                        "These trends help you monitor your health progress and identify any patterns or changes."),
                new Answer("The app also provides visual charts and graphs to make it easier to understand how your health indicators fluctuate over days, weeks, or months.",
                        "You can quickly spot improvements or areas that may need attention."),
                new Answer("Additional information may include alerts or recommendations based on your trends.",
                        "For example, if a metric is consistently outside the normal range, the app may suggest lifestyle adjustments or prompt a medical consultation.")
        )));

        healthTrendsQuestions.add(new Question("Update", Arrays.asList(
                new Answer("Health trends are typically updated automatically whenever new data is recorded.",
                        "For example, if you log your blood pressure or weight, the app processes this information and refreshes the trends to reflect the latest measurements."),
                new Answer("Some metrics may be updated daily or in real-time, depending on how the app syncs with your devices or manual entries.",
                        "This ensures you always have the most current view of your health indicators."),
                new Answer("In certain cases, trends are updated weekly or after periodic reviews, especially if the data comes from external sources like lab reports or wearable devices.",
                        "The app will indicate the last update date so you know how recent the information is.")
        )));

        categoryMap.put("Health Trends", healthTrendsQuestions);

        // ================= Manage Alerts =================
        List<Question> manageAlertsQuestions = new ArrayList<>();

        manageAlertsQuestions.add(new Question("Daily Reminders", Arrays.asList(
                new Answer("Yes, enable Daily Reminders in the Notifications section.",
                        "You can choose which alerts you want to receive every day to stay on track with your tasks or health goals."),
                new Answer("Turn on “Daily Alerts” under your profile settings.",
                        "The app will send reminders automatically at the times you set, helping you remember important activities."),
                new Answer("Go to Settings → Reminders → Daily Reminders to schedule notifications.",
                        "You can customize the time, type of reminder, and repeat frequency so it fits your routine.")
        )));

        manageAlertsQuestions.add(new Question("Stop Notifications", Arrays.asList(
                new Answer("Use the “Do Not Disturb” mode in app settings.",
                        "This will temporarily pause all notifications until you turn the mode off, helping you avoid interruptions."),
                new Answer("Turn off notifications for specific activities in the Notifications menu.",
                        "This allows you to selectively pause alerts without affecting other important notifications."),
                new Answer("Pause all alerts from Settings → Notifications → Pause Notifications.",
                        "The app will indicate that notifications are paused and resume them automatically based on your settings.")
        )));

        categoryMap.put("Manage Alerts", manageAlertsQuestions);


    }
}
