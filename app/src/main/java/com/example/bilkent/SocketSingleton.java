package com.example.bilkent;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketSingleton {
    public static Socket mSocket = null;

    public static Socket getSocket() {
        if(mSocket == null) {
            try {
                mSocket = IO.socket("http://104.248.131.83:8080/quiz");
                return mSocket;
            } catch (URISyntaxException ignore) {}
        }

        return mSocket;
    }
}
