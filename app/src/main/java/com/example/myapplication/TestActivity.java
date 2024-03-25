package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.QuestionAdapter;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Test;

import java.util.List;

public class TestActivity extends AppCompatActivity {
    private RecyclerView recyclerViewQuestions;
    private Button finishButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerViewQuestions = findViewById(R.id.recyclerViewQuestions);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));

        Test test = (Test) getIntent().getSerializableExtra("test");
        if (test != null) {
            QuestionAdapter adapter = new QuestionAdapter(getApplicationContext(), test);
            recyclerViewQuestions.setAdapter(adapter);
        }

        finishButton = findViewById(R.id.finishTestButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allRadioButtonsSelected(test)) {
                    showTestResults(test);
                } else {
                    Toast.makeText(getApplicationContext(), "Ответьте на все вопросы!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean allRadioButtonsSelected(Test test) {
        List<Question> questions = test.getQuestions();
        for (Question question : questions) {
            if (question.getIdAnswer() == -1) {
                return false;
            }
        }
        return true;
    }

    private void showTestResults(Test test) {
        Intent intent = new Intent(TestActivity.this, TestResultActivity.class);
        intent.putExtra("test", test);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
