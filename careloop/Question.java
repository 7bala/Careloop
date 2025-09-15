package com.example.careloop;

import java.util.List;

public class Question {
    String text;
    List<Answer> answers;

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }
}
