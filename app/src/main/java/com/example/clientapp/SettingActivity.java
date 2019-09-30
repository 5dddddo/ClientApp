package com.example.clientapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    private TextView IdTv;
    private EditText nameEt;
    private TextView nameTv;
    private ToggleButton cnameBtn;
    private EditText telEt;
    private TextView telTv;
    private ToggleButton ctelBtn;
    private Button cancelBtn;

    private String name;
    private String client_id;
    private String tel;

    ClientService cs; // 서비스
    boolean isService = false; // 서비스 실행 확인

    ServiceConnection conn = new ServiceConnection() {
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            // 서비스와 연결되었을 때 호출되는 메서드
            ClientService.ClientServiceBinder cb =
                    (ClientService.ClientServiceBinder) service;
            cs = cb.getService();
            isService = true; // 실행 여부를 판단
        }
        public void onServiceDisconnected(ComponentName name) {
            // 서비스와 연결이 끊기거나 종료되었을 때
            isService = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        IdTv = (TextView) findViewById(R.id.IdTv);
        nameEt = (EditText) findViewById(R.id.nameEt);
        nameTv = (TextView) findViewById(R.id.nameTv);
        cnameBtn = (ToggleButton) findViewById(R.id.cnameBtn);
        telEt = (EditText) findViewById(R.id.telEt);
        telTv = (TextView) findViewById(R.id.telTv);
        ctelBtn = (ToggleButton) findViewById(R.id.ctelBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        cnameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cnameBtn.isChecked()) {
                    nameTv.setVisibility(View.GONE);
                    nameEt.setVisibility(View.VISIBLE);
                } else {
                    if (nameEt.getText().length() != 0)
                        name = nameEt.getText().toString();
                    nameTv.setText(name);
                    nameTv.setVisibility(View.VISIBLE);
                    nameEt.setVisibility(View.GONE);

                }
            }
        });
        ctelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ctelBtn.isChecked()) {
                    telTv.setVisibility(View.GONE);
                    telEt.setVisibility(View.VISIBLE);
                } else {
                    if (telEt.getText().length() != 0)
                        tel = telEt.getText().toString();
                    telTv.setText(tel);
                    telTv.setVisibility(View.VISIBLE);
                    telEt.setVisibility(View.GONE);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(getApplicationContext(), HomeActivity.class);
                backIntent.putExtra("client_id", client_id);
                startActivity(backIntent);
            }
        });
    }
}
