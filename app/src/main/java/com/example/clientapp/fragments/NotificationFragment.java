package com.example.clientapp.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import com.example.clientapp.Activities.NotificationListViewAdapter;
import com.example.clientapp.HttpUtils;
import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;
import com.example.clientapp.VO.NotificationVO;
import com.example.clientapp.VO.ReservationVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    NotificationListViewAdapter nAdapter;
    private ExpandableListView notification_lv;
    private ArrayList<NotificationVO> list;
    private MemberVO mVO;
    private NotificationVO nVO;
    private String res = "";

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_notification, container, false);
        notification_lv = (ExpandableListView) rootView.findViewById(R.id.notification_lv);

        Bundle b = getArguments();
        if (b != null)
            mVO = b.getParcelable("vo");

        Thread t = new Thread() {
            public void run() {
                try {

                    String url = "http://70.12.115.73:9090/Chavis/Member/nList.do";
                    HttpUtils http = new HttpUtils(HttpUtils.GET, url, getContext());
                    res = http.request();
                } catch (Exception e) {
                    Log.i("MemberRegisterError", e.toString());
                }
            }
        };
        t.start();

        try {
            t.join();
        } catch (Exception e) {
            Log.i("NotificationError", "Thread join error");
        }

        if (!res.equals("[]")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                list = mapper.readValue(res, new TypeReference<ArrayList<NotificationVO>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            nAdapter = new NotificationListViewAdapter(getContext(), list);
            notification_lv.setAdapter(nAdapter);

//            notification_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView parent, View v, int position, long id) {
//
//                    androidx.appcompat.app.AlertDialog.Builder dialog =
//                            new androidx.appcompat.app.AlertDialog.Builder(getContext());
//                    dialog.setTitle("정비 예약 세부 내역");
//
//                    ReservationVO vo = data.get(position);
//
//                    final List<String> ListItems = new ArrayList<>();
//
//                    ListItems.add("예약 번호  :  " + vo.getReservation_no());
//                    ListItems.add("정비소 ID  :  " + vo.getBodyshop_no());
//                    ListItems.add("정비 예약 시간  :  " + vo.getReservation_time());
//                    ListItems.add(vo.getRepaired_time() == null || vo.getRepaired_time().equals("0") ? ("정비 완료 시간  :  정비중") : ("정비 완료 시간  :  " + vo.getRepaired_time()));
//                    ListItems.add("KEY 동의 여부  :  " + (vo.getKey().equals("1") ? "O" : "X"));
//                    ListItems.add("담당 정비사  :  " + vo.getRepaired_person());
//
//                    final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);
//
//                    dialog.setItems(items, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int pos) {
//                            return;
//                        }
//                    });
//
//                    dialog.setPositiveButton("확 인", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            return;
//                        }
//                    });
//                    dialog.show();
//                }
//            });
        }


        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

}
