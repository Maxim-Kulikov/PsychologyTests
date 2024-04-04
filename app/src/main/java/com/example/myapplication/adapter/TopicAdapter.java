package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.TopicActivity;
import com.example.myapplication.model.Topic;

import java.util.List;

public class TopicAdapter extends ArrayAdapter<Topic> {

    public TopicAdapter(Context context, List<Topic> topics) {
        super(context, 0, topics);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Topic topic = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.topic_item, parent, false);
        }
        Button topicButton = convertView.findViewById(R.id.topicButton);
        topicButton.setText(topic.getName());
        topicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TopicActivity.class);
                intent.removeExtra("topic");
                intent.putExtra("topic", topic);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
