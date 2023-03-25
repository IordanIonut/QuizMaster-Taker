package com.example.quizmaster.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.Holder.UserLecutreProgressHolder;
import com.example.quizmaster.Model.UserQuizzers;
import com.example.quizmaster.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserLecutureProgressAdapter extends RecyclerView.Adapter<UserLecutreProgressHolder>{
    private Context content;
    private List<UserQuizzers> userQuizzersList;
    private List<Object[]> objects;
    public UserLecutureProgressAdapter(Context content, List<UserQuizzers> userQuizzersList,  List<Object[]> objects) {
        this.content = content;
        this.userQuizzersList = userQuizzersList;
        this.objects = objects;
    }

    @NonNull
    @NotNull
    @Override
    public UserLecutreProgressHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new UserLecutreProgressHolder(LayoutInflater.from(content).inflate(R.layout.item_progress_lecture, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserLecutreProgressHolder holder, int position) {
        holder.lecture_name.setText(userQuizzersList.get(position).getQuizzer_id().getLecture_id().getTitle());
        holder.lecture_description.setText(userQuizzersList.get(position).getQuizzer_id().getLecture_id().getDescription());
        Double doubleValue = (Double) objects.get(position)[1];
        int intValue = doubleValue.intValue();
        holder.score_lecuture.setText(userQuizzersList.get(position).getScore() + " / " + intValue);
    }

    @Override
    public int getItemCount() {
        return userQuizzersList.size();
    }
}
