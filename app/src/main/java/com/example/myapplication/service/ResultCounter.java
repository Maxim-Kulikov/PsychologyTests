package com.example.myapplication.service;

import com.example.myapplication.model.Answer;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Result;
import com.example.myapplication.model.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public enum ResultCounter {
    INSTANCE;

    public Result getResult(Test test) {
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

    private Result countArgAndGetResult(Test test, List<Part> partList) {
        List<Question> questions = test.getQuestions();
        List<Answer> answers = test.getAnswers().getAnswers();


        String totalResult = getCommonResults(questions, answers).toString();

        for (Part part : partList) {
            String resultText = null;
            if ("%".equals(part.arg)) {
                resultText = getAnswerByStrategyOfPercent(part, questions, answers);
            } else if ("+".equals(part.arg)) {
                resultText = getAnswerByStrategyOfSum(part, questions, answers);
            } else {
                resultText = getAnswerByStrategyOfArgsCounting(part, questions, answers);
            }
            if (resultText != null) {
                return new Result(test.getDescription(), totalResult, resultText);
            }
        }

        return new Result();
    }

    private StringBuilder getCommonResults(List<Question> questions, List<Answer> answers) {
        StringBuilder result = new StringBuilder();
        Map<String, Integer> resultMap = new HashMap<>();
        answers.forEach(answer -> resultMap.put(answer.getAnswer(), 0));
        questions.forEach(question -> {
            Answer answer = answers.stream().filter(x -> x.getId() == question.getIdAnswer()).findFirst().get();
            resultMap.put(answer.getAnswer(), resultMap.get(answer.getAnswer()) + 1);
        });
        resultMap.forEach((key, value) -> result.append("Вы ответили ").append(key).append(" ").append(value).append(" раз\n"));
        return result;
    }

    private String getAnswerByStrategyOfPercent(Part part, List<Question> questions, List<Answer> answers) {
        long count = questions.stream().filter(question -> question.getIdAnswer() == 0).count();
        double countPercent = (count / (double) questions.size()) * 100;
        if ((part.expr.equals("<=") && countPercent <= part.value)
                || (part.expr.equals(">") && countPercent > part.value)
                || (part.expr.equals("<") && countPercent < part.value)
                || (part.expr.equals("==") && countPercent == part.value)
                || (part.expr.equals(">=") && countPercent >= part.value)) {
            return part.answer;
        }
        return null;
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
