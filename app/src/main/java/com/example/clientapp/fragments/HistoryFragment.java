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
import com.example.clientapp.HttpUtils;
import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;
import com.example.clientapp.VO.ReservationVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private ArrayList<ReservationVO> data;
    private MemberVO vo;
    private String res = "";

    public HistoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_history, container, false);
        final EditText editText = (EditText) rootView.findViewById(R.id.keywordEt);
        ListView lv = (ListView) rootView.findViewById(R.id.notification_lv);
        Bundle b = getArguments();
        vo = b.getParcelable("vo");

        try {
            Thread t = new Thread() {      // UI 관련작업 아니면 Thread를 생성해서 처리해야 하는듯... main thread는 ui작업(손님접대느낌) 하느라 바쁨
                public void run() {
                    try {
                        String url = "http://70.12.115.73:9090/Chavis/Member/rlist.do?id=" + vo.getMember_id();
                        HttpUtils http = new HttpUtils(HttpUtils.GET, url, getContext());
                        res = http.request();
//                        receiveD(vo.getMember_id());
                    } catch (Exception e) {
                        Log.i("msi", e.toString());
                    }
                }
            };
            t.start();

            try {
                t.join();
            } catch (Exception e) {
                Log.i("msi", "이상이상22");
            }
        } catch (Exception e) {
            Log.i("msi", "여기가안댐?" + e.toString());
        }
        if (!res.equals("[]")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                data = mapper.readValue(res, new TypeReference<ArrayList<RepairedListVO>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            HistoryListViewAdapter adapter = new HistoryListViewAdapter();
            for (ReservationVO vo : data) {
                adapter.addItem(vo);
            }
            lv.setAdapter(adapter);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id) {

                    androidx.appcompat.app.AlertDialog.Builder dialog =
                            new androidx.appcompat.app.AlertDialog.Builder(getContext());
                    dialog.setTitle("정비 예약 세부 내역");

                    RepairedListVO vo = data.get(position);

                    final List<String> ListItems = new ArrayList<>();

                    ListItems.add("예약 번호  :  " + vo.getReservation_no());
                    ListItems.add("정비소 이름  :  " + vo.getBodyshop_name());
                    ListItems.add("정비 예약 시간  :  " + vo.getReservation_time());
                    ListItems.add("KEY 동의 여부  :  " + (vo.getKey().equals("1") ? "O" : "X"));

                    if (vo.getTire() == null){
                        ListItems.add("정비 중");
                    }
                    else {
                        String a[] = {vo.getTire(), vo.getCooler(), vo.getEngine_oil(), vo.getWiper()};
                        ListItems.add("정비 완료 시간  :  " + vo.getRepaired_time());
                        ListItems.add("담당 정비사  :  " + vo.getRepaired_person());
                        String s = CheckRepairedList(a);
                        ListItems.add("정비 내역 : " + s);
                    }

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
        }

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

    public String CheckRepairedList(String[] a){

        String s = "";

        if (a[0].equals("O")){
            s += "타이어 교체, ";
        }
        if (a[1].equals("O")){
            s += "냉각수 교체, ";
        }
        if (a[2].equals("O")){
            s += "엔진오일 교체, ";
        }
        if (a[3].equals("O")){
            s += "와이퍼 교체, ";
        }




        return s.substring(0,s.length()-2);
    }

}
