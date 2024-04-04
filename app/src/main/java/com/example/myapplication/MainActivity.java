package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapter.TopicAdapter;
import com.example.myapplication.data.TopicsDB;
import com.example.myapplication.model.Topic;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView topicListView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TopicsDB topicsDB = TopicsDB.INSTANCE;
        topicsDB.initialize(getApplicationContext());

        topicListView = findViewById(R.id.topicListView);

        List<Topic> topics = topicsDB.getTopics();

        TopicAdapter adapter = new TopicAdapter(this, topics);
        topicListView.setAdapter(adapter);
    }
}
