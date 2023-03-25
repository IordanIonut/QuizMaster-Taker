package com.example.quizmaster.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.Holder.QuestionHolder;
import com.example.quizmaster.Model.Questions;
import com.example.quizmaster.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {
    private Context content;
    private List<Questions> quizzersList;
    private List<Object[]> answerList;
    public QuestionAdapter(Context content, List<Questions> quizzersList, List<Object[]> answerList) {
        this.content = content;
        this.quizzersList = quizzersList;
        this.answerList = answerList;
    }
    @NonNull
    @NotNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new QuestionHolder(LayoutInflater.from(content).inflate(R.layout.item_quizs, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull QuestionHolder holder, int position) {
        holder.questionView.setText(quizzersList.get(position).getQuestion());
        holder.question1View.setText(quizzersList.get(position).getAnswer_1());
        holder.question2View.setText(quizzersList.get(position).getAnswer_2());
        holder.question3View.setText(quizzersList.get(position).getAnswer_3());
        holder.question4View.setText(quizzersList.get(position).getAnswer_4());
        for(Object[] a : answerList){
            if(quizzersList.get(position).getQuestion_id() == (Long) Math.round((Double) a[0])) {
                double sum = (double) a[1] + (double) a[2];
                holder.questionCorrect.setText(Math.round(((double) a[1] * 100.0) / sum) + "%");
                holder.questionWrong.setText(Math.round(((double) a [2] * 100.0) / sum) + "%");
            }
        }
        if(holder.question1View.getText().toString().equals(quizzersList.get(position).getCorrect_answer())) {
            holder.question1View.setTextColor(ContextCompat.getColor(holder.question1View.getContext(), android.R.color.holo_green_dark));
        } else if(holder.question2View.getText().toString().equals(quizzersList.get(position).getCorrect_answer())) {
            holder.question2View.setTextColor(ContextCompat.getColor(holder.question2View.getContext(), android.R.color.holo_green_dark));
        }else if(holder.question3View.getText().toString().equals(quizzersList.get(position).getCorrect_answer())) {
            holder.question3View.setTextColor(ContextCompat.getColor(holder.question3View.getContext(), android.R.color.holo_green_dark));
        }else if(holder.question4View.getText().toString().equals(quizzersList.get(position).getCorrect_answer())) {
            holder.question4View.setTextColor(ContextCompat.getColor(holder.question4View.getContext(), android.R.color.holo_green_dark));
        }
    }
    @Override
    public int getItemCount() {
        return quizzersList.size();
    }
    public void updateData(List<Questions> updatedQuestions, List<Object[]> answerList) {
        this.quizzersList.clear();
        this.quizzersList.addAll(updatedQuestions);
        this.answerList.clear();
        this.answerList.addAll(answerList);
        notifyDataSetChanged();
    }
}
