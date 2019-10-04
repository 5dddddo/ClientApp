package com.example.clientapp.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.clientapp.Activities.CustomListViewAdapter;
import com.example.clientapp.R;
import com.example.clientapp.VO.ReservationVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    String clientid = "mins1011";
    ArrayList<ReservationVO> data;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);
        final EditText editText = (EditText) rootView.findViewById(R.id.keywordEt);
        try {
            Thread wThread = new Thread() {      // UI 관련작업 아니면 Thread를 생성해서 처리해야 하는듯... main thread는 ui작업(손님접대느낌) 하느라 바쁨
                public void run() {
                    try {
                        receiveD(clientid);
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
            Log.i("msi", "여기가안댐?" + e.toString());
        }

        ListView lv = (ListView) rootView.findViewById(R.id.lv);
        CustomListViewAdapter adapter = new CustomListViewAdapter();
        Log.i("msi123", data.toString());
        for (ReservationVO vo : data) {
            adapter.addItem(vo);
            Log.i("msi411111", vo.getReservation_no() + "");
        }

        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                androidx.appcompat.app.AlertDialog.Builder dialog =
                        new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dialog.setTitle("정비 예약 세부 내역");

                ReservationVO vo = data.get(position);

                final List<String> ListItems = new ArrayList<>();

                ListItems.add("예약 번호 : " + vo.getReservation_no());
                ListItems.add("정비소 ID : " + vo.getBodyshop_no());
                ListItems.add("정비 예약 시간 : " + vo.getReservation_time());
                ListItems.add("정비 완료 시간 : " + vo.getRepaired_time());
                ListItems.add("KEY 동의 여부 : " + (vo.getKey().equals("1") ? "O" : "X"));
                ListItems.add("담당 정비사 :" + vo.getRepaired_person());

                final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int pos) {
                        return;
                    }
                });

                dialog.setPositiveButton("확 인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                dialog.show();
            }
        });


        Button searchBtn = (Button) rootView.findViewById(R.id.searchBtn);   // 앞에 this가 생략할수도있는데 여기 activity에서 찾는거
        // anonymous inner class를 이용한 Event처리 (Android의 전형적인 event처리)
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼을 눌렀을 때 서비스를 생성하고 실행.

            }
        });

        return rootView;
    }

    private void receiveD(String clientid) throws Exception {

        String receivedata;

//        // 접속할 서버 주소 (이클립스에서 android.jsp 실행시 웹브라우저 주소)
        String r_url = "http://70.12.115.57:9090/TestProject/rlist.do?id=" + clientid;
//        String r_url = "http://70.12.115.73:9090/Chavis/Member/view.do?member_id=" + clientid;  // 한석햄
//        String r_url = "http://70.12.115.73:9090/Chavis/Reservation/add.do";

        URL url = new URL(r_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestMethod("GET");
        conn.setDoInput(true);


        String inputLine = "";
        StringBuffer sb = new StringBuffer();
        // stream을 통해 data 읽어오기
        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
            Log.i("KAKAOBOOKLog", inputLine);
        }
        receivedata = sb.toString();
        br.close();
        Log.i("KAKAOBOOKLog22", receivedata);

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<ReservationVO> myObject = mapper.readValue(receivedata, new TypeReference<ArrayList<ReservationVO>>() {
        });

        data = myObject;
        for (ReservationVO v : myObject)
            Log.i("KAKAOBOOKLog@@@", v.getReservation_no() + "");
        Log.i("오은애", "오은애");
    }

}
