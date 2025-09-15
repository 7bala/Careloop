package com.example.careloop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> messages;
    private final OnOptionClick listener;

    interface OnOptionClick {
        void onClick(String option);
    }

    public ChatAdapter(List<ChatMessage> messages, OnOptionClick listener) {
        this.messages = messages;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage msg = messages.get(position);
        String sender = msg.getSender();
        if ("bot".equals(sender)) return 0;
        if ("user".equals(sender)) return 1;
        if ("questions".equals(sender)) return 5;   // <-- important: handle "questions"
        if ("menu".equals(sender)) return 2;        // explicit for quick menu
        return 2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_left, parent, false);
            return new BotViewHolder(v);
        } else if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_right, parent, false);
            return new UserViewHolder(v);
        } else if (viewType == 5) { // questions view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_questions, parent, false);
            return new QuestionsViewHolder(v);
        } else { // quick menu
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_quick_menu, parent, false);
            return new MenuViewHolder(v, listener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage msg = messages.get(position);

        if (holder instanceof BotViewHolder) {
            ((BotViewHolder) holder).tvMessage.setText(msg.getMessage());
        } else if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).tvMessage.setText(msg.getMessage());
        } else if (holder instanceof QuestionsViewHolder) {
            // message format expected: "question_menu:<CategoryName>"
            String payload = msg.getMessage();
            String prefix = "question_menu:";
            String category = payload;
            if (payload != null && payload.startsWith(prefix)) {
                category = payload.substring(prefix.length());
            }

            List<Question> qs = QAData.categoryMap.get(category);
            QuestionsViewHolder qvh = (QuestionsViewHolder) holder;

            if (qs != null && !qs.isEmpty()) {
                qvh.q1.setText(qs.size() > 0 ? qs.get(0).text : "");
                qvh.q2.setText(qs.size() > 1 ? qs.get(1).text : "");
                qvh.q3.setText(qs.size() > 2 ? qs.get(2).text : "");

                if (qs.size() > 0) qvh.q1.setOnClickListener(v -> listener.onClick(qvh.q1.getText().toString()));
                if (qs.size() > 1) qvh.q2.setOnClickListener(v -> listener.onClick(qvh.q2.getText().toString()));
                if (qs.size() > 2) qvh.q3.setOnClickListener(v -> listener.onClick(qvh.q3.getText().toString()));
            } else {
                qvh.q1.setText("No questions found");
                qvh.q2.setText("");
                qvh.q3.setText("");
                qvh.q1.setOnClickListener(null);
                qvh.q2.setOnClickListener(null);
                qvh.q3.setOnClickListener(null);
            }

            // exit always sends "Exit"
            qvh.exit.setOnClickListener(v -> listener.onClick("Exit"));
        }
        // MenuViewHolder is static and has its own click wiring in constructor
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;
        BotViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;
        UserViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        MenuViewHolder(View itemView, OnOptionClick listener) {
            super(itemView);
            // Make sure these strings match the keys you put in QAData.categoryMap
            itemView.findViewById(R.id.tvAccount).setOnClickListener(v -> listener.onClick("Account Information"));
            itemView.findViewById(R.id.tvAddPerson).setOnClickListener(v -> listener.onClick("Add Person"));
            itemView.findViewById(R.id.tvReport).setOnClickListener(v -> listener.onClick("Report")); // match QAData key "Report"
            itemView.findViewById(R.id.tvTrends).setOnClickListener(v -> listener.onClick("Health Trends"));
            itemView.findViewById(R.id.tvAlerts).setOnClickListener(v -> listener.onClick("Manage Alerts"));
            itemView.findViewById(R.id.tvExit).setOnClickListener(v -> listener.onClick("Exit"));
        }
    }

    static class QuestionsViewHolder extends RecyclerView.ViewHolder {
        TextView q1, q2, q3, exit;
        QuestionsViewHolder(View itemView) {
            super(itemView);
            q1 = itemView.findViewById(R.id.tvQ1);
            q2 = itemView.findViewById(R.id.tvQ2);
            q3 = itemView.findViewById(R.id.tvQ3);
            exit = itemView.findViewById(R.id.tvExit);
        }
    }
}
