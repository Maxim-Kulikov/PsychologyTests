package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Answer;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.Test;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private Test test;
    private Context context;

    public QuestionAdapter(Context context, Test test) {
        this.test = test;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = test.getQuestions().get(position);
        final int idQuestion = position;
        holder.questionTextView.setText(question.getQuestion());

        holder.answerRadioGroup.removeAllViews(); // Очищаем список RadioButton

        List<Answer> answers = test.getAnswers();
        for (Answer answer : answers) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(answer.getAnswer());
            holder.answerRadioGroup.addView(radioButton);
        }

        holder.answerRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = group.findViewById(checkedId);
                int idx = group.indexOfChild(radioButton);
                test.getQuestions().get(idQuestion).setIdAnswer(idx);
            }
        });
    }


    @Override
    public int getItemCount() {
        return test.getQuestions().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        RadioGroup answerRadioGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerRadioGroup = itemView.findViewById(R.id.answerRadioGroup);
        }
    }

}
