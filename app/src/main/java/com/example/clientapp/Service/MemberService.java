package com.example.clientapp.Service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MemberService extends Service {
    public MemberService() {
    }

    IBinder mBinder = new MyBinder();


//    class ClientSendRunnable implements Runnable {
//        BlockingQueue blockingQueue;
//
//        ClientSendRunnable(BlockingQueue blockingQueue) {
//            this.blockingQueue = blockingQueue;
//        }
//
//        @Override
//        public void run() {
//            while (true) {
//                try {
//
//                    String msg = (String) blockingQueue.take();
//                    Log.i("ChattingClient", "blocking queue send: " + msg);
//                    out.println(msg);
//                    out.flush();
//                } catch (Exception e) {
//                    Log.i("ChattingClientError", "blocking queue 문제 : " + e.toString());
//                }
//            }
//
//
//        }
//    }

//    class ClientReceiveRunnable implements Runnable {
//
//        Intent receiveIntent = new Intent();
//
//        @Override
//        public void run() {
//            try {
//                try {
//                    socket = new Socket("70.12.115.63", 6767);
//                    Log.i("BodyShopService", "서버 연결 성공!!");
//                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    out = new PrintWriter(socket.getOutputStream());
//                } catch (Exception e) {
//                    Log.i("BodyShopServiceError", "서버 연결 실패 : " + e.toString());
//                }
//
//                out.println("BodyShopNO#" + bodyShopDTO.getBodyshop_no());
//                out.flush();
//                String s = "";
//
//                while ((s = br.readLine()) != null) {
//                    Log.i("BodyShopService", "서버로 받는 데이터 : " + s);
//                    String msg[] = s.split("#");
//                    if (msg[0].equals("Key")) {
//                        Log.i("BodyShopService_KEY", msg[1]);
//                        blockingQueue.add("Key#" + msg[1]);
//                    } else if (msg[0].equals("RepairFinishResult")) {
//                        receiveIntent = new Intent();
//                        ComponentName componentName = new ComponentName("com.example.myapplication", "com.example.myapplication.CarKeyActivity");
//                        Log.i("BodyShopService_Result", msg[1]);
//                        receiveIntent.putExtra("repairedResult", msg[1]);
//                        receiveIntent.setComponent(componentName);
//                        receiveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        receiveIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        receiveIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(receiveIntent);
//                    }
//
//                }
//            } catch (Exception e) {
//                Log.i("BodyShopServiceError", "읽기 문제 : " + e.toString());
//            }
//
//        }
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.i("service", "서비스 시작");

    }

    class MyBinder extends Binder {
        MemberService getService() {
            return MemberService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행

        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
//        String myObject = preferences.getString("myObject", "NO");
//
//        Gson gson = new Gson();
//        bodyShopDTO = gson.fromJson(myObject, BodyShopDTO.class);
//
//        ClientReceiveRunnable receiveRunnable = new ClientReceiveRunnable();
//        ClientSendRunnable sendRunnable = new ClientSendRunnable(blockingQueue);
//        Thread thread1 = new Thread(receiveRunnable);
//        Thread thread2 = new Thread(sendRunnable);
//        thread1.start();
//        thread2.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        // 서비스가 종료될 때 실행

    }
}
