package com.example.clientapp.Activities;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.R;
import com.example.clientapp.VO.BodyshopVO;
import com.example.clientapp.VO.MemberVO;
import com.example.clientapp.VO.ReservationVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
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
    TextView text_date, text_time;
    CheckBox checkBox;
    String[] receive;
    int[] bnolist;

    //    String member_id = "111";     // 가상의 클라이언트 ID
    String date = "", time = "", otpkey = "", reserve_time = "", day = "";
    int body_no;
    String reserokdata;


    class MyReservationRunnable implements Runnable {
        private Handler handler;

        public MyReservationRunnable(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            String receivedata = "";
            URL url = null;
            try {
                url = new URL("http://70.12.115.73:9090/Chavis/Bodyshop/list.do");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("charset", "utf-8");
//                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
//
//                Map<String, String> map = new HashMap<String, String>();
//
//                map.put("Bodyshop", "Bodyshop");
//
//                ObjectMapper mapper = new ObjectMapper();
//                String json = mapper.writeValueAsString(map);
//
//                Log.i("FIRST", "가랏 데이터 : " + json);
//
//                osw.write(json);
//                osw.flush();

                Log.i("FIRST", "데이터 받기 ");
                int responseCode = conn.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                ObjectMapper mapper = new ObjectMapper();
                receivedata = response.toString();
                ArrayList<BodyshopVO> myObject = mapper.readValue(receivedata, new TypeReference<ArrayList<BodyshopVO>>() {
                });
                in.close();

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("Bodyshop_list", myObject);

                Message message = new Message();
                message.setData(bundle);

                handler.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        Intent i = getIntent();
        vo = (MemberVO) i.getExtras().getParcelable("vo");

//        Log.i("vooovoovovoov", vo.getMember_id());
//        member_id = vo.getMember_id();

        textView_Date = (TextView) findViewById(R.id.textView_date);
        textView_Date2 = (TextView) findViewById(R.id.textView_date2);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        this.InitializeListener();

        Button btn = (Button) findViewById(R.id.reserbtn);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);

        text_date = (TextView) findViewById(R.id.textView_date);
        text_time = (TextView) findViewById(R.id.textView_date2);

        checkBox = (CheckBox) findViewById(R.id.checkbox);

        TextView myname = (TextView) findViewById(R.id.myname);
        TextView myphonenumber = (TextView) findViewById(R.id.myphonenumber);
        TextView mycartype = (TextView) findViewById(R.id.mycartype);
        TextView mycarnumber = (TextView) findViewById(R.id.mycarnumber);

        myname.setText(vo.getMember_mname());
        myphonenumber.setText(vo.getMember_phonenumber());
        mycartype.setText(vo.getCar_type());
        mycarnumber.setText(vo.getCar_id());

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                ArrayList<BodyshopVO> result = bundle.getParcelableArrayList("Bodyshop_list");

                receive = new String[result.size()];
                bnolist = new int[result.size()];
                for(int i=0; i<receive.length; i++){
                    receive[i] = result.get(i).getBodyshop_name();
                    bnolist[i] = result.get(i).getBodyshop_no();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item,receive);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);

            }
        };

        MyReservationRunnable myReservationRunnable = new MyReservationRunnable(handler);
        Thread thread = new Thread(myReservationRunnable);
        thread.start();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                body_no = bnolist[i];

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(ReservationActivity.this, R.style.MyDatePickerDialogTheme, callbackMethod, 2019, 10, 8);
                dialog.show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog2 = new TimePickerDialog(ReservationActivity.this, R.style.MyTimePickerDialog, callbackMethod2, 5, 11, false);
                dialog2.show();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (reserve_time.length() == 0 || day.length() == 0) {
                    insertinfoloss();
                    return;

                } else {
                    try {
                        Thread wThread = new Thread() {      // UI 관련작업 아니면 Thread를 생성해서 처리해야 하는듯... main thread는 ui작업(손님접대느낌) 하느라 바쁨
                            public void run() {
                                try {
                                    sendPost(vo.getMember_id());
                                } catch (Exception e) {
                                    Log.i("msi", e.toString());
                                }
                            }
                        };
                        wThread.start();

                        try {
                            wThread.join();
                        } catch (Exception e) {
                            Log.i("msi", "이상이상22");
                        }
                    } catch (Exception e) {
                        Log.i("msi", e.toString());
                    }

                    reserve_ok_dialog();
                }

            }
        });
    }

    private String sendPost(String parameters) throws Exception {

        String receivedata;
        String sendMsg;

//        // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
//        URL url = new URL("http://70.12.115.57:9090/TestProject/reserve.do");
        URL url = new URL("http://70.12.115.73:9090/Chavis/Reservation/add.do");    // 한석햄22

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Type", "application/JSON");      // 한석햄..
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("charset", "utf-8");
        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

        Map<String, String> map = new HashMap<String, String>();

        if (checkBox.isChecked()) {
            otpkey = "1";
        } else {
            otpkey = "0";
        }

        map.put("member_id", vo.getMember_id());
        map.put("reservation_time", day + reserve_time);
        map.put("key", otpkey);

        map.put("bodyshop_no",body_no+"");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        Log.i("msi", "송신하는 데이터 : " + json);

        osw.write(json);
        osw.flush();


        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        receivedata = response.toString();
        in.close();
        Log.i("receivedata", receivedata);

        ArrayList<ReservationVO> myObject = mapper.readValue(receivedata, new TypeReference<ArrayList<ReservationVO>>() {
        });
        for (ReservationVO v : myObject)
            Log.i("##@@@", v.getReservation_no() + "");

        reserokdata = receivedata;

        return receivedata;
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

    void reserve_ok_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("예약이 완료되었습니다");
//        builder.setMessage("AlertDialog Content");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(), "예약 잘 되엇는지 확인하는 엑티비티?로.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("fragment", "reservation");
                intent.putExtra("vo", vo);

                startActivity(intent);
            }

        });
        builder.show();
    }

    void insertinfoloss() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Error");
        alert.setMessage("예약날짜, 시간을 확인해 주세요");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.show();
    }

}
