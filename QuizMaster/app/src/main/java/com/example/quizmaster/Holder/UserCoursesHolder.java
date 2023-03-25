package com.example.quizmaster.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.R;
import org.jetbrains.annotations.NotNull;

public class UserCoursesHolder extends RecyclerView.ViewHolder  {
    public TextView person_name;
    public TextView person_email;
    public TextView person_number;
    public TextView person_description;
    public TextView person_course_name;
    public ImageView viewProgress;
    public UserCoursesHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        person_name = itemView.findViewById(R.id.person_name);
        person_email = itemView.findViewById(R.id.person_email);
        person_number = itemView.findViewById(R.id.person_number);
        person_description = itemView.findViewById(R.id.person_description);
        person_course_name = itemView.findViewById(R.id.person_course_name);
        viewProgress = itemView.findViewById(R.id.viewProgress);
    }
}
