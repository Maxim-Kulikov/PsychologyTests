package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Question;
import com.example.myapplication.model.Test;
import com.example.myapplication.service.ResultCounter;

import java.util.List;
import java.util.stream.Collectors;

public class TestResultActivity extends AppCompatActivity {

    private ResultCounter resultCounter = ResultCounter.INSTANCE;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        TextView resultText = findViewById(R.id.resultText);
        Test test = (Test) getIntent().getSerializableExtra("test");
        resultText.setText(resultCounter.getResult(test));

        Button finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAnswers(test);
                finish();
            }
        });

    }

    private void clearAnswers(Test test) {
        List<Question> questions = test.getQuestions();
        for (Question question : questions) {
            question.setIdAnswer(-1);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}