package com.example.myapplication.model;

import java.io.Serializable;

public class Result implements Serializable {
    private String description;
    private String totalResult;

    public Result(String description, String totalResult, String resultText) {
        this.description = description;
        this.totalResult = totalResult;
        this.resultText = resultText;
    }

    private String resultText;

    public Result() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }
}
