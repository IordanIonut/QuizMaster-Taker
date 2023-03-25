package com.example.quiztaker.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quiztaker.R;
import org.jetbrains.annotations.NotNull;

public class CourseHolder extends RecyclerView.ViewHolder{
    public TextView course_name;
    public TextView course_description;
    public ImageView course_id;
    public CourseHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        course_name = itemView.findViewById(R.id.course_name);
        course_description = itemView.findViewById(R.id.course_description);
        course_id = itemView.findViewById(R.id.course_id);

    }
}
