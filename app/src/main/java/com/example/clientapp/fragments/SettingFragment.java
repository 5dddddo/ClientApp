package com.example.clientapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;

public class SettingFragment extends Fragment {

    private TextView IdTv;
    private EditText nameEt;
    private TextView nameTv;
    private ToggleButton cnameBtn;
    private EditText telEt;
    private TextView telTv;
    private EditText carIdEt;

    private ToggleButton ctelBtn;
    private Button modifyBtn;
    private Button cancelBtn;


    private String mId;
    private String mPw;
    private String mName;
    private String mTel;
    private String mCarType;
    private String mCarId;
    private String mCarColor;
    private MemberVO vo;

    public SettingFragment() {
        // Required empty public constructor
    }

    public SettingFragment(MemberVO vo) {
        // Required empty public constructor
        this.vo = vo;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
        IdTv = (TextView) rootView.findViewById(R.id.IdTv);
        nameEt = (EditText) rootView.findViewById(R.id.nameEt);
        nameTv = (TextView) rootView.findViewById(R.id.nameTv);
        cnameBtn = (ToggleButton) rootView.findViewById(R.id.cnameBtn);
        telEt = (EditText) rootView.findViewById(R.id.telEt);
        telTv = (TextView) rootView.findViewById(R.id.telTv);
        carIdEt = (EditText) rootView.findViewById(R.id.carIdEt);
        ctelBtn = (ToggleButton) rootView.findViewById(R.id.ctelBtn);
        cancelBtn = (Button) rootView.findViewById(R.id.cancelBtn);
        modifyBtn = (Button) rootView.findViewById(R.id.modifyBtn);

        Bundle b = getArgument();

        if (bundle != null) {
        	vo = b.getParcelable("vo");
        }
        
        IdTv.setText(vo.getMember_id());
        nameTv.setText(vo.getMember_mname());
        telTv.setText(vo.getMember_phonenumber());
        carIdEt.setText(vo.getCar_id());

        cnameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cnameBtn.isChecked()) {
                    nameTv.setVisibility(View.GONE);
                    nameEt.setVisibility(View.VISIBLE);
                } else {
                    if (nameEt.getText().length() != 0)
                        name = nameEt.getText().toString();
                    nameTv.setText(name);
                    nameTv.setVisibility(View.VISIBLE);
                    nameEt.setVisibility(View.GONE);

                }
            }
        });
        ctelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ctelBtn.isChecked()) {
                    telTv.setText(tel);
                    telTv.setVisibility(View.GONE);
                    telEt.setVisibility(View.VISIBLE);
                } else {
                    telTv.setVisibility(View.VISIBLE);
                    telEt.setVisibility(View.GONE);
                    if (telEt.getText().length() != 0)
                        tel = telEt.getText().toString();
                }
            }
        });
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInputComplete()) {
                    Thread t = new Thread() {
                        public void run() {
                            try {
                                map = new HashMap<String, String>();
                                map.put("member_pw", mPw);
                                map.put("member_id", mId);
                                map.put("member_mname", mName);
                                map.put("member_phonenumber", mTel);
                                map.put("car_type", mCarType);
                                map.put("car_color", mCarColor);
                                map.put("car_id", mCarId);
                                String url = "http://70.12.115.57:9090/TestProject/update.do";
                                HttpUtils http = new HttpUtils(HttpUtils.POST, map, url, getApplicationContext());
                                res = http.request();
                            } catch (Exception e) {
                                Log.i("MemberRegisterError", e.toString());
                            }
                        }
                    };
                    t.start();
                    try {
                        t.join();
                        if (res.equals("true")) {
                            Toast.makeText(getApplicationContext(), "회원정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                            // Settingfragment 띄우기
                        } else {
                            Toast.makeText(getApplicationContext(), "회원정보 수정 실패", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }

        });
        return rootView;
    }

}
