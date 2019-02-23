package com.example.bilkent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bilkent.DataClasses.GameState;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class WaitFragment extends Fragment {

    private OnFragmentInteractionListener mListener = null;
    private Socket mSocket;
    private String uniqueID;
    private String name;

    {
        try {
            mSocket = IO.socket("http://104.248.131.83:8080");
        } catch (URISyntaxException ignored) {
        }
    }

    public WaitFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            uniqueID = getArguments().getString("uniqueID");
        else
            throw new RuntimeException("You had to give uniqueID in the bundle");

        mSocket.on("general", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject)args[0];
                try {
                    name = obj.getString("name");
                    mListener.onStateChange(GameState.Game);
                } catch (JSONException ignore){

                }
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                mListener.onStateChange(GameState.ConnectionFailed);
            }
        });

        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                mListener.onStateChange(GameState.ConnectionFailed);
            }
        });

        mSocket.connect();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT);
        mSocket.off(Socket.EVENT_CONNECT_ERROR);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT);
        mSocket.close();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wait, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onStateChange(GameState newState);
    }

}