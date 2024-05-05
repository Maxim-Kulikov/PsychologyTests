package com.example.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {
    private int id;
    private int idTopic;
    private String name;
    private String description;
    private String formula;
    private List<Question> questions;
    private Answers answers;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Answers getAnswers() {
        return answers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormula() {
        return formula;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setAnswers(Answers answers) {
        this.answers = answers;
    }

    public Test(int id, int idTopic, String name, String description, String formula, List<Question> questions, Answers answers) {
        this.id = id;
        this.idTopic = idTopic;
        this.name = name;
        this.description = description;
        this.formula = formula;
        this.questions = questions;
        this.answers = answers;
    }
}
