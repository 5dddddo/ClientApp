package com.example.clientapp.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;
import com.example.clientapp.VO.ReservationVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReservationActivity extends AppCompatActivity {
    private MemberVO vo;

    private TextView textView_Date;
    private TextView textView_Date2;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private TimePickerDialog.OnTimeSetListener callbackMethod2;
    TextView text_date, text_time, per_tire, per_wiper, per_oil, per_cool, per_dis;
    CheckBox checkBox;

    String keyword = "111";     // 가상의 클라이언트 ID
    String date = "", time = "", otpkey = "", reserve_time = "", day = "";
    String reserokdata;
    String TIRE_CHANGE_DISTANCE = "58000", WIPER_CHANGE_DISTANCE = "8000", ENGINE_OIL_VISCOSITY = "60", DISTANCE = "90000", COOLER_LEFT = "80";
    String change_TIRE_CHANGE_DISTANCE = "60000", change_WIPER_CHANGE_DISTANCE = "10000";

    double tire = Double.parseDouble(TIRE_CHANGE_DISTANCE);
    double wiper = Double.parseDouble(WIPER_CHANGE_DISTANCE);
    int engineoil = Integer.parseInt(ENGINE_OIL_VISCOSITY);
    int distance = Integer.parseInt(DISTANCE);
    int cooler = Integer.parseInt(COOLER_LEFT);

    double ch_tire = Double.parseDouble(change_TIRE_CHANGE_DISTANCE);
    double ch_wiper = Double.parseDouble(change_WIPER_CHANGE_DISTANCE);

    int f_tire = (int) (100 - (tire / ch_tire) * 100);
    int f_wiper = (int) (100 - (wiper / ch_wiper) * 100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Intent i = getIntent();
        vo = (MemberVO) i.getExtras().getParcelable("vo");
        Log.i("vooovoovovoov", vo.getMember_id());

        textView_Date = (TextView) findViewById(R.id.textView_date);
        textView_Date2 = (TextView) findViewById(R.id.textView_date2);

        this.InitializeListener();


        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        text_date = (TextView) findViewById(R.id.textView_date);
        text_time = (TextView) findViewById(R.id.textView_date2);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getApplicationContext(), callbackMethod, 2019, 9, 20);
                dialog.show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog2 = new TimePickerDialog(getApplicationContext(), callbackMethod2, 8, 10, false);
                dialog2.show();
            }
        });
    }

    public void InitializeListener() {
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                String m = "" + monthOfYear, d = "" + dayOfMonth;
                if (m.length() == 1) {
                    m = "0" + m;
                }
                if (d.length() == 1) {
                    d = "0" + d;
                }
                textView_Date.setText(year + "-" + m + "-" + d);
                day = year + "-" + m + "-" + d + " ";
            }
        };

        callbackMethod2 = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String h = "" + hourOfDay, m = "" + minute;
                if (h.length() == 1) {
                    h = "0" + h;
                }
                if (m.length() == 1) {
                    m = "0" + m;
                }
                textView_Date2.setText(h + ":" + m);
                reserve_time = h + ":" + m;
            }
        };

    }

}
