package com.example.bilkent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bilkent.DataClasses.UserResult;
import com.example.bilkent.DataClasses.UsersChoice;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class GameActivity extends AppCompatActivity {

    Socket mSocket;
    Gson gson;
    String uniqueID;
    int timeLeft;
    int play, idle, ads, result;
    int trueAnswerIndex = 0;
    int choice = -1;
    int number_of_answers = 0;

    ProgressBar pbWait;
    FrameLayout mainLayout;
    Button btnFirstAnswer, btnSecondAnswer, btnThirdAnswer, btnFourthAnswer;
    ImageView ivFirst, ivSecond, ivThird, ivFourth;
    TextView tv_number_of_answers;

    private void enableButton(Button btn){
        btn.setEnabled(true);
        btn.setAlpha(1);
    }

    private void disableButton(Button btn){
        btn.setEnabled(false);
        btn.setAlpha((float) 0.5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSocket = SocketSingleton.getSocket();
        gson = new Gson();

        uniqueID = IDUtil.id(this);
        (pbWait = findViewById(R.id.pb_wait_connect)).setVisibility(View.VISIBLE);
        (mainLayout = findViewById(R.id.fl_main)).setVisibility(View.INVISIBLE);
        btnFirstAnswer = findViewById(R.id.btn_first_answer);
        btnSecondAnswer = findViewById(R.id.btn_second_answer);
        btnThirdAnswer = findViewById(R.id.btn_third_answer);
        btnFourthAnswer = findViewById(R.id.btn_fourth_answer);

        ivFirst = findViewById(R.id.iv_first);
        ivSecond = findViewById(R.id.iv_second);
        ivThird = findViewById(R.id.iv_third);
        ivFourth = findViewById(R.id.iv_fourth);

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
                    progressBar.setMax(play);
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
                            btnFirstAnswer.setText(choices[0]);
                            btnSecondAnswer.setText(choices[1]);
                            btnThirdAnswer.setText(choices[2]);
                            btnFourthAnswer.setText(choices[3]);
                            pbWait.setVisibility(View.INVISIBLE);
                            mainLayout.setVisibility(View.VISIBLE);
                            ivFirst.setElevation(0);
                            ivSecond.setElevation(0);
                            ivThird.setElevation(0);
                            ivFourth.setElevation(0);
                            enableButton(btnFirstAnswer);
                            enableButton(btnSecondAnswer);
                            enableButton(btnThirdAnswer);
                            enableButton(btnFourthAnswer);
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
                            tv_number_of_answers.setText(Integer.toString(number_of_answers));
                            ((ProgressBar) findViewById(R.id.progressBarToday))
                                    .setProgress(timeLeft);
                            ((TextView) findViewById(R.id.tv_remaining_time)).
                                    setText(String.valueOf(timeLeft));
                            if (timeLeft == 0) {
                                Log.i("True Answer is ", "" + trueAnswerIndex);
                                if(trueAnswerIndex == 0) {
                                    btnFirstAnswer.setAlpha(1);
                                    btnSecondAnswer.setAlpha((float) 0.5);
                                    btnThirdAnswer.setAlpha((float) 0.5);
                                    btnFourthAnswer.setAlpha((float) 0.5);
                                    ivFirst.setElevation(10);
                                    ivSecond.setElevation(0);
                                    ivThird.setElevation(0);
                                    ivFourth.setElevation(0);
                                }else if (trueAnswerIndex == 1) {
                                    btnSecondAnswer.setAlpha(1);
                                    btnFirstAnswer.setAlpha((float) 0.5);
                                    btnThirdAnswer.setAlpha((float) 0.5);
                                    btnFourthAnswer.setAlpha((float) 0.5);
                                    ivSecond.setElevation(10);
                                    ivFirst.setElevation(0);
                                    ivThird.setElevation(0);
                                    ivFourth.setElevation(0);
                                }else if (trueAnswerIndex == 2) {
                                    btnThirdAnswer.setAlpha(1);
                                    btnSecondAnswer.setAlpha((float) 0.5);
                                    btnFirstAnswer.setAlpha((float) 0.5);
                                    btnFourthAnswer.setAlpha((float) 0.5);
                                    ivThird.setElevation(10);
                                    ivSecond.setElevation(0);
                                    ivFirst.setElevation(0);
                                    ivFourth.setElevation(0);
                                }else if (trueAnswerIndex == 3) {
                                    btnFourthAnswer.setAlpha(1);
                                    btnSecondAnswer.setAlpha((float) 0.5);
                                    btnThirdAnswer.setAlpha((float) 0.5);
                                    btnFirstAnswer.setAlpha((float) 0.5);
                                    ivFourth.setElevation(10);
                                    ivSecond.setElevation(0);
                                    ivThird.setElevation(0);
                                    ivFirst.setElevation(0);
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
                    String summaryStr = object.getJSONArray("summary").toString();
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
                btnSecondAnswer.setAlpha((float) 0.5);
                btnThirdAnswer.setAlpha((float) 0.5);
                btnFourthAnswer.setAlpha((float) 0.5);
                break;
            case R.id.btn_second_answer:
                choice = 1;
                btnFirstAnswer.setAlpha((float) 0.5);
                btnThirdAnswer.setAlpha((float) 0.5);
                btnFourthAnswer.setAlpha((float) 0.5);
                break;
            case R.id.btn_third_answer:
                choice = 2;
                btnSecondAnswer.setAlpha((float) 0.5);
                btnFirstAnswer.setAlpha((float) 0.5);
                btnFourthAnswer.setAlpha((float) 0.5);
                break;
            case R.id.btn_fourth_answer:
                choice = 3;
                btnSecondAnswer.setAlpha((float) 0.5);
                btnThirdAnswer.setAlpha((float) 0.5);
                btnFirstAnswer.setAlpha((float) 0.5);
                break;
        }

        btnFirstAnswer.setEnabled(false);
        btnSecondAnswer.setEnabled(false);
        btnThirdAnswer.setEnabled(false);
        btnFourthAnswer.setEnabled(false);

        System.out.println(choice);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("choice", choice);

        mSocket.emit("move", jsonObject);
    }

}
