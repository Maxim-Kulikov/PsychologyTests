package com.example.myapplication.data;

import android.content.Context;

import com.example.myapplication.model.Topic;
import com.example.myapplication.util.TestParser;

import java.util.List;

public enum TopicsDB {
    INSTANCE;

    private List<Topic> topics;

    public void initialize(Context context) {
        topics = TestParser.parseTopics(context);
    }

    public List<Topic> getTopics() {
        return topics;
    }
}
