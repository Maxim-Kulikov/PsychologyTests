package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapter.TestAdapter;
import com.example.myapplication.model.Test;
import com.example.myapplication.model.Topic;

import java.util.List;

public class TopicActivity extends AppCompatActivity {

    private ListView testListView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        testListView = findViewById(R.id.testListView);

        Topic topic = (Topic) getIntent().getSerializableExtra("topic");
        List<Test> tests = topic.getTestList();

        TestAdapter adapter = new TestAdapter(this, tests);
        testListView.setAdapter(adapter);
    }

}