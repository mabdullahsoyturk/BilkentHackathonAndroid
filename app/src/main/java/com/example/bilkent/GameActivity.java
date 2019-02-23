package com.example.bilkent;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity implements TriviaGameFragment.OnFragmentInteractionListener {

    Button btn;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        btn = findViewById(R.id.btn_tmp);
        final TriviaGameFragment fragment = new TriviaGameFragment();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(android.R.id.content, fragment, "Hello");
                transaction.commit();
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
