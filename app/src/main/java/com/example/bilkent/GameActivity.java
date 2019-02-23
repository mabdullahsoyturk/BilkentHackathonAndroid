package com.example.bilkent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class GameActivity extends AppCompatActivity {

    Socket mSocket;
    String uniqueID;


    {
        try {
            mSocket = IO.socket("http://104.248.131.83:8080/quiz");
        } catch (URISyntaxException ignore) {
        }
    }

    ProgressBar pbWait;
    FrameLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        uniqueID = IDUtil.id(this);
        (pbWait = findViewById(R.id.pb_wait_connect)).setVisibility(View.VISIBLE);
        (mainLayout = findViewById(R.id.fl_main)).setVisibility(View.INVISIBLE);
        final String message = getIntent().getStringExtra("categories");

        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                mSocket.emit("login",
                        String.format("{phoneId:'%s',  categories:%s}",
                                IDUtil.id(GameActivity.this), message));
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });

        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });

        mSocket.on("general", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });

        mSocket.on("play", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject object = (JSONObject) args[0];
                    object = object.getJSONObject("question");
                    final String text = object.getString("text");
                    JSONArray choicesJSONArr = object.getJSONArray("choices");
                    final String[] choices = new String[4];
                    for(int i = 0; i < choicesJSONArr.length(); i++){
                        choices[i] = choicesJSONArr.getString(i);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)findViewById(R.id.tv_question)).setText(text);
                            ((Button)findViewById(R.id.btn_first_answer)).setText(choices[0]);
                            ((Button)findViewById(R.id.btn_second_answer)).setText(choices[1]);
                            ((Button)findViewById(R.id.btn_third_answer)).setText(choices[2]);
                            ((Button)findViewById(R.id.btn_fourth_answer)).setText(choices[3]);
                            pbWait.setVisibility(View.INVISIBLE);
                            mainLayout.setVisibility(View.VISIBLE);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*

*/


            }
        });

        if (!mSocket.connected())
            mSocket.connect();
    }

}
