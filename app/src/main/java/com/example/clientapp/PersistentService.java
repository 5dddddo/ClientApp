//package com.example.clientapp;
//
//import android.annotation.TargetApi;
//import android.app.AlarmManager;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.os.Build;
//import android.os.CountDownTimer;
//import android.os.IBinder;
//import android.os.SystemClock;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//
//public class PersistentService extends Service {
//
//    private static final int MILLISINFUTURE = 1000 * 1000;
//    private static final int COUNT_DOWN_INTERVAL = 1000;
//
//    private CountDownTimer countDownTimer;
//
//
//    Socket socket;
//    BufferedReader br;
//    PrintWriter out;
//    StringBuffer response = new StringBuffer();
//    String msg;
//    BlockingQueue blockingQueue = new ArrayBlockingQueue(10);
//
//
//    class ClientReceiveRunnable implements Runnable {
//
//        Intent receiveIntent = new Intent();
//
//        @Override
//        public void run() {
//            try {
//                try {
//                    socket = new Socket("70.12.115.63", 6767);
//                    Log.i("service", "서버 연결 성공!!");
//                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    out = new PrintWriter(socket.getOutputStream());
//                } catch (Exception e) {
//                    Log.i("service", "서버 연결 실패 : " + e.toString());
//                }
//
//                out.println("MemberNO#" + msg);
//                out.flush();
//                String s = "";
//
//                while ((s = br.readLine()) != null) {
//                    Log.i("service", "서버로부터 받는 데이터 : " + s);
//                    response.append(s);
//                    if (s.contains("Key")){
//                        String[] receivemsg = s.split("#");
//                        out.println(receivemsg[1]);
//                        out.flush();
//                    }
//                    Log.i("Receivedata", response.toString());
//                }
//            } catch (
//                    Exception e) {
//                Log.i("service", "읽기 문제 : " + e.toString());
//            }
//        }
//    }
//
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        unregisterRestartAlarm();
//        super.onCreate();
//
//        Log.i("PerService", "12ooououououoou3");
//
//        initData();
//    }
//
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        startForeground(1, new Notification());
//
//        /**
//         * startForeground 를 사용하면 notification 을 보여주어야 하는데 없애기 위한 코드
//         */
//        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Notification notification;
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//
//            notification = new Notification.Builder(getApplicationContext())
//                    .setContentTitle("제목")
//                    .setContentText("내용")
//                    .build();
//
//
//        } else {
//
//            Notification.Builder builder = new Notification.Builder(this)
//                    .setContentTitle("korea")
//                    .setContentText("koko");
//
//            notification = builder.build();
//
//
//
////            notification = new Notification(0, "", System.currentTimeMillis());
////            notification.setLatestEventInfo(getApplicationContext(), "", "", null);
//
//        }
//
//        nm.notify(0, notification);
//        nm.cancel(startId);
//
//        Log.i("nono", "nonokkk");
//
//
//        msg = intent.getExtras().getString("mNo");
//
//
//        ClientReceiveRunnable receiveRunnable = new ClientReceiveRunnable();
////        ClientSendRunnable sendRunnable = new ClientSendRunnable();
//        Thread thread1 = new Thread(receiveRunnable);
////        Thread thread2 = new Thread(sendRunnable);
//        thread1.start();
////        thread2.start();
//
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        Log.i("PersistentService", "Persisteent_onDestroy");
//        countDownTimer.cancel();
//
//
//        try {
//            if (br != null) {
//                br.close();
//            }
//            if (out != null){
//                out.close();
//            }
//            if (socket != null){
//                socket.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        /**
//         * 서비스 종료 시 알람 등록을 통해 서비스 재 실행
//         */
//        registerRestartAlarm();
//
//    }
//
//    /**
//     * 데이터 초기화
//     */
//    private void initData() {
//
//
//        countDownTimer();
//        Log.i("PerService", "4");
//        countDownTimer.start();
//        Log.i("PerService", "5");
//    }
//
//    public void countDownTimer() {
//
//        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
//            public void onTick(long millisUntilFinished) {
//
//                Log.i("PersistentService", "onTick");
//            }
//
//            public void onFinish() {
//
//                Log.i("PersistentService", "onFinish");
//            }
//        };
//    }
//
//
//    /**
//     * 알람 매니져에 서비스 등록
//     */
//    private void registerRestartAlarm() {
//
//        Log.i("000 PersistentService", "registerRestartAlarm");
//        Intent intent = new Intent(PersistentService.this, RestartService.class);
//        intent.setAction("ACTION.RESTART.PersistentService");
//        PendingIntent sender = PendingIntent.getBroadcast(PersistentService.this, 0, intent, 0);
//
//        long firstTime = SystemClock.elapsedRealtime();
//        firstTime += 1 * 1000;
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        /**
//         * 알람 등록
//         */
//        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 1 * 1000, sender);
//
//    }
//
//    /**
//     * 알람 매니져에 서비스 해제
//     */
//    private void unregisterRestartAlarm() {
//
//        Log.i("000 PersistentService", "unregisterRestartAlarm");
//
//        Intent intent = new Intent(PersistentService.this, RestartService.class);
//        intent.setAction("ACTION.RESTART.PersistentService");
//        PendingIntent sender = PendingIntent.getBroadcast(PersistentService.this, 0, intent, 0);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//        /**
//         * 알람 취소
//         */
//        alarmManager.cancel(sender);
//
//
//    }
//}
//
//
//
//
//
//
//
////
////package com.example.clientapp;
////
////import android.annotation.TargetApi;
////import android.app.AlarmManager;
////import android.app.Notification;
////import android.app.NotificationManager;
////import android.app.PendingIntent;
////import android.app.Service;
////import android.content.Intent;
////import android.os.Build;
////import android.os.CountDownTimer;
////import android.os.IBinder;
////import android.os.SystemClock;
////import android.util.Log;
////
//////import com.woong.beaconbackgroundservice.R;
////
/////**
//// * Created by woong on 2015. 1. 28..
//// */
////public class PersistentService extends Service {
////
////    private static final int MILLISINFUTURE = 1000*1000;
////    private static final int COUNT_DOWN_INTERVAL = 1000;
////
////    private CountDownTimer countDownTimer;
////
////
////    @Override
////    public IBinder onBind(Intent intent) {
////        return null;
////    }
////
////    @Override
////    public void onCreate() {
////        unregisterRestartAlarm();
////        super.onCreate();
////
////        initData();
////    }
////
////    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
////    @Override
////    public int onStartCommand(Intent intent, int flags, int startId) {
////
////        startForeground(1,new Notification());
////
////        /**
////         * startForeground 를 사용하면 notification 을 보여주어야 하는데 없애기 위한 코드
////         */
////        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
////        Notification notification;
////
////        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
////
////            notification = new Notification.Builder(getApplicationContext())
////                    .setContentTitle("")
////                    .setContentText("")
////                    .build();
////
////        }else{
////            notification = new Notification(0, "", System.currentTimeMillis());
////            notification.setLatestEventInfo(getApplicationContext(), "", "", null);
////        }
////
////        nm.notify(startId, notification);
////        nm.cancel(startId);
////
////        return super.onStartCommand(intent, flags, startId);
////    }
////
////    @Override
////    public void onDestroy() {
////        super.onDestroy();
////
////        Log.i("PersistentService" , "onDestroy" );
////        countDownTimer.cancel();
////
////        /**
////         * 서비스 종료 시 알람 등록을 통해 서비스 재 실행
////         */
////        registerRestartAlarm();
////    }
////
////    /**
////     * 데이터 초기화
////     */
////    private void initData(){
////
////
////        countDownTimer();
////        countDownTimer.start();
////    }
////
////    public void countDownTimer(){
////
////        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
////            public void onTick(long millisUntilFinished) {
////
////                Log.i("PersistentService","onTick");
////            }
////            public void onFinish() {
////
////                Log.i("PersistentService","onFinish");
////            }
////        };
////    }
////
////
////    /**
////     * 알람 매니져에 서비스 등록
////     */
////    private void registerRestartAlarm(){
////
////        Log.i("000 PersistentService" , "registerRestartAlarm" );
////        Intent intent = new Intent(PersistentService.this,RestartService.class);
////        intent.setAction("ACTION.RESTART.PersistentService");
////        PendingIntent sender = PendingIntent.getBroadcast(PersistentService.this,0,intent,0);
////
////        long firstTime = SystemClock.elapsedRealtime();
////        firstTime += 1*1000;
////
////        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
////
////        /**
////         * 알람 등록
////         */
////        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstTime,1*1000,sender);
////
////    }
////
////    /**
////     * 알람 매니져에 서비스 해제
////     */
////    private void unregisterRestartAlarm(){
////
////        Log.i("000 PersistentService" , "unregisterRestartAlarm" );
////
////        Intent intent = new Intent(PersistentService.this,RestartService.class);
////        intent.setAction("ACTION.RESTART.PersistentService");
////        PendingIntent sender = PendingIntent.getBroadcast(PersistentService.this,0,intent,0);
////
////        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
////
////        /**
////         * 알람 취소
////         */
////        alarmManager.cancel(sender);
////
////
////
////    }
