package com.example.clientapp.FindIdPwFragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.clientapp.Activities.Main2Activity;
import com.example.clientapp.HttpUtils;
import com.example.clientapp.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PWFragment extends Fragment {
    private Map<String, String> map;
    private Button findPwBtn;
    private EditText fIdEt;
    private EditText fNameEt2;
    private EditText fTelEt2;
    private String res = "";

    public PWFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pw, container, false);
        fIdEt = (EditText) rootView.findViewById(R.id.fIdEt);
        fNameEt2 = (EditText) rootView.findViewById(R.id.fNameEt2);
        fTelEt2 = (EditText) rootView.findViewById(R.id.fTelEt2);

        findPwBtn = (Button) rootView.findViewById(R.id.findPwBtn);
        findPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fIdEt.getText().toString().length() != 0 &&
                        fNameEt2.getText().toString().length() != 0 &&
                        fTelEt2.getText().toString().length() != 0) {
                    map = new HashMap<String, String>();
                    map.put("member_id", fIdEt.getText().toString());
                    map.put("member_mname", fNameEt2.getText().toString());
                    map.put("member_phonenumber", fTelEt2.getText().toString());
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            String url = "http://70.12.115.73:9090/Chavis/Member/findinfo.do";
                            HttpUtils http = new HttpUtils(HttpUtils.POST, map, url, getContext());
                            res = http.request();
                            Log.i("PWFragmentRes", res);
                        }
                    };
                    t.start();
                    try {
                        t.join();
                        if (!res.equals("\"NO\"")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("비밀번호 확인").setMessage("회원님의 비밀번호는 " + res + "입니다.");
                            builder.setNegativeButton("로그인하기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(getContext(), Main2Activity.class));
                                }
                            });
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("입력하신 정보와 일치하는 회원 정보가 없습니다.");
                            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                            builder.show();
                        }
                    } catch (InterruptedException e) {
                        Log.i("PWFragmentError", "PWFragment join 실패");
                    }
                } else {
                    Toast.makeText(getContext(), "입력하지 않은 정보가 있습니다!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return rootView;
    }

}
