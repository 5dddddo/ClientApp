package com.example.clientapp.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.HttpUtils;
import com.example.clientapp.PersistentService;
import com.example.clientapp.R;
import com.example.clientapp.RestartService;
import com.example.clientapp.VO.MemberVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private RestartService restartService;

    String member_id;
    String member_pw;
    String res = null;
    MemberVO vo;
    Map<String, String> map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        Button findIdPwBtn = (Button) findViewById(R.id.findIdPwBtn);
        Button addUserBtn = (Button) findViewById(R.id.addUserBtn);

        final EditText idEditView = (EditText) findViewById(R.id.idEditView);
        final EditText pwEditView = (EditText) findViewById(R.id.pwEditView);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                member_id = idEditView.getText().toString();
                member_pw = pwEditView.getText().toString();
                if (member_id.length() != 0 && member_pw.length() != 0) {
                    Thread t = new Thread() {
                        public void run() {
                            try {
                                map.put("member_id", member_id);
                                map.put("member_pw", member_pw);
                                String url = "http://70.12.115.73:9090/Chavis/Member/login.do";
                                HttpUtils http = new HttpUtils(HttpUtils.POST, map, url, getApplicationContext());
                                res = http.request();
                                Log.i("dlakfjalekjf;qiwejf",res);
                            } catch (Exception e) {
                                Log.i("MemberLoginError", e.toString());
                            }
                        }
                    };
                    t.start();
                    try {
                        t.join();
                        if (!res.equals("null")) {
                            ObjectMapper mapper = new ObjectMapper();
                            vo = mapper.readValue(res, MemberVO.class);


//                            initData();
//                            Log.i("st","000");

                            // 서비스 실행
//                            Intent servicei = new Intent();
//                            ComponentName sComponentName = new ComponentName("com.example.clientapp", "com.example.clientapp.ClientService");
//                            servicei.setComponent(sComponentName);
//                            servicei.putExtra("mNo",vo.getMember_no()+"");
//                            startService(servicei);
//                            Log.i("st","1234");


                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            i.putExtra("vo", vo);
                            i.putExtra("fragment", "login");
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            }

        });

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입
                Intent i = new Intent(
                        getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
            }
        });

        findIdPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  ID/PW 찾기
                Intent i = new Intent(
                        getApplicationContext(),// Activiey Context
                        HomeActivity.class);
                startActivity(i);
            }
        });
    }



//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        Log.i("MainActivity","onDestroy");
//        //브로드 캐스트 해제
//        unregisterReceiver(restartService);
//    }

    private void initData(){

        //리스타트 서비스 생성
        restartService = new RestartService();
        intent = new Intent(MainActivity.this, PersistentService.class);
        Log.i("얍","12");


        IntentFilter intentFilter = new IntentFilter("com.example.clientapp.PersistentService");
        Log.i("얍","34");
        //브로드 캐스트에 등록
        registerReceiver(restartService,intentFilter);
        Log.i("얍","56");
        // 서비스 시작
        startService(intent);
        Log.i("얍","78");
    }

}