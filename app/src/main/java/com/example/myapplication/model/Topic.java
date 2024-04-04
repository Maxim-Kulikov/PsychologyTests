package com.example.myapplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topic implements Serializable {
    private int id;
    private String name;
    private List<Test> testList;

    public Topic(int id, String name) {
        this.id = id;
        this.name = name;
        this.testList = new ArrayList<>();
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
