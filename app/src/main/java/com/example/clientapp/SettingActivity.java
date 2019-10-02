package com.example.clientapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class SettingActivity extends AppCompatActivity {
    private TextView IdTv;
    private EditText nameEt;
    private TextView nameTv;
    private ToggleButton cnameBtn;
    private EditText telEt;
    private TextView telTv;
    private Spinner carSp;
    private EditText carIdEt;

    private ToggleButton ctelBtn;
    private Button cancelBtn;

    private String name;
    private String client_id;
    private String tel;

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
        carIdEt = (EditText) findViewById(R.id.carIdEt);
        ctelBtn = (ToggleButton) findViewById(R.id.ctelBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        carSp = (Spinner) findViewById(R.id.carSp);


//        IdTv.setText(clientService.getClientVO().getCLIENT_ID());
//        nameTv.setText(clientService.getClientVO().getCLIENT_NAME());
//        telTv.setText(clientService.getClientVO().getTEL());
////        carSp.setSelection(Integer.parseInt(clientService.getClientVO().getCAR_TYPE()));
//        carSp.setSelection(1);
//        carIdEt.setText(clientService.getClientVO().getCAR_ID());

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

//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent backIntent = new Intent(getApplicationContext(), Hom.class);
//                backIntent.putExtra("client_id", client_id);
//                startActivity(backIntent);
//            }
//        });
    }
}
