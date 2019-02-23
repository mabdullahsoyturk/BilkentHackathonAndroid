package com.example.bilkent;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bilkent.DataClasses.GameState;

public class GameActivity extends AppCompatActivity
        implements WaitFragment.OnFragmentInteractionListener,
        TriviaGameFragment.OnFragmentInteractionListener {

    @Override
    public void onStateChange(GameState newState) {
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.remove(waitFragment);
        if (newState == GameState.ConnectionFailed) {
            transaction.add(R.id.container, connectionFailedFragment);
        } else if (newState == GameState.Game) {
            transaction.add(R.id.container, triviaGameFragment);
        }
        transaction.commit();
    }

    GameState mGameState;
    FragmentTransaction transaction;
    TriviaGameFragment triviaGameFragment;
    WaitFragment waitFragment;
    ConnectionFailedFragment connectionFailedFragment;
    GameState gameState;
    String uniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        uniqueID = IDUtil.id(this);
        mGameState = GameState.ConnectionTrying;
        connectionFailedFragment = new ConnectionFailedFragment();
        waitFragment = new WaitFragment();
        Bundle arguments = new Bundle();
        arguments.putString("uniqueID", uniqueID);
        waitFragment.setArguments(arguments);
        triviaGameFragment = new TriviaGameFragment();
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.container, waitFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
