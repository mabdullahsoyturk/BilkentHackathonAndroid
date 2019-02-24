package com.example.bilkent;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bilkent.DataClasses.UserResult;
import com.example.bilkent.DataClasses.UsersChoice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import 	android.view.animation.DecelerateInterpolator;

public class GameActivity extends AppCompatActivity {

    Socket mSocket;
    String uniqueID;
    int timeLeft;
    int play, idle, ads, result;
    int trueAnswerIndex = 0;
    int choice = -1;
    int number_of_answers = 0;
    int button_width;

    ProgressBar pbWait;
    FrameLayout mainLayout;
    Button[] buttons;

    ImageView ivFirst, ivSecond, ivThird, ivFourth;
    ImageView[] imageViews;

    ObjectAnimator progressBarAnimation;
    TextView tv_number_of_answers;

    private void enableButton(Button btn){
        btn.setEnabled(true);
        btn.setAlpha(1);
    }

    private void disableButton(Button btn){
        btn.setEnabled(false);
        btn.setAlpha((float) 0.5);
    }
    private void setProgressAnimate(ProgressBar pb, int progressTo)
    {
        if(progressBarAnimation != null){
            progressBarAnimation.cancel();
        }

        ObjectAnimator progressBarAnimation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo*100 );
        progressBarAnimation.setDuration(1200);
        progressBarAnimation.setInterpolator(new DecelerateInterpolator());
        progressBarAnimation.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSocket = SocketSingleton.getSocket();

        uniqueID = IDUtil.id(this);
        (pbWait = findViewById(R.id.pb_wait_connect)).setVisibility(View.VISIBLE);
        (mainLayout = findViewById(R.id.fl_main)).setVisibility(View.INVISIBLE);
        buttons = new Button[4];
        buttons[0] = findViewById(R.id.btn_first_answer);
        buttons[1] = findViewById(R.id.btn_second_answer);
        buttons[2] = findViewById(R.id.btn_third_answer);
        buttons[3] = findViewById(R.id.btn_fourth_answer);

        button_width = buttons[0].getWidth();

        imageViews = new ImageView[4];
        imageViews[0] = findViewById(R.id.iv_first);
        imageViews[1] = findViewById(R.id.iv_second);
        imageViews[2] = findViewById(R.id.iv_third);
        imageViews[3] = findViewById(R.id.iv_fourth);

        tv_number_of_answers = findViewById(R.id.tv_number_of_answers);

        final int[] categories = getIntent().getIntArrayExtra("categories");
        final JSONObject object = new JSONObject();
        try {
            object.put("phoneId", IDUtil.id(this));
            JSONArray array = new JSONArray();
            for (int i = 0; i < categories.length; i++) array.put(i, categories[i]);
            object.put("categories", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                mSocket.emit("login", object);
            }
        });

        timeLeft = 20;

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
                try {
                    JSONObject jsonObject = (JSONObject) args[0];
                    jsonObject = jsonObject.getJSONObject("stateDurations");
                    int idle = jsonObject.getInt("Idle");
                    int play = jsonObject.getInt("Play");
                    int result = jsonObject.getInt("Result");
                    int ads = jsonObject.getInt("Ads");
                    GameActivity.this.idle = idle;
                    GameActivity.this.play = play;
                    GameActivity.this.result = result;
                    GameActivity.this.ads = ads;
                    ProgressBar progressBar = findViewById(R.id.progressBarToday);
                    progressBar.setMax(play*100);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mSocket.on("play", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject object = (JSONObject) args[0];
                    Log.i("Json Came", object.toString());
                    object = object.getJSONObject("question");
                    final String text = object.getString("text");
                    JSONArray choicesJSONArr = object.getJSONArray("choices");
                    final String[] choices = new String[4];
                    trueAnswerIndex = object.getInt("trueAnswer");
                    for (int i = 0; i < choicesJSONArr.length(); i++) {
                        choices[i] = choicesJSONArr.getString(i);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.tv_question)).setText(text);
                            for(int i = 0; i < buttons.length; i++){
                                ViewGroup.LayoutParams params = buttons[i].getLayoutParams();
                                params.width = button_width;
                                buttons[i].setLayoutParams(params);

                                buttons[i].setText(choices[i]);
                                imageViews[i].setElevation(0);
                                enableButton(buttons[i]);
                            }
                            pbWait.setVisibility(View.INVISIBLE);
                            mainLayout.setVisibility(View.VISIBLE);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        mSocket.on("realtime", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject object = (JSONObject) args[0];
                    Log.i("Json Came", object.toString());
                    timeLeft = object.getInt("timeLeft");
                    number_of_answers = object.getInt("numberOfAnswers");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setProgressAnimate((ProgressBar) findViewById(R.id.progressBarToday), timeLeft);

                            tv_number_of_answers.setText(Integer.toString(number_of_answers));
                            ((ProgressBar) findViewById(R.id.progressBarToday))
                                    .setProgress(timeLeft);

                            ((TextView) findViewById(R.id.tv_remaining_time)).
                                    setText(String.valueOf(timeLeft));
                            if (timeLeft == 0) {
                                Log.i("True Answer is ", "" + trueAnswerIndex);
                                for(int i = 0; i < buttons.length; i++) {
                                    if(trueAnswerIndex == i){
                                        buttons[i].setAlpha(1);
                                        imageViews[i].setElevation(10);
                                    } else{
                                        buttons[i].setAlpha((float) 0.5);
                                        imageViews[i].setElevation(0);
                                    }
                                }

                            }


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mSocket.on("results", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject object = (JSONObject) args[0];
                try {
                    String scoreboardStr = object.getJSONArray("scoreboard").toString();
                    final JSONArray summary = object.getJSONArray("summary");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                for(int i = 0; i < buttons.length; i++) {
                                    ViewGroup.LayoutParams params = buttons[i].getLayoutParams();
                                    double weight = summary.getDouble(i);

                                    if(weight < 0.05)
                                        weight = 0.1;
                                    params.width = (int)(buttons[i].getWidth() * weight);

                                    buttons[i].setText("%" + ((int)(summary.getDouble(i)*100)));
                                    buttons[i].setLayoutParams(params);

                                }

                            }catch (Exception e){
                                Log.i("LAN", "HEYHEY");
                            }
                            }});

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        if (!mSocket.connected())
            mSocket.connect();
    }

    public void gameOnClick(View view) throws JSONException {
        System.out.println("works");

        switch (view.getId()) {
            case R.id.btn_first_answer:
                choice = 0;
                buttons[1].setAlpha((float) 0.5);
                buttons[2].setAlpha((float) 0.5);
                buttons[3].setAlpha((float) 0.5);
                break;
            case R.id.btn_second_answer:
                choice = 1;
                buttons[0].setAlpha((float) 0.5);
                buttons[2].setAlpha((float) 0.5);
                buttons[3].setAlpha((float) 0.5);
                break;
            case R.id.btn_third_answer:
                choice = 2;
                buttons[1].setAlpha((float) 0.5);
                buttons[0].setAlpha((float) 0.5);
                buttons[3].setAlpha((float) 0.5);
                break;
            case R.id.btn_fourth_answer:
                choice = 3;
                buttons[1].setAlpha((float) 0.5);
                buttons[2].setAlpha((float) 0.5);
                buttons[0].setAlpha((float) 0.5);
                break;
        }
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setEnabled(false);
        }

        System.out.println(choice);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("choice", choice);

        mSocket.emit("move", jsonObject);
    }

}
