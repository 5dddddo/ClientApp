package com.example.clientapp.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ClientService extends Service {

    Socket socket;
    BufferedReader br;
    PrintWriter out;
    StringBuffer response = new StringBuffer();
    String msg;
    BlockingQueue blockingQueue = new ArrayBlockingQueue(10);

    class ClientSendRunnable implements Runnable {
        BlockingQueue blockingQueue;

        @Override
        public void run() {
            while (true) {
                try {
                    String msg = (String) blockingQueue.take();
                    Log.i("ChattingClient___", "blocking queue send: " + msg);
                    out.println(msg);
                    out.flush();
                } catch (Exception e) {
                    Log.i("ChattingClientError", "blocking queue 문제 : " + e.toString());
                }
            }
        }
    }


    class ClientReceiveRunnable implements Runnable {

        Intent receiveIntent = new Intent();

        @Override
        public void run() {
            try {
                try {
                    socket = new Socket("70.12.115.63", 6767);
                    Log.i("service", "서버 연결 성공!!");
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream());
                } catch (Exception e) {
                    Log.i("service", "서버 연결 실패 : " + e.toString());
                }

                out.println("MemberNO#" + msg);
                out.flush();
                String s = "";

                while ((s = br.readLine()) != null) {
                    Log.i("service", "서버로부터 받는 데이터 : " + s);
                    response.append(s);
                }
            } catch (
                    Exception e) {
                Log.i("service", "읽기 문제 : " + e.toString());
            }
        }
    }



    public ClientService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.i("service", "서비스 시작");

        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // 서비스가 호출될 때마다 실행

        msg = intent.getExtras().getString("mNo");


        ClientReceiveRunnable receiveRunnable = new ClientReceiveRunnable();
//        ClientSendRunnable sendRunnable = new ClientSendRunnable();
        Thread thread1 = new Thread(receiveRunnable);
//        Thread thread2 = new Thread(sendRunnable);
        thread1.start();
//        thread2.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("clientService","클라이언트 서비스 종료");
    }

}
