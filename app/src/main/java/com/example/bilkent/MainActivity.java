package com.example.bilkent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bilkent.DataClasses.Question;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, GameActivity.class);
        /*
        Question someQuestion = new Question("Who is ugly",
                new String[] {"Muaz", "Of course, Muaz", "Muaz, obviously", "Loser Muaz"});
        intent.putExtra("question", someQuestion); */
        startActivity(intent); //start the activity
    }
}
