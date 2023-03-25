package com.example.quizmaster.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.Activity.CoursesActivity;
import com.example.quizmaster.Holder.CourseHolder;
import com.example.quizmaster.Model.Courses;
import com.example.quizmaster.R;
import com.example.quizmaster.Service.ApiClient;
import com.example.quizmaster.Service.CourseService;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseHolder> {
    Context content;
    List<Courses> coursesList;
    String receivedString;
    public CourseAdapter(Context content, List<Courses> coursesList, String receivedString) {
        this.content = content;
        this.coursesList = coursesList;
        this.receivedString = receivedString;
    }
    @NonNull
    @NotNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new CourseHolder(LayoutInflater.from(content).inflate(R.layout.item_course,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull CourseHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.course_name.setText(coursesList.get(position).getName());
        holder.course_description.setText(coursesList.get(position).getDescription());
        holder.course_id.setTag(coursesList.get(position).getCourse_id());
        holder.delete_id.setTag(coursesList.get(position).getCourse_id());
        holder.course_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long courseId = (Long) view.getTag();
                Log.d("courseId", "Clicked on course with id: " + courseId);
                Intent intent = new Intent(view.getContext(), CoursesActivity.class);
                intent.putExtra("courseId", courseId);
                //
                intent.putExtra("user_id", coursesList.get(position).getUser_id().getUser_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
        holder.delete_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long courseId = (Long) view.getTag();
                CourseService courseService = ApiClient.getClient().create(CourseService.class);
                Call<Void> callCourseDelete = courseService.deleteCoursesByCourseId(courseId);
                callCourseDelete.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("tes1234", "boos sa syers");
                        coursesList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, coursesList.size());
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("tes1234", "ni 1231231312");
                    }
                });
            }
        });
    }
    @Override
    public int getItemCount() {
        return coursesList.size();
    }
}
