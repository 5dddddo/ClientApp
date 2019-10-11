package com.example.clientapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;

import java.util.Map;

public class SettingFragment extends Fragment {
    private MemberVO vo;
    private Map<String, String> map;

    private EditText idEt;
    private EditText pwEt;
    private EditText cPwEt;
    private EditText nameEt;
    private EditText telEt;
    private EditText carTypeEt;
    private EditText carColorEt;
    private EditText carIdEt;

    private TextView vid;
    private TextView vpw;
    private TextView vcpw;
    private TextView vname;
    private TextView vtel;
    private TextView vtype;
    private TextView vcolor;
    private TextView vcarid;

    private ToggleButton pwBtn;
    private ToggleButton nameBtn;
    private ToggleButton telBtn;
    private ToggleButton typeBtn;
    private ToggleButton colorBtn;
    private ToggleButton cidBtn;

    private Button modifyBtn;
    private Button cancelBtn;

    private String mId;
    private String mPw;
    private String mName;
    private String mTel;
    private String mCarType;
    private String mCarId;
    private String mCarColor;

//    private boolean isMember_idValid = false;
//    private boolean isDupValid = false;
//    private boolean isMember_pwValid = false;
//    private boolean isMember_pwchkValid = false;
//    private boolean isMember_nameValid = false;
//    private boolean isMember_telValid = false;
//    private boolean isMember_cartypeValid = false;
//    private boolean isMember_caridValid = false;
//    private boolean isMember_carcolorValid = false;

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
        final RelativeLayout r = (RelativeLayout) rootView.findViewById(R.id.relativePw);
        final TextView cpwtv = (TextView) rootView.findViewById(R.id.cpwtv);
        idEt = (EditText) rootView.findViewById(R.id.idEt);
        pwEt = (EditText) rootView.findViewById(R.id.pwEt);
        cPwEt = (EditText) rootView.findViewById(R.id.cPwEt);
        nameEt = (EditText) rootView.findViewById(R.id.nameEt);
        telEt = (EditText) rootView.findViewById(R.id.telEt);
        carTypeEt = (EditText) rootView.findViewById(R.id.carTypeEt);
        carColorEt = (EditText) rootView.findViewById(R.id.carColorEt);
        carIdEt = (EditText) rootView.findViewById(R.id.carIdEt);

        vid = (TextView) rootView.findViewById(R.id.vid);
        vpw = (TextView) rootView.findViewById(R.id.vpw);
        vcpw = (TextView) rootView.findViewById(R.id.vcpw);
        vname = (TextView) rootView.findViewById(R.id.vname);
        vtel = (TextView) rootView.findViewById(R.id.vtel);
        vtype = (TextView) rootView.findViewById(R.id.vtype);
        vcolor = (TextView) rootView.findViewById(R.id.vcolor);
        vcarid = (TextView) rootView.findViewById(R.id.vcarid);

        pwBtn = (ToggleButton) rootView.findViewById(R.id.pwBtn);
        nameBtn = (ToggleButton) rootView.findViewById(R.id.nameBtn);
//        telBtn = (ToggleButton) rootView.findViewById(R.id.telBtn);
//        typeBtn = (ToggleButton) rootView.findViewById(R.id.typeBtn);
//        colorBtn = (ToggleButton) rootView.findViewById(R.id.colorBtn);
//        cidBtn = (ToggleButton) rootView.findViewById(R.id.cidBtn);

        cancelBtn = (Button) rootView.findViewById(R.id.cancelBtn);
        modifyBtn = (Button) rootView.findViewById(R.id.modifyBtn);

        Bundle b = getArguments();
        if (b != null)
            vo = b.getParcelable("vo");

        setMemberInfo();

        pwBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    r.setVisibility(View.VISIBLE);
                    cpwtv.setVisibility(View.VISIBLE);
                } else {

                }
            }
        });
        nameBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nameEt.setFocusable(true);
                    nameEt.setFocusableInTouchMode(true);
                    nameEt.setEnabled(true);
                } else {
                    nameBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.pencil));
                    if (nameEt.getText().length() != 0)
                        mName = nameEt.getText().toString();
//                    nameEt.setText(mName);
                }
            }
        });


//        modifyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isInputComplete()) {
//                    Thread t = new Thread() {
//                        public void run() {
//                            try {
//                                map = new HashMap<String, String>();
//                                map.put("member_pw", mPw);
//                                map.put("member_id", mId);
//                                map.put("member_mname", mName);
//                                map.put("member_phonenumber", mTel);
//                                map.put("car_type", mCarType);
//                                map.put("car_color", mCarColor);
//                                map.put("car_id", mCarId);
//                                String url = "http://70.12.115.57:9090/TestProject/update.do";
//                                HttpUtils http = new HttpUtils(HttpUtils.POST, map, url, getContext());
//                                res = http.request();
//                            } catch (Exception e) {
//                                Log.i("MemberRegisterError", e.toString());
//                            }
//                        }
//                    };
//                    t.start();
//                    try {
//                        t.join();
//                        if (res.equals("true")) {
//                            Toast.makeText(getContext(), "회원정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
//                            // Settingfragment 띄우기
//                        } else {
//                            Toast.makeText(getContext(), "회원정보 수정 실패", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(getContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMemberInfo();
            }
        });

        return rootView;
    }

    public void setMemberInfo() {
        idEt.setText(vo.getMember_id());
        pwEt.setText(vo.getMember_pw());
        nameEt.setText(vo.getMember_mname());
        telEt.setText(vo.getMember_phonenumber());
        carTypeEt.setText(vo.getCar_type());
        carColorEt.setText(vo.getCar_color());
        carIdEt.setText(vo.getCar_id());
    }

}
