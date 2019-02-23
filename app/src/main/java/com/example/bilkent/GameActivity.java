package com.example.bilkent;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bilkent.DataClasses.GameState;

public class GameActivity extends AppCompatActivity implements WaitFragment.OnFragmentInteractionListener {

    @Override
    public void onStateChange(GameState newState) {

    }

    GameState mGameState;
    FragmentTransaction transaction;
    TriviaGameFragment triviaGameFragment;
    WaitFragment waitFragment;
    GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameState = GameState.ConnectionTrying;
        waitFragment = new WaitFragment();
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(android.R.id.content, waitFragment, "Hello");
        transaction.commit();
    }

}
