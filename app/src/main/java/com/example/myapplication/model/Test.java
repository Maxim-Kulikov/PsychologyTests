package com.example.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {
    private int id;
    private String name;
    private List<Question> questions;
    private List<Answer> answers;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Test(int id, String name, List<Question> questions, List<Answer> answers) {
        this.id = id;
        this.name = name;
        this.questions = questions;
        this.answers = answers;
    }
}
