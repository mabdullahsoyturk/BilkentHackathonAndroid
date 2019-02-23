package com.example.bilkent;

import android.content.Intent;
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

        ib_sport         =              findViewById(R.id.ib_sport);
        ib_music         =              findViewById(R.id.ib_music);
        ib_literature    =              findViewById(R.id.ib_literature);
        ib_history       =              findViewById(R.id.ib_history);
        ib_geography     =              findViewById(R.id.ib_geography);
        ib_art           =              findViewById(R.id.ib_art);
        ib_culture       =              findViewById(R.id.ib_culture);
        ib_cinema        =              findViewById(R.id.ib_cinema);

//        Intent intent = new Intent(this, GameActivity.class);
//        startActivity(intent);
    }

    public void onClick(View v) {

        System.out.println(v.getId());

        switch (v.getId()) {
            case R.id.ib_sport:
                if(category_list.contains(5)) {
                    category_list.remove(5);
                }
                category_list.add(5);
                break;
            case R.id.ib_music:
                if(category_list.contains(7)) {
                    category_list.remove(7);
                }
                category_list.add(7);
                break;
            case R.id.ib_literature:
                if(category_list.contains(6)) {
                    category_list.remove(6);
                }
                category_list.add(6);
                break;
            case R.id.ib_history:
                if(category_list.contains(2)) {
                    category_list.remove(2);
                }
                category_list.add(2);
            case R.id.ib_geography:
                if(category_list.contains(3)) {
                    category_list.remove(3);
                }
                category_list.add(3);
                break;
            case R.id.ib_art:
                if(category_list.contains(4)) {
                    category_list.remove(4);
                }
                category_list.add(4);
                break;
            case R.id.ib_culture:
                if(category_list.contains(1)) {
                    category_list.remove(1);
                }
                category_list.add(1);
                break;
            case R.id.ib_cinema:
                if(category_list.contains(8)) {
                    category_list.remove(8);
                }
                category_list.add(8);
                break;
        }
    }
}
