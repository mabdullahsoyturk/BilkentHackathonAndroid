package com.example.bilkent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bilkent.DataClasses.Question;

import java.io.Console;

public class QuestionActivity extends AppCompatActivity {

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Intent creatorIntent = getIntent();
        question = creatorIntent.getParcelableExtra("question");
        Log.i("Question Log", "onCreate: " + question.getQuestionText());
    }
}
