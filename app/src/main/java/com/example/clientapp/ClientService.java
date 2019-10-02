package com.example.clientapp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.example.clientapp.VO.MemberVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class ClientService extends Service {

    SharedPreferences pref;

    public ClientService() {
    }

    public class ClientRunnable implements Runnable {
        String id;
        String pw;

        public ClientRunnable(String id, String pw) {
            this.id = id;
            this.pw = pw;
        }

        @Override
        public void run() {
            try {
                Log.i("login", id + "  " + pw);
                URL url = new URL("http://70.12.115.57:9090/TestProject/clogin.do?id=" + id + "&pw=" + pw);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("charset", "utf-8");

                Log.i("ClientService", "Http connection 됐다!!");
                con.setRequestMethod("GET");
                con.setDoInput(true);

                String input = "";
                StringBuffer sb = new StringBuffer();
                // stream을 통해 data 읽어오기
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                while ((input = br.readLine()) != null) {
                    sb.append(input);
                }
                Log.i("data", sb.toString());
                JSONObject jsonObject = new JSONObject(sb.toString());
                br.close();
                con.disconnect();

                ObjectMapper mapper = new ObjectMapper();
                //jackson library를 이용하여 json 문자열을 String[] 형태로 변환
                MemberVO vo = mapper.readValue(jsonObject.toString(), MemberVO.class);
                Intent resultIntent;
                if (vo == null) {
                    resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                } else {
                    resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    resultIntent.putExtra("vo",vo);
//                    savePreferences(id, );
                }
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(resultIntent);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        // 서비스 객체가 만들어지는 시점에 1번 호출
        // 사용할 resource를 준비하는 과정
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ClientService", "onStartCommand 호출됐어요!!");
        String Member_id = intent.getExtras().getString("Member_id");
        String Member_pw = intent.getExtras().getString("Member_pw");
        if (intent == null) {
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.putExtra("ClientServiceError", "서비스 에러!");
            // flag에 의해 메모리에 이미 떠있는 Activity는 onCreate() 호출하지 않고
            // 생성되어 있는 Activity 가져옴
            startActivity(resultIntent);
        } else {
            ClientRunnable runnable = new ClientRunnable(Member_id, Member_pw);
            Thread t = new Thread(runnable);
            t.start();
        }
        // 강제 종료되면 자동적으로 재시작함
        return Service.START_STICKY;

    }

    @Override
    public void onDestroy() {
        // 서비스 객체가 메모리상에서 삭제될 때 1번 호출
        // 사용한 resource를 정리하는 과정
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
