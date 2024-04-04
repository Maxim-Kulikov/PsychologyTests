package com.example.myapplication.util;

import android.content.Context;

import com.example.myapplication.R;
import com.example.myapplication.model.Answer;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Test;
import com.example.myapplication.model.Topic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestParser {
    public static List<Topic> parseTopics(Context context) {
        List<Test> tests = parseTests(context);
        List<Topic> topics = new ArrayList<>();

        try {
            Field[] fields = R.raw.class.getFields();
            for (Field field : fields) {
                if (field.getName().contains("topics")) {
                    int resourceId = field.getInt(null);
                    InputStream inputStream = context.getResources().openRawResource(resourceId);
                    String jsonString = convertStreamToString(inputStream);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray jsonArray = jsonObject.getJSONArray("topics");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonTopic = jsonArray.getJSONObject(i);
                        Topic topic = new Topic(jsonTopic.getInt("id"), jsonTopic.getString("name"));
                        tests.stream()
                                .filter(test -> test.getIdTopic() == topic.getId())
                                .forEach(test -> topic.getTestList().add(test));
                        topics.add(topic);
                    }
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topics;
    }

    private static List<Test> parseTests(Context context) {
        List<Test> tests = new ArrayList<>();

        try {
            Field[] fields = R.raw.class.getFields();
            for (Field field : fields) {
                if (field.getName().startsWith("test")) {
                    int resourceId = field.getInt(null);
                    InputStream inputStream = context.getResources().openRawResource(resourceId);
                    String jsonString = convertStreamToString(inputStream);
                    Test test = parseTest(jsonString, field.getName(), context);
                    if (test != null) {
                        tests.add(test);
                    }
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tests;
    }

    private static Test parseTest(String jsonString, String fileName, Context context) {
        try {
            JSONObject testObject = new JSONObject(jsonString);
            int id = testObject.getInt("id");
            int idTopic = testObject.getInt("idTopic");
            String name = testObject.getString("name");
            String formula = testObject.getString("formula");
            JSONArray questionsArray = testObject.getJSONArray("questions");
            List<Question> questions = new ArrayList<>();

            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject questionObject = questionsArray.getJSONObject(i);
                int idQuestion = questionObject.getInt("id");
                String question = questionObject.getString("question");
                questions.add(new Question(idQuestion, question, -1));
            }


            return new Test(id, idTopic, name, formula, questions, parseAnswers(fileName, context));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Answer> parseAnswers(String testsFileName, Context context) {
        List<Answer> answers = new ArrayList<>();

        try {
            Field[] fields = R.raw.class.getFields();
            String requiredFileName = "answers" + extractNumbers(testsFileName);
            for (Field field : fields) {
                if (field.getName().startsWith(requiredFileName)) {
                    int resourceId = field.getInt(null);
                    InputStream inputStream = context.getResources().openRawResource(resourceId);
                    String jsonString = convertStreamToString(inputStream);
                    answers = parseAnswers(jsonString);
                    inputStream.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return answers;
    }

    private static List<Answer> parseAnswers(String jsonString) throws JSONException {
        List<Answer> answers = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray answersArray = jsonObject.getJSONArray("answers");

        for (int i = 0; i < answersArray.length(); i++) {
            JSONObject answerObject = answersArray.getJSONObject(i);
            int idAnswer = answerObject.getInt("id");
            String answer = answerObject.getString("answer");
            int value = answerObject.getInt("value");

            answers.add(new Answer(idAnswer, answer, value));
        }

        return answers;
    }

    private static String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private static String extractNumbers(String filename) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filename.length(); i++) {
            char ch = filename.charAt(i);
            if (Character.isDigit(ch)) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
