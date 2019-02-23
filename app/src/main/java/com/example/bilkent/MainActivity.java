package com.example.bilkent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> category_list = new ArrayList<>();

    ImageButton ib_sport, ib_music, ib_literature, ib_history, ib_geography, ib_art, ib_culture, ib_cinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ib_sport = findViewById(R.id.ib_sport);
        ib_music = findViewById(R.id.ib_music);
        ib_literature = findViewById(R.id.ib_literature);
        ib_history = findViewById(R.id.ib_history);
        ib_geography = findViewById(R.id.ib_geography);
        ib_art = findViewById(R.id.ib_art);
        ib_culture = findViewById(R.id.ib_culture);
        ib_cinema = findViewById(R.id.ib_cinema);

//        Intent intent = new Intent(this, GameActivity.class);
//        startActivity(intent);
    }

    public void checkIfContains(int number) {
        if (category_list.contains(number)) {
            category_list.remove(category_list.indexOf(number));
        } else {
            category_list.add(number);
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_sport:
                checkIfContains(5);
                break;
            case R.id.ib_music:
                checkIfContains(7);
                break;
            case R.id.ib_literature:
                checkIfContains(6);
                break;
            case R.id.ib_history:
                checkIfContains(2);
                break;
            case R.id.ib_geography:
                checkIfContains(3);
                break;
            case R.id.ib_art:
                checkIfContains(4);
                break;
            case R.id.ib_culture:
                checkIfContains(1);
                break;
            case R.id.ib_cinema:
                checkIfContains(8);
                break;
        }
    }
}
