package com.example.quiztaker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quiztaker.Activity.CoursesActivity;
import com.example.quiztaker.Holder.CourseHolder;
import com.example.quiztaker.Model.Courses;
import com.example.quiztaker.Model.Lectures;
import com.example.quiztaker.Model.UserLectures;
import com.example.quiztaker.R;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {
    private Context content;
    private List<Courses> coursesList;
    private Long userId;
    private String yes;
    public CourseAdapter(Context content, List<Courses> coursesList, Long userId, String yes) {
        this.content = content;
        this.coursesList = coursesList;
        this.userId = userId;
        this.yes = yes;
    }
    @NonNull
    @NotNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new CourseHolder(LayoutInflater.from(content).inflate(R.layout.item_course,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull CourseHolder holder, int position) {
        holder.course_name.setText(coursesList.get(position).getName());
        holder.course_description.setText(coursesList.get(position).getDescription());
        holder.course_id.setTag(coursesList.get(position).getCourse_id());
        if(yes.equals("yes")){
            holder.course_id.setColorFilter(R.color.purple_500);
        }
        holder.course_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(yes.equals("yes")){
                    holder.course_id.setColorFilter(R.color.purple_500);
                }
                Long courseId = (Long) view.getTag();
                Intent intent = new Intent(view.getContext(), CoursesActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("userLoin", userId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return coursesList.size();
    }
    public void updateData(List<Courses> coursesList, Long userId) {
        this.coursesList.clear();
        this.coursesList.addAll(coursesList);
        this.userId = userId;
        notifyDataSetChanged();
    }
}
