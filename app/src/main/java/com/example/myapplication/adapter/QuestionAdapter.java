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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    private Test test;
    private Context context;
    private Map<Integer, Integer> checkedIds;

    public QuestionAdapter(Context context, Test test) {
        this.test = test;
        this.context = context;
        this.checkedIds = new HashMap<>();
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

        List<Answer> answers = test.getAnswers().getAnswers();
        for (Answer answer : answers) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(answer.getAnswer());
            radioButton.setId(answer.getId());

            if (checkedIds.containsKey(idQuestion) && checkedIds.get(idQuestion) == answer.getId()) {
                radioButton.setChecked(true);
            } else {
                radioButton.setChecked(false);
            }

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkedIds.put(idQuestion, radioButton.getId());
                    test.getQuestions().get(idQuestion).setIdAnswer(answer.getId());
                    notifyDataSetChanged();
                }
            });

            holder.answerRadioGroup.addView(radioButton);
        }

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
