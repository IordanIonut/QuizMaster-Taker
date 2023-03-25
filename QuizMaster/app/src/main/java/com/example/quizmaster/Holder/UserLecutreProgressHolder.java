package com.example.quizmaster.Holder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.R;
import org.jetbrains.annotations.NotNull;

public class UserLecutreProgressHolder extends RecyclerView.ViewHolder {
    public TextView lecture_name;
    public TextView lecture_description;
    public TextView score_lecuture;
    public UserLecutreProgressHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        lecture_name = itemView.findViewById(R.id.lecture_name);
        lecture_description = itemView.findViewById(R.id.lecture_description);
        score_lecuture = itemView.findViewById(R.id.score_lecuture);
    }
}
