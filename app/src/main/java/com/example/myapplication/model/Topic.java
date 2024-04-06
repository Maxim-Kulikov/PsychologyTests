package com.example.myapplication.model;

import java.io.Serializable;
import java.util.List;

public class Topic implements Serializable {
    private int id;
    private String name;
    private final List<Test> testList;

    public Topic(int id, String name, List<Test> testList) {
        this.id = id;
        this.name = name;
        this.testList = testList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Test> getTestList() {
        return testList;
    }
}
