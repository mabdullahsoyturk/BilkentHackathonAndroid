package com.example.bilkent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    boolean[] categoryArray = new boolean[8];
    LinearLayout lySport, lyMusic, lyLiterature, lyHistory, lyGeography, lyArt, lyCulture, lyCinema;
    boolean editable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lySport = findViewById(R.id.ly_sport);
        lyMusic = findViewById(R.id.ly_music);
        lyLiterature = findViewById(R.id.ly_literature);
        lyHistory = findViewById(R.id.ly_history);
        lyGeography = findViewById(R.id.ly_geography);
        lyArt = findViewById(R.id.ly_art);
        lyCulture = findViewById(R.id.ly_culture);
        lyCinema = findViewById(R.id.ly_cinema);
    }

    public boolean revert(int number) {
        categoryArray[number - 1] = !categoryArray[number - 1];
        return categoryArray[number - 1];
    }

    public void onClick(View v) {
        if (!editable) return;

        switch (v.getId()) {
            case R.id.ib_sport:
            case R.id.ly_sport:
                if(lySport.getAlpha() == 1) {
                    lySport.setAlpha((float)0.5);
                }else {
                    lySport.setAlpha(1);
                }
                break;

            case R.id.ly_music:
            case R.id.ib_music:
                if(lyMusic.getAlpha() == 1) {
                    lyMusic.setAlpha((float)0.5);
                }else {
                    lyMusic.setAlpha(1);
                }
                break;

            case R.id.ly_literature:
            case R.id.ib_literature:
                if(lyLiterature.getAlpha() == 1) {
                    lyLiterature.setAlpha((float)0.5);
                }else {
                    lyLiterature.setAlpha(1);
                }
                break;

            case R.id.ly_history:
            case R.id.ib_history:
                if(lyHistory.getAlpha() == 1) {
                    lyHistory.setAlpha((float)0.5);
                }else {
                    lyHistory.setAlpha(1);
                }
                break;

            case R.id.ly_geography:
            case R.id.ib_geography:
                if(lyGeography.getAlpha() == 1) {
                    lyGeography.setAlpha((float)0.5);
                }else {
                    lyGeography.setAlpha(1);
                }
                break;

            case R.id.ly_art:
            case R.id.ib_art:
                if(lyArt.getAlpha() == 1) {
                    lyArt.setAlpha((float)0.5);
                }else {
                    lyArt.setAlpha(1);
                }
                break;

            case R.id.ly_culture:
            case R.id.ib_culture:
                if(lyCulture.getAlpha() == 1) {
                    lyCulture.setAlpha((float)0.5);
                }else {
                    lyCulture.setAlpha(1);
                }
                break;

            case R.id.ly_cinema:
            case R.id.ib_cinema:
                if(lyCinema.getAlpha() == 1) {
                    lyCinema.setAlpha((float)0.5);
                }else {
                    lyCinema.setAlpha(1);
                }
                break;
        }
    }

    public void onConnectClicked(View view) {
        findViewById(R.id.btn_connect).setEnabled(false);
        ((Button) findViewById(R.id.btn_connect)).setText(R.string.connect_waiting);
        editable = false;

        int count = 0;
        for (boolean aCategoryArray : categoryArray) {
            if (aCategoryArray) count++;
        }

        int[] arr = new int[count];

        for(int i = 0; i < categoryArray.length; i++){
            if(categoryArray[i]){
                arr[count - 1] = i + 1;
                count --;
            }
        }
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("categories", arr);
        startActivity(intent);
    }
}
