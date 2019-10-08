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
    private Spinner carSp;
    private EditText carIdEt;

    private ToggleButton ctelBtn;
    private Button modifyBtn;
    private Button cancelBtn;

    private String name;
    private String tel;
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
        carSp = (Spinner) rootView.findViewById(R.id.carSp);

        vo = new MemberVO(1, "oea0805", "1234",
                "오은애", "01056113427", 1, "짱짱", "써나타");

        IdTv.setText(vo.getMember_id());
        nameTv.setText(vo.getMember_mname());
        telTv.setText(vo.getMember_phonenumber());
        carSp.setSelection(1);
        carIdEt.setText("소나타");

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

        return rootView;
    }

}
