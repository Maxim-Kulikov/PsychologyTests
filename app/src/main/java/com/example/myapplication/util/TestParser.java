package com.example.myapplication.util;

import android.content.Context;

import com.example.myapplication.R;
import com.example.myapplication.model.Answer;
import com.example.myapplication.model.Answers;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Test;
import com.example.myapplication.model.Topic;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TestParser {

    public List<Topic> getTopics(Context context) {
        List<Answers> answersList = parseAllAnswers(context);
        List<Test> testList = parseAllTests(context, answersList);
        return parseTopics(context, testList);
    }

    private List<Topic> parseTopics(Context context, List<Test> testList) {
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
                        int id = jsonTopic.getInt("id");
                        String name = jsonTopic.getString("name");
                        Topic topic = new Topic(id, name, findTestListByIdTopic(testList, id));
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

    private List<Test> parseAllTests(Context context, List<Answers> answersList) {
        List<Test> tests = new ArrayList<>();

        try {
            Field[] fields = R.raw.class.getFields();
            for (Field field : fields) {
                if (field.getName().startsWith("test")) {
                    int resourceId = field.getInt(null);
                    InputStream inputStream = context.getResources().openRawResource(resourceId);
                    String jsonString = convertStreamToString(inputStream);
                    Test test = parseTest(jsonString, answersList);
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

    private Test parseTest(String jsonString, List<Answers> answersList) {
        try {
            JSONObject testObject = new JSONObject(jsonString);
            int id = testObject.getInt("id");
            int idTopic = testObject.getInt("idTopic");
            String name = testObject.getString("name");
            String description = testObject.getString("description");
            String formula = testObject.getString("formula");
            JSONArray questionsArray = testObject.getJSONArray("questions");
            List<Question> questions = new ArrayList<>();

            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject questionObject = questionsArray.getJSONObject(i);
                int idQuestion = questionObject.getInt("id");
                String question = questionObject.getString("question");
                questions.add(new Question(idQuestion, question, -1));
            }

            return new Test(id, idTopic, name, description, formula, questions, findAnswersByIdTest(answersList, id));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Answers> parseAllAnswers(Context context) {
        List<Answers> answersList = new ArrayList<>();

        try {
            Field[] fields = R.raw.class.getFields();
            for (Field field : fields) {
                if (field.getName().startsWith("answers")) {
                    int resourceId = field.getInt(null);
                    InputStream inputStream = context.getResources().openRawResource(resourceId);
                    String jsonString = convertStreamToString(inputStream);
                    Answers answers = parseAnswers(jsonString);
                    if (answers != null) {
                        answersList.add(answers);
                    }
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return answersList;
    }

    private Answers parseAnswers(String jsonString) {
        int id;
        int idTest;
        List<Answer> answers = new ArrayList<>();

        try {
            JSONObject answersJSONObject = new JSONObject(jsonString);
            id = answersJSONObject.getInt("id");
            idTest = answersJSONObject.getInt("idTest");
            JSONArray answersJSONArray = answersJSONObject.getJSONArray("answers");
            for (int i = 0; i < answersJSONArray.length(); i++) {
                JSONObject answerJSONObject = answersJSONArray.getJSONObject(i);
                Answer answer = new Answer(answerJSONObject.getInt("id"),
                        answerJSONObject.getString("answer"),
                        answerJSONObject.getInt("value"));
                answers.add(answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new Answers(id, idTest, answers);
    }

    private String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private List<Test> findTestListByIdTopic(List<Test> testList, int idTopic) {
        return testList.stream()
                .filter(test -> test.getIdTopic() == idTopic)
                .collect(Collectors.toList());
    }

    private Answers findAnswersByIdTest(List<Answers> answersList, int idTest) {
        return answersList.stream()
                .filter(answers -> answers.getIdTest() == idTest)
                .findFirst()
                .get();
    }
}
