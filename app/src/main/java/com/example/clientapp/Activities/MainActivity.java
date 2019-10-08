package com.example.clientapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.HttpUtils;
import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
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
                Thread t = new Thread() {
                    public void run() {
                        try {
                            map.put("member_id", member_id);
                            map.put("member_pw", member_pw);
                            String url = "http://70.12.115.57:9090/TestProject/clogin.do";
                            HttpUtils http = new HttpUtils(HttpUtils.POST, map, url, getApplicationContext());
                            res = http.request();
                        } catch (Exception e) {
                            Log.i("MemberLoginError", e.toString());
                        }
                    }
                };
                t.start();
                try {
                    t.join();
                    if (res != null) {
                        ObjectMapper mapper = new ObjectMapper();
                        vo = mapper.readValue(res, MemberVO.class);
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        i.putExtra("vo", vo);
                        i.putExtra("fragment","login");
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

}