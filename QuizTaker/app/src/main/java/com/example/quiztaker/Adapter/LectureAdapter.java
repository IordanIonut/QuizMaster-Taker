package com.example.quiztaker.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quiztaker.Activity.CoursesActivity;
import com.example.quiztaker.Activity.LecturesActivity;
import com.example.quiztaker.Holder.LectureHolder;
import com.example.quiztaker.Model.Lectures;
import com.example.quiztaker.Model.UserLectures;
import com.example.quiztaker.Model.Users;
import com.example.quiztaker.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureHolder> {
    private Context content;
    private List<Lectures> lecturesList;
    private List<UserLectures> userLectures;
    private Long userId;
    private Intent intent;
    public LectureAdapter(Context content, List<Lectures> lecturesList, Long userId, List<UserLectures> userLectures) {
        this.content = content;
        this.lecturesList = lecturesList;
        this.userId = userId;
        this.userLectures = userLectures;
    }
    @NonNull
    @NotNull
    @Override
    public LectureHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new LectureHolder(LayoutInflater.from(content).inflate(R.layout.item_lecture,parent,false));
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull LectureHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.lecture_name.setText(lecturesList.get(position).getTitle());
        holder.lecture_description.setText(lecturesList.get(position).getDescription());
        holder.lecture_id.setTag(lecturesList.get(position).getLecture_id());
        for(UserLectures userLecture : userLectures){
            if(lecturesList.get(position).getLecture_id() == userLecture.getLecture_id().getLecture_id())
                holder.lecture_id.setColorFilter(R.color.purple_500);
        }
        holder.lecture_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lecture_id = (Long) view.getTag();
                Intent intent = new Intent(view.getContext(), LecturesActivity.class);
                intent.putExtra("lecture_id", lecture_id);
                intent.putExtra("courseId", lecturesList.get(position).getCourse_id().getCourse_id());
                intent.putExtra("userLoin", userId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return lecturesList.size();
    }
    public void updateData(List<Lectures> lecturesList, Long userId,  List<UserLectures> userLectures) {
        this.lecturesList.clear();
        this.lecturesList.addAll(lecturesList);
        this.userId = userId;
        this.userLectures.clear();
        this.userLectures.addAll(userLectures);
        notifyDataSetChanged();
    }
}