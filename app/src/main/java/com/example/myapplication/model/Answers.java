package com.example.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class Answers implements Serializable {
    private int id;
    private int idTest;
    private List<Answer> answers;

    public Answers(int id, int idTest, List<Answer> answers) {
        this.id = id;
        this.idTest = idTest;
        this.answers = answers;
    }

    public int getIdTest() {
        return idTest;
    }

    public int getId() {
        return id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

}
