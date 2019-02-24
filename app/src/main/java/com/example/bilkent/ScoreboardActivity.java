package com.example.bilkent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreboardActivity extends AppCompatActivity {

    TextView tv_first, tv_second, tv_third;
    TextView tv_first_score, tv_second_score, tv_third_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        tv_first = findViewById(R.id.tv_first);
        tv_second = findViewById(R.id.tv_second);
        tv_third = findViewById(R.id.tv_third);
        tv_first_score = findViewById(R.id.tv_first_score);
        tv_second_score = findViewById(R.id.tv_second_score);
        tv_third_score = findViewById(R.id.tv_third_score);


    }
}
