package com.example.quiztaker.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quiztaker.R;
import org.jetbrains.annotations.NotNull;

public class LectureHolder extends RecyclerView.ViewHolder {
    public TextView lecture_name;
    public TextView lecture_description;
    public ImageView lecture_id;
    public LectureHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        lecture_name = itemView.findViewById(R.id.lecture_name);
        lecture_description = itemView.findViewById(R.id.lecture_description);
        lecture_id = itemView.findViewById(R.id.lecture_id);
    }
}