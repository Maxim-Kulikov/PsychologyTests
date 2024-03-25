package com.example.myapplication;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.adapter.TestAdapter;
import com.example.myapplication.data.TestsDB;
import com.example.myapplication.model.Test;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView testListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestsDB testsDB = TestsDB.INSTANCE;
        testsDB.initialize(getApplicationContext());

        testListView = findViewById(R.id.testListView);

        List<Test> testList = testsDB.getTests();

        TestAdapter adapter = new TestAdapter(this, testList);
        testListView.setAdapter(adapter);
    }
}
