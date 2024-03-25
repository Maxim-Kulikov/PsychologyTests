package com.example.myapplication.model;

import java.io.Serializable;

public class Question implements Serializable {
    private int id;
    private String question;
    private int idAnswer;

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public Question(int id, String question, int idAnswer) {
        this.id = id;
        this.question = question;
        this.idAnswer = idAnswer;
    }
}
