package com.example.clientapp.Homefragments;


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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {
    private MemberVO vo;
    TextView per_tire, per_wiper, per_oil, per_cool, per_dis, caridTv;
    Button go_reservebtn;
    String reserokdata;
    String TIRE_CHANGE_DISTANCE = "0", WIPER_CHANGE_DISTANCE = "0", ENGINE_OIL_VISCOSITY = "0", DISTANCE = "0", COOLER_LEFT = "0";


    // 교체 권장 거리
    String change_TIRE_CHANGE_DISTANCE = "60000", change_WIPER_CHANGE_DISTANCE = "6000";

    public StatusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_status, container, false);

        Bundle b = getArguments();
        vo = b.getParcelable("vo");

        go_reservebtn = (Button) rootView.findViewById(R.id.go_reservebtn);
        per_tire = (TextView) rootView.findViewById(R.id.tireper);
        per_wiper = (TextView) rootView.findViewById(R.id.wiperper);
        per_oil = (TextView) rootView.findViewById(R.id.engineper);
        per_cool = (TextView) rootView.findViewById(R.id.coolper);
        per_dis = (TextView) rootView.findViewById(R.id.disper);
        caridTv = (TextView) rootView.findViewById(R.id.caridTv);
        caridTv.setText(vo.getCar_id());

        try {
            Thread wThread = new Thread() {      // UI 관련작업 아니면 Thread를 생성해서 처리해야 하는듯... main thread는 ui작업(손님접대느낌) 하느라 바쁨
                public void run() {
                    try {
                        sendPost(vo.getMember_id());
                    } catch (Exception e) {
                        Log.i("Status Error", e.toString());
                    }
                }
            };
            wThread.start();

            try {
                wThread.join();
            } catch (Exception e) {
                Log.i("Status Error", e.toString());
            }
        } catch (Exception e) {
            Log.i("Status Error", e.toString());
        }

        double tire = Double.parseDouble(TIRE_CHANGE_DISTANCE);
        double wiper = Double.parseDouble(WIPER_CHANGE_DISTANCE);
        double engineoil = Double.parseDouble(ENGINE_OIL_VISCOSITY);
        double distance = Double.parseDouble(DISTANCE);
        double cooler = Double.parseDouble(COOLER_LEFT);

        double ch_tire = Double.parseDouble(change_TIRE_CHANGE_DISTANCE);
        double ch_wiper = Double.parseDouble(change_WIPER_CHANGE_DISTANCE);

        int f_tire = (int) (100 - (tire / ch_tire) * 100);
        int f_wiper = (int) (100 - (wiper / ch_wiper) * 100);


        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.pb_tire);
        progressBar.setMax(100);
        progressBar.setProgress(f_tire);
        if (f_tire <= 20) {
            per_tire.setTextColor(getResources().getColor(R.color.progressred));
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        } else if (20 < f_tire && f_tire <= 40) {
            per_tire.setTextColor(getResources().getColor(R.color.progressyellow));
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar3));

        }
        if(f_tire <=0){
            f_tire = 0;
        }
        per_tire.setText(f_tire + "%");

        ProgressBar progressBar2 = (ProgressBar) rootView.findViewById(R.id.pb_cooler);
        progressBar2.setMax(100);
        progressBar2.setProgress((int) cooler);
        if (cooler <= 20) {
            per_cool.setTextColor(getResources().getColor(R.color.progressred));
            progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        } else if (20 < cooler && cooler <= 40) {
            per_cool.setTextColor(getResources().getColor(R.color.progressyellow));
            progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar3));

        }
        if(cooler <= 0){
            cooler = 0;
        }
        per_cool.setText((int) cooler + "%");

        ProgressBar progressBar3 = (ProgressBar) rootView.findViewById(R.id.pb_enginoil);
        progressBar3.setMax(100);
        progressBar3.setProgress((int) engineoil);
        if (engineoil <= 20) {
            per_oil.setTextColor(getResources().getColor(R.color.progressred));
            progressBar3.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        } else if (20 < engineoil && engineoil <= 40) {
            per_oil.setTextColor(getResources().getColor(R.color.progressyellow));
            progressBar3.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar3));

        }
        if(engineoil <=0){
            engineoil = 0;
        }
        per_oil.setText((int) engineoil + "%");


        per_dis.setText((int) distance + "km");

        ProgressBar progressBar5 = (ProgressBar) rootView.findViewById(R.id.pb_wiper);
        progressBar5.setMax(100);
        progressBar5.setProgress(f_wiper);
        if (f_wiper <= 20) {
            per_wiper.setTextColor(getResources().getColor(R.color.progressred));
            progressBar5.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar2));
        } else if (20 < f_wiper && f_wiper <= 40) {
            per_wiper.setTextColor(getResources().getColor(R.color.progressyellow));
            progressBar5.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_progressbar3));

        }
        if(f_wiper <= 0){
            f_wiper = 0;
        }
        per_wiper.setText(f_wiper + "%");


        go_reservebtn.setOnClickListener(new View.OnClickListener() {
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
        osw.write(json);
        osw.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            Log.i("StatusFra_receive", response.toString());
        }
        receivedata = response.toString();
        in.close();

        CarVO myObject = mapper.readValue(receivedata, new TypeReference<CarVO>() {
        });

        TIRE_CHANGE_DISTANCE = myObject.getTire_change_distance();
        WIPER_CHANGE_DISTANCE = myObject.getWiper_change_distance();
        ENGINE_OIL_VISCOSITY = myObject.getEngine_oil_viscosity();
        DISTANCE = myObject.getDistance();
        COOLER_LEFT = myObject.getCooler_left();

        reserokdata = receivedata;
    }

}
