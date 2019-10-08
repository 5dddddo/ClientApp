package com.example.clientapp.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.clientapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    public NotificationFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notification, container, false);


        per_tire = (TextView) rootView.findViewById(R.id.tireper);
        per_wiper = (TextView) rootView.findViewById(R.id.wiperper);
        per_oil = (TextView) rootView.findViewById(R.id.engineper);
        per_cool = (TextView) rootView.findViewById(R.id.coolper);
        per_dis = (TextView) rootView.findViewById(R.id.disper);


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




        return rootView;
    }

}
