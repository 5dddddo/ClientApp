package com.example.clientapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton settingBtn = (ImageButton) findViewById(R.id.settingBtn);
        ImageButton monitorBtn = (ImageButton) findViewById(R.id.monitorBtn);

        final Intent i = getIntent();
        final String client_id = i.getExtras().getString("client_id");

        monitorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), client_id, Toast.LENGTH_SHORT).show();

            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentName cname = new ComponentName("com.example.clientapp", "com.example.clientapp.SettingActivity");
                i.setComponent(cname);
                i.putExtra("client_id",client_id);
                startActivity(i);
            }
        });

    }
}
