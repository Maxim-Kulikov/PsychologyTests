package com.example.myapplication.service;

import com.example.myapplication.model.Answer;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum ResultCounter {
    INSTANCE;

    public String getResult(Test test) {
        String formula = test.getFormula();
        List<Part> partList = getParts(formula);
        return countArgAndGetResult(test, partList);
    }

    private List<Part> getParts(String formula) {
        List<Part> partList = new ArrayList<>();

        String[] parts = formula.split("OR");
        for (String part : parts) {
            String[] args = part.split("@");
            Part part1 = new Part();
            part1.arg = args[0];
            part1.expr = args[1];
            part1.value = Integer.parseInt(args[2]);
            part1.answer = args[3];
            partList.add(part1);
        }

        return partList;
    }

    private String countArgAndGetResult(Test test, List<Part> partList) {
        List<Question> questions = test.getQuestions();
        List<Answer> answers = test.getAnswers().getAnswers();

        for (Part part : partList) {
            String answer = null;
            if ("+".equals(part.arg)) {
                answer = getAnswerByStrategyOfSum(part, questions, answers);
            } else {
                answer = getAnswerByStrategyOfArgsCounting(part, questions, answers);
            }
            if (answer != null) {
                return answer;
            }
        }

        return "error";
    }

    private String getAnswerByStrategyOfSum(Part part, List<Question> questions, List<Answer> answers) {
        int sum = questions.stream()
                .mapToInt(question -> answers.stream()
                        .filter(answer -> answer.getId() == question.getIdAnswer())
                        .findFirst()
                        .get()
                        .getValue())
                .sum();
        if ((part.expr.equals("<=") && sum <= part.value)
                || (part.expr.equals(">") && sum > part.value)
                || (part.expr.equals("<") && sum < part.value)
                || (part.expr.equals("==") && sum == part.value)
                || (part.expr.equals(">=") && sum >= part.value)) {
            return part.answer;
        }
        return null;
    }

    private String getAnswerByStrategyOfArgsCounting(Part part, List<Question> questions, List<Answer> answers) {
        long countArgs = questions.stream()
                .filter(question -> Objects.equals(answers.get(question.getIdAnswer()).getAnswer(), part.arg))
                .count();
        if ((part.expr.equals("<=") && countArgs <= part.value)
                || (part.expr.equals(">") && countArgs > part.value)
                || (part.expr.equals("<") && countArgs < part.value)
                || (part.expr.equals("==") && countArgs == part.value)
                || (part.expr.equals(">=") && countArgs >= part.value)) {
            return part.answer;
        }
        return null;
    }

    private class Part {
        String arg;
        String expr;
        int value;
        String answer;
    }

}
