package com.example.clientapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.Activities.RegisterActivity;
import com.example.clientapp.VO.MemberVO;
import com.example.clientapp.VO.ReservationVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    String member_id;
    String member_pw;
    MemberVO vo;

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
                try {
                    Thread t = new Thread() {
                        public void run() {
                            try {
                                login(member_id, member_pw);
                                Log.i("msi", member_id);
                            } catch (Exception e) {
                                Log.i("msi", e.toString());
                            }
                        }
                    };
                    t.start();
                    try {
                        t.join();
                    } catch (Exception e) {
                        Log.i("msi", "이상이상22");
                    }
                } catch (Exception e) {
                    Log.i("msi", e.toString());
                }

                login(member_id, member_pw);

                Log.i("여기서죽어?", "이상이상22");
                if (vo != null) {
                    Intent i = new Intent(
                            getApplicationContext(),
                            HomeActivity.class);
                    i.putExtra("vo", vo);
                    startActivity(i);
                }
//                Intent i = new Intent(
//                        getApplicationContext(),// Activiey Context
//                        HomeActivity.class);
//
//                i.putExtra("member_id", member_id);
//                i.putExtra("member_pw", member_pw);
//                startActivity(i);
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

    public void login(String member_id, String member_pw) {
        try {
            URL url = new URL("http://70.12.115.57:9090/TestProject/clogin.do");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("charset", "utf-8");

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            Map<String, String> map = new HashMap<String, String>();

            map.put("id", member_id);
            map.put("pw", member_pw);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(map);
            osw.write(json);
            osw.flush();

            Log.i("MemberLogin", "성공");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer sb = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
            Log.i("MemberLoginVO", sb.toString());

            vo = mapper.readValue(sb.toString(), MemberVO.class);
            Log.i("MemberLoginID", vo.getMember_id());
            return;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
