package com.example.bilkent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    boolean[] categoryArray = new boolean[8];
    TextView tvSport, tvMusic, tvLiterature, tvHistory, tvGeography, tvArt, tvCulture, tvCinema;
    boolean editable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSport = findViewById(R.id.tv_sport);
        tvMusic = findViewById(R.id.tv_music);
        tvLiterature = findViewById(R.id.tv_literature);
        tvHistory = findViewById(R.id.tv_history);
        tvGeography = findViewById(R.id.tv_geography);
        tvArt = findViewById(R.id.tv_art);
        tvCulture = findViewById(R.id.tv_culture);
        tvCinema = findViewById(R.id.tv_cinema);


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
                tvSport.setText(getString(R.string.sport) + (revert(5) ? " X" : ""));
                break;

            case R.id.ly_music:
            case R.id.ib_music:
                tvMusic.setText(getString(R.string.music) + (revert(7) ? " X" : ""));
                break;

            case R.id.ly_literature:
            case R.id.ib_literature:
                tvLiterature.setText(getString(R.string.literature) + (revert(6) ? " X" : ""));
                break;

            case R.id.ly_history:
            case R.id.ib_history:
                tvHistory.setText(getString(R.string.history) + (revert(2) ? " X" : ""));
                break;

            case R.id.ly_geography:
            case R.id.ib_geography:
                tvGeography.setText(getString(R.string.geography) + (revert(3) ? "X" : ""));
                break;

            case R.id.ly_art:
            case R.id.ib_art:
                tvArt.setText(getString(R.string.art) + (revert(4) ? " X" : ""));
                break;

            case R.id.ly_culture:
            case R.id.ib_culture:
                tvCulture.setText(getString(R.string.culture) + (revert(1) ? " X" : ""));
                break;

            case R.id.ly_cinema:
            case R.id.ib_cinema:
                tvCinema.setText(getString(R.string.cinema) + (revert(8) ? " X" : ""));

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
