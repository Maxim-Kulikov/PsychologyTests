package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.TestActivity;
import com.example.myapplication.model.Test;

import java.util.List;

public class TestAdapter extends ArrayAdapter<Test> {

    public TestAdapter(Context context, List<Test> tests) {
        super(context, 0, tests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Test test = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.test_item, parent, false);
        }
        Button testButton = convertView.findViewById(R.id.testButton);
        testButton.setText(test.getName());
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TestActivity.class);
                intent.removeExtra("test");
                intent.putExtra("test", test);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
