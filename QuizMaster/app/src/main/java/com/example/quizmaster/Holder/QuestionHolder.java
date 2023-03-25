package com.example.quizmaster.Holder;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizmaster.R;
import org.jetbrains.annotations.NotNull;

public class QuestionHolder extends RecyclerView.ViewHolder {
    public TextView questionView, question1View, question2View, question3View, question4View, questionCorrect, questionWrong;
    public QuestionHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        questionView = itemView.findViewById(R.id.questionView);
        question1View = itemView.findViewById(R.id.question1View);
        question2View = itemView.findViewById(R.id.question2View);
        question3View = itemView.findViewById(R.id.question3View);
        question4View = itemView.findViewById(R.id.question4View);
        questionCorrect = itemView.findViewById(R.id.questionCorrect);
        questionWrong = itemView.findViewById(R.id.questionWrong);
    }
}

