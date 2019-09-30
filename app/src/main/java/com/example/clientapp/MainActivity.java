package com.example.clientapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    String client_id;
    String password;

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
                client_id = idEditView.getText().toString();
                password = pwEditView.getText().toString();
                Intent i = new Intent(
                        getApplicationContext(),// Activiey Context
                        ClientService.class);
                i.putExtra("client_id", client_id);
                i.putExtra("password", password);
                startService(i);
            }
        });

        addUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.clientapp", "com.example.clientapp.RegisterActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });

        findIdPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  ID/PW 찾기
                Intent i = new Intent();
                ComponentName cname = new ComponentName("com.example.clientapp", "com.example.clientapp.SendActivity");
                i.setComponent(cname);
                startActivity(i);
            }
        });
    }

}
