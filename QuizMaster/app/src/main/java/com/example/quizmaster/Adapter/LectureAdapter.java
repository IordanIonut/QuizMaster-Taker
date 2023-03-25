package com.example.quizmaster.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.Activity.CoursesActivity;
import com.example.quizmaster.Activity.LecturesActivity;
import com.example.quizmaster.Holder.LectureHolder;
import com.example.quizmaster.Model.Lectures;
import com.example.quizmaster.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureHolder> {
    private Context content;
    private List<Lectures> lecturesList;
    private Intent intent;
    public LectureAdapter(Context content, List<Lectures> lecturesList) {
        this.content = content;
        this.lecturesList = lecturesList;
    }
    @NonNull
    @NotNull
    @Override
    public LectureHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new LectureHolder(LayoutInflater.from(content).inflate(R.layout.item_lecture,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull LectureHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.lecture_name.setText(lecturesList.get(position).getTitle());
        holder.lecture_description.setText(lecturesList.get(position).getDescription());
        holder.lecture_id.setTag(lecturesList.get(position).getLecture_id());
        holder.lecture_edit.setTag(lecturesList.get(position).getLecture_id());
        holder.lecture_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(content, CoursesActivity.class);
                Long lecture_id = (Long) view.getTag();
                intent.putExtra("lecture_id", lecture_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user_id", lecturesList.get(position).getCourse_id().getUser_id().getUser_id());
                intent.putExtra("courseId",  lecturesList.get(position).getCourse_id().getCourse_id());
                content.startActivity(intent);
            }
        });
        holder.lecture_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lecture_id = (Long) view.getTag();
                Intent intent = new Intent(view.getContext(), LecturesActivity.class);
                intent.putExtra("lecture_id", lecture_id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user_id", lecturesList.get(position).getCourse_id().getUser_id().getUser_id());
                intent.putExtra("courseId", lecturesList.get(position).getCourse_id().getCourse_id());
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return lecturesList.size();
    }

    public void updateData(List<Lectures> lecturesList) {
        this.lecturesList.clear();
        this.lecturesList.addAll(lecturesList);
    }
}