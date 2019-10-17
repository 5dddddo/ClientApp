package com.example.clientapp.Service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.clientapp.Activities.Main2Activity;
import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RealService extends Service {
    public static Intent serviceIntent;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    StringBuffer response = new StringBuffer();
    String msg;
    private MemberVO vo;
    public static Intent logoutIntent = new Intent();
    BlockingQueue blockingQueue = new ArrayBlockingQueue(10);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");
    String alram = "";


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

                out.println(msg);
                Log.i("ads", msg);
                out.flush();
                String s = "";

                while ((s = br.readLine()) != null) {
                    alram = "";
                    Log.i("service", "서버로부터 받는 데이터 : " + s);
                    response.append(s);
                    if (s.contains("Key")) {
                        out.println(s);
                        out.flush();
                        Log.i("keyValue", s);
                    } else if(s.contains("Request")) {
                        if (s.equals("RequestChangeTire")) {
                            Log.i("RealService", s);
                            alram += "타이어, ";
                        }
                        if (s.equals("RequestChangeWiper")) {
                            Log.i("RealService", s);
                            alram += "와이퍼, ";
                        }
                        if (s.equals("RequestChangeCooler")) {
                            Log.i("RealService", s);
                            alram += "냉각수, ";
                        }
                        if (s.equals("RequestChangeEngineOil")) {
                            Log.i("RealService", s);
                            alram += "엔진오일, ";
                        }

                        Log.i("keyValue", alram.length() + "");
                        alram = alram.substring(0, alram.length() - 2);
                        alram += " 정비요망";
                        builder.setSmallIcon(R.mipmap.appicon1_round);
                        builder.setContentTitle("Chavis 알림");
                        builder.setContentText(alram);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
                        }

                        notificationManager.notify(1, builder.build());
                    }
                    else if(s.equals("RepairSuccess")){

                        builder.setContentTitle("Chavis 알림");
                        builder.setContentText("정비가 완료 되었습니다.");

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
                        }

                        notificationManager.notify(1, builder.build());
                    }
                }
            } catch (
                    Exception e) {
                Log.i("service", "읽기 문제 : " + e.toString());
            }
        }
    }

    public RealService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceIntent = intent;
        initializeNotification();

        //
        // Todo.
        //
        // Thread, Timer 등으로 처리
//        if(msg!=null) {
//            msg = intent.getExtras().getString("mNo");
//        }
        Log.i("service", "service실행");

        logoutIntent.putExtra("key", "0");

        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        String json = preferences.getString("myObject", "");
        try {

            ObjectMapper mapper = new ObjectMapper();
            vo = mapper.readValue(json, MemberVO.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("Service_SharedPref", vo.getMember_no() + "");
        Log.i("Service_SharedPref", "로그인 객체 저장 성공34343");

        Log.i("service", vo.getMember_no() + "");
        msg = "MemberNO#" + vo.getMember_no() + "";


        ClientReceiveRunnable receiveRunnable = new ClientReceiveRunnable();
//        ClientSendRunnable sendRunnable = new ClientSendRunnable();
        Thread thread1 = new Thread(receiveRunnable);
//        Thread thread2 = new Thread(sendRunnable);
        thread1.start();
//        thread2.start();
        return START_STICKY;
    }

    public void initializeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
        builder.setSmallIcon(R.mipmap.appicon1_round);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText("설정을 보려면 누르세요.");
        style.setBigContentTitle(null);
        style.setSummaryText("서비스 동작중");
        builder.setContentText(null);
        builder.setContentTitle(null);
        builder.setOngoing(true);
        builder.setStyle(style);
        builder.setWhen(0);
        builder.setShowWhen(false);
        Intent notificationIntent = new Intent(this, Main2Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(new NotificationChannel("1", "undead_service", NotificationManager.IMPORTANCE_NONE));
        }

        Notification notification = builder.build();
        startForeground(1, notification);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        final Calendar calendar = Calendar.getInstance();

        if (logoutIntent.getExtras().get("key").toString().equals("1")) {
            try {
                out.close();
                br.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            serviceIntent = null;

        } else if (logoutIntent.getExtras().get("key").toString().equals("0")) {

            serviceIntent = null;
            try {
                out.close();
                br.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 3);
            Intent intent = new Intent(this, AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    }
}