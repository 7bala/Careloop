package com.example.careloop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button startButton;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatList = new ArrayList<>();
    String currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        recyclerView = findViewById(R.id.recyclerView);
        startButton = findViewById(R.id.startButton);

        chatAdapter = new ChatAdapter(chatList, this::handleUserSelection);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Get name from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("CareloopPrefs", MODE_PRIVATE);
        String userName = prefs.getString("userFullName", "User"); // fallback to "User"


        // Intro bot messages
        addBotMessage("Hi! I’m Remora means a sleek take on remainder and records");
        addBotMessage("Hello " + userName + ",\nWarm greetings from Careloop! 👋\n" +
                "I’m here to help you manage your health better — storing medical records, reminders, or reports.\n" +
                "How can I assist you today?");

        // Start button
        startButton.setOnClickListener(v -> {
            startButton.setVisibility(View.GONE);
            addQuickMenu();
        });
    }

    private void addBotMessage(String msg) {
        chatList.add(new ChatMessage(msg, "bot"));
        chatAdapter.notifyItemInserted(chatList.size() - 1);
        recyclerView.scrollToPosition(chatList.size() - 1);
    }

    private void addUserMessage(String msg) {
        chatList.add(new ChatMessage(msg, "user"));
        chatAdapter.notifyItemInserted(chatList.size() - 1);
        recyclerView.scrollToPosition(chatList.size() - 1);
    }

    private void addQuickMenu() {
        chatList.add(new ChatMessage("quick_menu", "menu"));
        chatAdapter.notifyItemInserted(chatList.size() - 1);
        recyclerView.scrollToPosition(chatList.size() - 1);
    }

    private void handleUserSelection(String option) {
        addUserMessage(option);


        if (option.equals("Exit")) {
            addBotMessage("Chat ended. Press Start to begin again.");
            startButton.setVisibility(View.VISIBLE);
            currentCategory = null;
            return;
        }

        if (currentCategory == null) {
            // User picked category
            currentCategory = option;
            showQuestions(currentCategory);
        } else {
            // User picked a question inside category
            List<Question> qs = QAData.categoryMap.get(currentCategory);
            if (qs != null) {
                for (Question q : qs) {
                    if (q.text.equals(option)) {
                        // Pick random answer
                        List<Answer> ansList = new ArrayList<>(q.answers);
                        Collections.shuffle(ansList);
                        Answer chosen = ansList.get(0);

                        // Show step + explanation separately
                        addBotMessage(chosen.step);
                        addBotMessage(chosen.explanation);

                        // Show questions menu again
                        showQuestions(currentCategory);
                        return;
                    }
                }
            }
        }
    }

    private void showQuestions(String category) {
        List<Question> qs = QAData.categoryMap.get(category);
        if (qs != null) {
            chatList.add(new ChatMessage("question_menu:" + category, "questions"));
            chatAdapter.notifyItemInserted(chatList.size() - 1);
            recyclerView.scrollToPosition(chatList.size() - 1);
        }
    }

}
