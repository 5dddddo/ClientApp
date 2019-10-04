package com.example.clientapp.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.example.clientapp.R;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {
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

    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_reservation, container, false);


        textView_Date = (TextView) rootView.findViewById(R.id.textView_date);
        textView_Date2 = (TextView) rootView.findViewById(R.id.textView_date2);

        this.InitializeListener();

        Button btn = (Button) rootView.findViewById(R.id.reserbtn);
        Button button = (Button) rootView.findViewById(R.id.button);
        Button button2 = (Button) rootView.findViewById(R.id.button2);
        text_date = (TextView) rootView.findViewById(R.id.textView_date);
        text_time = (TextView) rootView.findViewById(R.id.textView_date2);
        checkBox = (CheckBox) rootView.findViewById(R.id.checkbox);

        per_tire = (TextView) rootView.findViewById(R.id.tireper);
        per_wiper = (TextView) rootView.findViewById(R.id.wiperper);
        per_oil = (TextView) rootView.findViewById(R.id.engineper);
        per_cool = (TextView) rootView.findViewById(R.id.coolper);
        per_dis = (TextView) rootView.findViewById(R.id.disper);
        ProgressBar progressBar1 = (ProgressBar) rootView.findViewById(R.id.pb_tire);
        ProgressBar progressBar2 = (ProgressBar) rootView.findViewById(R.id.pb_cooler);
        ProgressBar progressBar3 = (ProgressBar) rootView.findViewById(R.id.pb_enginoil);
        ProgressBar progressBar4 = (ProgressBar) rootView.findViewById(R.id.pb_tdistance);
        ProgressBar progressBar5 = (ProgressBar) rootView.findViewById(R.id.pb_wiper);
        progressBar1.setMax(100);
        progressBar1.setProgress(f_tire);
        if (f_tire <= 25) {
            per_tire.setTextColor(Color.RED);
            progressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        }
        per_tire.setText(f_tire + "%");

        progressBar2.setMax(100);
        progressBar2.setProgress(cooler);
        if (cooler <= 25) {
            per_cool.setTextColor(Color.RED);
            progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        }
        per_cool.setText(cooler + "%");

        progressBar3.setMax(100);
        progressBar3.setProgress(engineoil);
        if (engineoil <= 25) {
            per_oil.setTextColor(Color.RED);
            progressBar3.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        }
        per_oil.setText(engineoil + "%");


        progressBar4.setMax(100);
        progressBar4.setProgress(distance);
        per_dis.setText(distance + "km");

        progressBar5.setMax(100);
        progressBar5.setProgress(f_wiper);
        if (f_wiper <= 25) {
            per_wiper.setTextColor(Color.RED);
            progressBar5.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        }
        per_wiper.setText(f_wiper + "%");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Thread wThread = new Thread() {      // UI 관련작업 아니면 Thread를 생성해서 처리해야 하는듯... main thread는 ui작업(손님접대느낌) 하느라 바쁨
                        public void run() {
                            try {
                                sendPost(keyword);
                                Log.i("msi", keyword);
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
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod, 2019, 9, 20);
                dialog.show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog2 = new TimePickerDialog(getContext(), callbackMethod2, 8, 10, false);
                dialog2.show();
            }
        });

        return rootView;
    }

    private String sendPost(String parameters) throws Exception {

        String receivedata;
        String sendMsg;

//        // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
        URL url = new URL("http://70.12.115.57:9090/TestProject/reserve.do");
//        URL url = new URL("http://70.12.115.73:9090/Chavis/Reservation/add.do");    // 한석햄22

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        conn.setRequestProperty("Content-Type", "application/JSON");      // 한석햄..
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("charset", "utf-8");
        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

        Map<String, String> map = new HashMap<String, String>();

        if (checkBox.isChecked()) {
            // TODO : CheckBox is checked.
            otpkey = "1";
        } else {
            // TODO : CheckBox is unchecked.
            otpkey = "0";
        }

        map.put("member_id", keyword);
        map.put("reservation_time", day + reserve_time);
        Log.i("공성나", day + reserve_time);
        map.put("key", otpkey);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        Log.i("msi", "가랏 데이터 : " + json);

//        sendMsg = "id=" + keyword + "&reserve_time=" + reserve_time + "&otpkey=" + otpkey;
//        Log.i("msi",sendMsg);

        osw.write(json);
        osw.flush();


        Log.i("msi", "222");
        int responseCode = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        receivedata = response.toString();
        in.close();
        Log.i("KAKAOBOOKLog22", receivedata);

        ArrayList<ReservationVO> myObject = mapper.readValue(receivedata, new TypeReference<ArrayList<ReservationVO>>() {
        });
        for (ReservationVO v : myObject)
            Log.i("KAKAOBOOKLog@@@", v.getReservation_no() + "");
        Log.i("오은애", "오은애");

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
}


