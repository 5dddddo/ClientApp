package com.example.clientapp.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.clientapp.Activities.ReservationActivity;
import com.example.clientapp.R;
import com.example.clientapp.VO.CarVO;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {
    private MemberVO vo;

    public StatusFragment(MemberVO vo) {
        this.vo = vo;
    }

    private TextView textView_Date;
    private TextView textView_Date2;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private TimePickerDialog.OnTimeSetListener callbackMethod2;
    TextView per_tire, per_wiper, per_oil, per_cool, per_dis;

    String keyword = "111";     // 가상의 클라이언트 ID
    String date = "", time = "", otpkey = "", reserve_time = "", day = "";
    String reserokdata;
    String TIRE_CHANGE_DISTANCE = "58000", WIPER_CHANGE_DISTANCE = "8000", ENGINE_OIL_VISCOSITY = "60", DISTANCE = "90000", COOLER_LEFT = "80";
    String change_TIRE_CHANGE_DISTANCE = "60000", change_WIPER_CHANGE_DISTANCE = "10000";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_status, container, false);

        Button btn = (Button) rootView.findViewById(R.id.go_reservebtn);

        per_tire = (TextView) rootView.findViewById(R.id.tireper);
        per_wiper = (TextView) rootView.findViewById(R.id.wiperper);
        per_oil = (TextView) rootView.findViewById(R.id.engineper);
        per_cool = (TextView) rootView.findViewById(R.id.coolper);
        per_dis = (TextView) rootView.findViewById(R.id.disper);

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

        double tire = Double.parseDouble(TIRE_CHANGE_DISTANCE);
        double wiper = Double.parseDouble(WIPER_CHANGE_DISTANCE);
        int engineoil = Integer.parseInt(ENGINE_OIL_VISCOSITY);
        int distance = Integer.parseInt(DISTANCE);
        int cooler = Integer.parseInt(COOLER_LEFT);

        double ch_tire = Double.parseDouble(change_TIRE_CHANGE_DISTANCE);
        double ch_wiper = Double.parseDouble(change_WIPER_CHANGE_DISTANCE);

        int f_tire = (int) (100 - (tire / ch_tire) * 100);
        int f_wiper = (int) (100 - (wiper / ch_wiper) * 100);


        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.pb_tire);
        progressBar.setMax(100);
        progressBar.setProgress(f_tire);
        if (f_tire <= 25) {
            per_tire.setTextColor(Color.RED);
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        }
        per_tire.setText(f_tire + "%");

        ProgressBar progressBar2 = (ProgressBar) rootView.findViewById(R.id.pb_cooler);
        progressBar2.setMax(100);
        progressBar2.setProgress(cooler);
        if (cooler <= 25) {
            per_cool.setTextColor(Color.RED);
            progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        }
        per_cool.setText(cooler + "%");

        ProgressBar progressBar3 = (ProgressBar) rootView.findViewById(R.id.pb_enginoil);
        progressBar3.setMax(100);
        progressBar3.setProgress(engineoil);
        if (engineoil <= 25) {
            per_oil.setTextColor(Color.RED);
            progressBar3.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        }
        per_oil.setText(engineoil + "%");


        ProgressBar progressBar4 = (ProgressBar) rootView.findViewById(R.id.pb_tdistance);
        progressBar4.setMax(100);
        progressBar4.setProgress(distance);
        per_dis.setText(distance + "km");

        ProgressBar progressBar5 = (ProgressBar) rootView.findViewById(R.id.pb_wiper);
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

                Intent intent = new Intent(getActivity(), ReservationActivity.class);
                intent.putExtra("vo", vo);
                startActivity(intent);
            }
        });



        return rootView;
    }


    private void sendPost(String parameters) throws Exception {

        String receivedata;

        URL url = new URL("http://70.12.115.73:9090/Chavis/Car/personalview.do");    // 한석햄22

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("charset", "utf-8");
        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

        Map<String, String> map = new HashMap<String, String>();


        map.put("member_id", vo.getMember_id());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        Log.i("msi", "내 맴버아이디 가라 : " + json);

        osw.write(json);
        osw.flush();


        Log.i("msi", "22ㅇㅅㅇ2");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        receivedata = response.toString();
        in.close();
        Log.i("msi", receivedata);


//        ArrayList<CarVO> myObject = mapper.readValue(receivedata, new TypeReference<ArrayList<CarVO>>() {
//        });
        CarVO myObject = mapper.readValue(receivedata, new TypeReference<CarVO>() {
        });
        Log.i("msi", myObject.getCar_color());

        TIRE_CHANGE_DISTANCE = myObject.getTire_change_distance();
        Log.i("msi", myObject.getTire_change_distance());
        WIPER_CHANGE_DISTANCE = myObject.getWiper_change_distance();
        Log.i("msi", myObject.getWiper_change_distance());
        ENGINE_OIL_VISCOSITY = myObject.getEngine_oil_viscosity();
        Log.i("msi", myObject.getEngine_oil_viscosity());
        DISTANCE = myObject.getDistance();
        Log.i("msi", myObject.getDistance());
        COOLER_LEFT = myObject.getCooler_left();
        Log.i("msi", myObject.getCooler_left());

        reserokdata = receivedata;
    }


}
