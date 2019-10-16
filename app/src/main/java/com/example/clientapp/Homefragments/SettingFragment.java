package com.example.clientapp.Homefragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.clientapp.HttpUtils;
import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static com.example.clientapp.HttpUtils.isCarIdVaild;
import static com.example.clientapp.HttpUtils.isCarTypeVaild;
import static com.example.clientapp.HttpUtils.isNameVaild;
import static com.example.clientapp.HttpUtils.isPwVaild;
import static com.example.clientapp.HttpUtils.isTelVaild;

public class SettingFragment extends Fragment {
    private MemberVO vo;
    private Map<String, String> map;
    private String res;

    private EditText pwEt;
    private EditText cPwEt;
    private EditText nameEt;
    private EditText telEt;
    private EditText carTypeEt;
    private EditText carColorEt;
    private EditText carIdEt;

    private TextView idTv;
    private TextView pwTv;
    private TextView nameTv;
    private TextView telTv;
    private TextView cartypeTv;
    private TextView colorTv;
    private TextView cidTv;

    private TextView vpw;
    private TextView vcpw;
    private TextView vname;
    private TextView vtel;
    private TextView vtype;
    private TextView vcolor;
    private TextView vcarid;
    private ImageView ipw;

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

    private boolean isMember_pwValid = true;
    private boolean isMember_pwchkValid = true;
    private boolean isMember_nameValid = true;
    private boolean isMember_telValid = true;
    private boolean isMember_cartypeValid = true;
    private boolean isMember_caridValid = true;
    private boolean isMember_carcolorValid = true;

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
        idTv = (TextView) rootView.findViewById(R.id.idTv);
        pwEt = (EditText) rootView.findViewById(R.id.pwEt);
        cPwEt = (EditText) rootView.findViewById(R.id.cPwEt);
        nameEt = (EditText) rootView.findViewById(R.id.nameEt);
        telEt = (EditText) rootView.findViewById(R.id.telEt);
        carTypeEt = (EditText) rootView.findViewById(R.id.carTypeEt);
        carColorEt = (EditText) rootView.findViewById(R.id.carColorEt);
        carIdEt = (EditText) rootView.findViewById(R.id.carIdEt);

        ipw = (ImageView) rootView.findViewById(R.id.ipw);
        pwTv = (TextView) rootView.findViewById(R.id.pwTv);
        nameTv = (TextView) rootView.findViewById(R.id.nameTv);
        telTv = (TextView) rootView.findViewById(R.id.telTv);
        cartypeTv = (TextView) rootView.findViewById(R.id.cartypeTv);
        colorTv = (TextView) rootView.findViewById(R.id.colorTv);
        cidTv = (TextView) rootView.findViewById(R.id.cidTv);

        vpw = (TextView) rootView.findViewById(R.id.vpw);
        vname = (TextView) rootView.findViewById(R.id.vname);
        vtel = (TextView) rootView.findViewById(R.id.vtel);
        vtype = (TextView) rootView.findViewById(R.id.vtype);
        vcolor = (TextView) rootView.findViewById(R.id.vcolor);
        vcarid = (TextView) rootView.findViewById(R.id.vcarid);

        pwBtn = (ToggleButton) rootView.findViewById(R.id.pwBtn);
        nameBtn = (ToggleButton) rootView.findViewById(R.id.nameBtn);
        telBtn = (ToggleButton) rootView.findViewById(R.id.telBtn);
        typeBtn = (ToggleButton) rootView.findViewById(R.id.typeBtn);
        colorBtn = (ToggleButton) rootView.findViewById(R.id.colorBtn);
        cidBtn = (ToggleButton) rootView.findViewById(R.id.cidBtn);

        cancelBtn = (Button) rootView.findViewById(R.id.cancelBtn);
        modifyBtn = (Button) rootView.findViewById(R.id.findIdBtn);

        Bundle b = getArguments();
        if (b != null)
            vo = b.getParcelable("vo");

