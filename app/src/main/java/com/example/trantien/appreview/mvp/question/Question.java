package com.example.trantien.appreview.mvp.question;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.trantien.appreview.R;

public class Question extends AppCompatActivity {
    private FrameLayout answerA;
    private FrameLayout answerB;
    private FrameLayout answerC;
    private FrameLayout answerD;
    private QuestionModel.Answer answer;
    private String unselectionColor = "#ffffff";
    private String selectionColor = "#e9e9e9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);

        answerA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateAnswer(QuestionModel.Answer.A);
            }
        });

        answerB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateAnswer(QuestionModel.Answer.B);
            }
        });

        answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer(QuestionModel.Answer.C);
            }
        });

        answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnswer(QuestionModel.Answer.D);
            }
        });
    }

    private void updateAnswer(QuestionModel.Answer answer){
        updateAllBackground();
        this.answer = answer;
        switch (answer){
            case A: answerA.setBackgroundColor(Color.parseColor(selectionColor)); break;
            case B: answerB.setBackgroundColor(Color.parseColor(selectionColor)); break;
            case C: answerC.setBackgroundColor(Color.parseColor(selectionColor)); break;
            case D: answerD.setBackgroundColor(Color.parseColor(selectionColor)); break;
        }
    }

    private void updateAllBackground(){
        answerA.setBackgroundColor(Color.parseColor(unselectionColor));
        answerB.setBackgroundColor(Color.parseColor(unselectionColor));
        answerC.setBackgroundColor(Color.parseColor(unselectionColor));
        answerD.setBackgroundColor(Color.parseColor(unselectionColor));
    }
}