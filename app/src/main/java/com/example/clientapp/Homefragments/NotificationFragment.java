package com.example.clientapp.Homefragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import com.example.clientapp.Activities.NotificationListViewAdapter;
import com.example.clientapp.HttpUtils;
import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;
import com.example.clientapp.VO.NotificationVO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    NotificationListViewAdapter nAdapter;
    private ExpandableListView notification_lv;
    private ArrayList<NotificationVO> list;
    private MemberVO mVO;
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
                    String url = "http://70.12.115.73:9090/Chavis/Member/nlist.do?member_id=" + mVO.getMember_id();
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
            int lastClickedPosition = 0;

            nAdapter = new NotificationListViewAdapter(getContext(), list);
            notification_lv.setAdapter(nAdapter);

        }

        notification_lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            int lastClickedPosition = 0;

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Boolean isExpand = (!notification_lv.isGroupExpanded(groupPosition));
                notification_lv.collapseGroup(lastClickedPosition);
                if (isExpand) {
                    notification_lv.expandGroup(groupPosition);
                }
                lastClickedPosition = groupPosition;
                return true;
            }
        });

        return rootView;
    }
}

