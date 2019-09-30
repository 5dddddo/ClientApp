package com.example.clientapp;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    ClientService ms; // 서비스
    boolean isService = false; // 서비스 실행 확인
    BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Intent i = getIntent();
        final String client_id = i.getExtras().getString("client_id");
        backPressCloseHandler = new BackPressCloseHandler(this);

        ImageButton settingBtn = (ImageButton) findViewById(R.id.settingBtn);
        ImageButton monitorBtn = (ImageButton) findViewById(R.id.monitorBtn);


        monitorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), client_id, Toast.LENGTH_SHORT).show();

            }
        });

        ServiceConnection conn = new ServiceConnection() {
            public void onServiceConnected(ComponentName name,
                                           IBinder service) {
                // 서비스와 연결되었을 때 호출되는 메서드
                ClientService.MyBinder cb =
                        (ClientService.MyBinder) service;
                ms = cb.getService();
                isService = true; // 실행 여부를 판단
            }

            public void onServiceDisconnected(ComponentName name) {
                // 서비스와 연결이 끊기거나 종료되었을 때
                isService = false;
            }
        };
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentName cname = new ComponentName("com.example.clientapp", "com.example.clientapp.SettingActivity");
                i.setComponent(cname);
                i.putExtra("client_id", client_id);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
