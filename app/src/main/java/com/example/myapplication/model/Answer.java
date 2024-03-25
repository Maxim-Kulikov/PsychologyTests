package com.example.myapplication.model;

import java.io.Serializable;

public class Answer implements Serializable {
    private int id;
    private String answer;
    private int value;

    public Answer(int id, String answer, int value) {
        this.id = id;
        this.answer = answer;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public int getValue() {
        return value;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
