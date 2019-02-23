package com.example.bilkent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class GameActivity extends AppCompatActivity {

    Socket mSocket;
    String uniqueID;

    {
        try{
            mSocket = IO.socket("http://104.248.131.83:8080/quiz");
        } catch (URISyntaxException ignore) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        uniqueID = IDUtil.id(this);
        findViewById(R.id.pb_wait_connect).setVisibility(View.VISIBLE);
        findViewById(R.id.fl_main).setVisibility(View.INVISIBLE);
        String message = getIntent().getStringExtra("categories");



    }

}
