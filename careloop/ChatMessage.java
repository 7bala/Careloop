package com.example.careloop;

public class ChatMessage {
    private final String message;
    private final String sender; // "bot", "user", "menu"

    public ChatMessage(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }
}