        initMemberInfo();

        pwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwBtn.isChecked()) {
                    pwTv.setVisibility(View.GONE);
                    pwEt.setVisibility(View.VISIBLE);
                    r.setVisibility(View.VISIBLE);
                    cpwtv.setVisibility(View.VISIBLE);
                } else {
                    pwTv.setText(mPw);
                    pwTv.setVisibility(View.VISIBLE);
                    pwEt.setVisibility(View.GONE);
                    r.setVisibility(View.INVISIBLE);
                    cpwtv.setVisibility(View.INVISIBLE);
                }
            }
        });

        pwEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = pwEt.getText().toString();
                if (isMember_pwValid && input.equals(mPw)) return;
                else if (!isPwVaild(vpw, input)) {
                    isMember_pwValid = false;
                } else {
                    isMember_pwValid = true;
                    mPw = input;
                    pwTv.setText(mPw);
                    vpw.setTextColor(GREEN);
                    vpw.setText("사용 가능합니다.");
                }
            }
        });
        cPwEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mPw.equals(cPwEt.getText().toString())) {
                    ipw.setImageResource(R.drawable.o);
                    isMember_pwchkValid = true;
                } else {
                    ipw.setImageResource(R.drawable.x);
                    isMember_pwchkValid = false;
                }
            }
        });

        nameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameBtn.isChecked()) {
                    nameEt.setVisibility(View.VISIBLE);
                    nameTv.setVisibility(View.GONE);
                } else {
                    nameEt.setVisibility(View.GONE);
                    nameTv.setVisibility(View.VISIBLE);
                }
            }
        });

        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = nameEt.getText().toString();
                if (isMember_nameValid && input.equals(mName)) return;
                if (!isNameVaild(vname, input)) {
                    isMember_nameValid = false;
                } else {
                    isMember_nameValid = true;
                    mName = input;
                    nameTv.setText(mName);
                    vname.setTextColor(GREEN);
                    vname.setText("사용 가능합니다.");
                }
            }
        });

        telBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (telBtn.isChecked()) {
                    telEt.setVisibility(View.VISIBLE);
                    telTv.setVisibility(View.GONE);
                } else {
                    telEt.setVisibility(View.GONE);
                    telTv.setVisibility(View.VISIBLE);
                }
            }
        });

        telEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = telEt.getText().toString();
                if (isMember_telValid && input.equals(mTel)) return;
                if (!isTelVaild(vtel, input)) {
                    isMember_telValid = false;
                } else {
                    isMember_telValid = true;
                    mTel = input;
                    telTv.setText(mTel);
                    vtel.setTextColor(GREEN);
                    vtel.setText("사용 가능합니다.");
                }
            }
        });
        typeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeBtn.isChecked()) {
                    carTypeEt.setVisibility(View.VISIBLE);
                    cartypeTv.setVisibility(View.GONE);
                } else {
                    carTypeEt.setVisibility(View.GONE);
                    cartypeTv.setVisibility(View.VISIBLE);
                }
            }
        });
        carTypeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = carTypeEt.getText().toString();

                if (isMember_cartypeValid && input.equals(mCarType)) return;

                if (!isCarTypeVaild(vtype, input)) {
                    isMember_cartypeValid = false;
                } else {
                    isMember_cartypeValid = true;
                    mCarType = input;
                    cartypeTv.setText(mCarType);
                    vtype.setTextColor(GREEN);
                    vtype.setText("사용 가능합니다.");
                }
            }

        });
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (colorBtn.isChecked()) {
                    carColorEt.setVisibility(View.VISIBLE);
                    colorTv.setVisibility(View.GONE);
                } else {
                    carColorEt.setVisibility(View.GONE);
                    colorTv.setVisibility(View.VISIBLE);
                }
            }
        });
        carColorEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = carColorEt.getText().toString();

                if (isMember_carcolorValid && input.equals(mCarColor)) return;
                if (!isNameVaild(vcolor, input)) {
                    isMember_carcolorValid = false;
                } else {
                    isMember_carcolorValid = true;
                    mCarColor = input;
                    colorTv.setText(mCarColor);
                    vcolor.setTextColor(GREEN);
                    vcolor.setText("사용 가능합니다.");
                }
            }
        });
        cidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cidBtn.isChecked()) {
                    carIdEt.setVisibility(View.VISIBLE);
                    cidTv.setVisibility(View.GONE);
                } else {
                    carIdEt.setVisibility(View.GONE);
                    cidTv.setVisibility(View.VISIBLE);
                }
            }
        });

        carIdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = carIdEt.getText().toString();

                if (isMember_caridValid && input.equals(mCarId)) return;
                if (!isCarIdVaild(vcarid, input)) {
                    isMember_caridValid = false;
                } else {
                    isMember_caridValid = true;
                    mCarId = input;
                    cidTv.setText(mCarId);
                    vcarid.setTextColor(GREEN);
                    vcarid.setText("사용 가능합니다.");
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
//                                MemberVO tmp = new MemberVO(vo.getMember_no(), mId, mPw, mName, mTel, vo.getCar_no(), mCarType, mCarColor, mCarId);
                                map = new HashMap<String, String>();
                                map.put("member_id", mId);
                                map.put("member_pw", mPw);
                                map.put("member_mname", mName);
                                map.put("member_phonenumber", mTel);
                                map.put("car_type", mCarType);
                                map.put("car_color", mCarColor);
                                map.put("car_id", mCarId);
                                String url = "http://70.12.115.73:9090/Chavis/Member/update.do";
                                HttpUtils http = new HttpUtils(HttpUtils.POST, map, url, getContext());
                                res = http.request();
                            } catch (Exception e) {
                                Log.i("MemberRegisterError", e.toString());
                            }
                        }
                    };
                    t.start();
                    try {
                        t.join();
                        if (Integer.parseInt(res) == 1) {
                            Toast.makeText(getContext(), "회원정보 수정 성공", Toast.LENGTH_SHORT).show();
                            FragmentTransaction f = getFragmentManager().beginTransaction();
                            f.detach(SettingFragment.this).attach(SettingFragment.this).commit();

                        } else
                            Toast.makeText(getContext(), "회원정보 수정 실패", Toast.LENGTH_SHORT).show();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getContext(), "회원가입 수정 실패", Toast.LENGTH_SHORT).show();
            }

        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog =
                        new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dialog.setTitle("수정을 취소하시겠습니까?");
                dialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FragmentTransaction f = getFragmentManager().beginTransaction();
                        f.detach(SettingFragment.this).attach(SettingFragment.this).commit();
                        return;
                    }
                });
                dialog.show();
            }
        });

        return rootView;
    }

    public void initMemberInfo() {

        mId = vo.getMember_id();
        mPw = vo.getMember_pw();
        mName = vo.getMember_mname();
        mTel = vo.getMember_phonenumber();
        mCarType = vo.getCar_type();
        mCarColor = vo.getCar_color();
        mCarId = vo.getCar_id();

        idTv.setText(mId);
        pwTv.setText(mPw);
        pwEt.setText(mPw);
        nameTv.setText(mName);
        nameEt.setText(mName);
        telTv.setText(mTel);
        telEt.setText(mTel);
        cartypeTv.setText(mCarType);
        carTypeEt.setText(mCarType);
        colorTv.setText(mCarColor);
        carColorEt.setText(mCarColor);
        cidTv.setText(mCarId);
        carIdEt.setText(mCarId);


        vpw.setText("");
        vname.setText("");
        vtel.setText("");
        vtype.setText("");
        vcolor.setText("");
        vcarid.setText("");
    }

    public boolean isInputComplete() {
        if (isMember_pwValid && isMember_pwchkValid && isMember_nameValid && isMember_telValid
                && isMember_cartypeValid && isMember_carcolorValid && isMember_caridValid)
            return true;
        else
            return false;
    }
}
