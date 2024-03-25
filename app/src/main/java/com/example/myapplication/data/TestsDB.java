package com.example.myapplication.data;

import android.content.Context;

import com.example.myapplication.model.Test;
import com.example.myapplication.util.TestParser;

import java.util.List;

public enum TestsDB {
    INSTANCE;

    private List<Test> tests;

    public void initialize(Context context) {
        tests = TestParser.parseTests(context);
    }

    public List<Test> getTests() {
        return tests;
    }
}
