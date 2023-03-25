package com.example.quizmaster.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.Activity.HomeActivity;
import com.example.quizmaster.Holder.UserCoursesHolder;
import com.example.quizmaster.Model.UserCourses;
import com.example.quizmaster.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserCoursesAdapter extends RecyclerView.Adapter<UserCoursesHolder> {
    private Context content;
    private List<UserCourses> userCoursesList;
    public UserCoursesAdapter(Context content, List<UserCourses> userCoursesList) {
        this.content = content;
        this.userCoursesList = userCoursesList;
    }
    @NonNull
    @NotNull
    @Override
    public UserCoursesHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new UserCoursesHolder(LayoutInflater.from(content).inflate(R.layout.item_person, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull UserCoursesHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.person_name.setText(userCoursesList.get(position).getUser_id().getUser_name());
        holder.person_email.setText(userCoursesList.get(position).getUser_id().getEmail());
        holder.person_number.setText(userCoursesList.get(position).getUser_id().getPhone_number());
        holder.person_course_name.setText(userCoursesList.get(position).getCourse_id().getName());
        holder.person_description.setText(userCoursesList.get(position).getCourse_id().getDescription());
        holder.viewProgress.setTag(userCoursesList.get(position).getCourse_id().getUser_id().getUser_id());
        holder.viewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long lecture_id = (Long) view.getTag();
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                intent.putExtra("user_id12", userCoursesList.get(position).getUser_id().getUser_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("teacher_id12", lecture_id);
                intent.putExtra("user_id", lecture_id);
                intent.putExtra("courseId12", userCoursesList.get(position).getCourse_id().getCourse_id());
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return userCoursesList.size();
    }
    public void updateData(List<UserCourses> userCoursesList){
        this.userCoursesList.clear();
        this.userCoursesList.addAll(userCoursesList);
    }
}
