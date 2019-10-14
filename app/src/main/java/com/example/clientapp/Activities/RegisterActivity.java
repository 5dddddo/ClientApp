package com.example.clientapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.HttpUtils;
import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static com.example.clientapp.HttpUtils.isCarIdVaild;
import static com.example.clientapp.HttpUtils.isCarTypeVaild;
import static com.example.clientapp.HttpUtils.isIdValid;
import static com.example.clientapp.HttpUtils.isNameVaild;
import static com.example.clientapp.HttpUtils.isPwVaild;
import static com.example.clientapp.HttpUtils.isTelVaild;

public class RegisterActivity extends AppCompatActivity {
    private boolean isMember_idValid = false;
    private boolean isDupValid = false;
    private boolean isMember_pwValid = false;
    private boolean isMember_pwchkValid = false;
    private boolean isMember_nameValid = false;
    private boolean isMember_telValid = false;
    private boolean isMember_cartypeValid = false;
    private boolean isMember_caridValid = false;
    private boolean isMember_carcolorValid = false;

    private EditText mIdEt;
    private EditText mPwEt;
    private EditText mPwCheckEt;
    private EditText mNameEt;
    private EditText mTelEt;
    private EditText mCarTypeEt;
    private EditText mCarIdEt;
    private EditText mCarColorEt;
    private ImageView checkPw;

    private TextView validid;
    private TextView validpw;
    private TextView validpwch;
    private TextView validname;
    private TextView validtel;
    private TextView validcartype;
    private TextView validcarid;
    private TextView validcarcolor;


    private Button registerBtn;

    private String mId;
    private String mPw;
    private String mName;
    private String mTel;
    private String mCarType;
    private String mCarId;
    private String mCarColor;

    HttpUtils http;
    private String res;
    private MemberVO vo = new MemberVO();
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mIdEt = (EditText) findViewById(R.id.mIdEt);
        mPwEt = (EditText) findViewById(R.id.mPwEt);
        mPwCheckEt = (EditText) findViewById(R.id.mPwCheckEt);
        mNameEt = (EditText) findViewById(R.id.mNameEt);
        mTelEt = (EditText) findViewById(R.id.mTelEt);
        mCarTypeEt = (EditText) findViewById(R.id.carTypeEt);
        mCarColorEt = (EditText) findViewById(R.id.carColorEt);

        mCarIdEt = (EditText) findViewById(R.id.carIdEt);
        checkPw = (ImageView) findViewById(R.id.checkPw);

        validid = (TextView) findViewById(R.id.vid);
        validpw = (TextView) findViewById(R.id.vpw);
        validpwch = (TextView) findViewById(R.id.vcpw);
        validname = (TextView) findViewById(R.id.vname);
        validtel = (TextView) findViewById(R.id.vtel);
        validcartype = (TextView) findViewById(R.id.vtype);
        validcarcolor = (TextView) findViewById(R.id.vcolor);
        validcarid = (TextView) findViewById(R.id.vcarid);

        registerBtn = (Button) findViewById(R.id.registerBtn);

        mIdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = mIdEt.getText().toString();

                if (isMember_idValid && isDupValid && input.equals(mId)) return;

                if (idCheck(input)) {
                    isDupValid = false;
                    validid.setTextColor(RED);
                    validid.setText("이미 존재하는 아이디입니다.");
                } else if (!isIdValid(validid, input)) {
                    isMember_idValid = false;
                } else {
                    isDupValid = true;
                    isMember_idValid = true;
                    validid.setTextColor(GREEN);
                    validid.setText("사용 가능한 아이디입니다.");
                    mId = input;
                }
            }
        });

        mPwEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = mPwEt.getText().toString();
                if (isMember_pwValid && input.equals(mPw)) return;
                else if (!isPwVaild(validpw, input)) {
                    isMember_pwValid = false;
                } else {
                    isMember_pwValid = true;
                    mPw = input;
                    validpw.setTextColor(GREEN);
                    validpw.setText("사용 가능합니다.");
                }
            }
        });
        mNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = mNameEt.getText().toString();
                if (isMember_nameValid && input.equals(mName)) return;
                if (!isNameVaild(validname, input)) {
                    isMember_nameValid = false;
                } else {
                    isMember_nameValid = true;
                    mName = input;
                    validname.setTextColor(GREEN);
                    validname.setText("사용 가능합니다.");
                }
            }
        });
        mTelEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = mTelEt.getText().toString();
                if (isMember_telValid && input.equals(mTel)) return;
                if (!isTelVaild(validtel, input)) {
                    isMember_telValid = false;
                } else {
                    isMember_telValid = true;
                    mTel = input;
                    validtel.setTextColor(GREEN);
                    validtel.setText("사용 가능합니다.");
                }

            }
        });

        mCarTypeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = mCarTypeEt.getText().toString();

                if (isMember_cartypeValid && input.equals(mCarType)) return;

                if (!isCarTypeVaild(validcartype, input)) {
                    isMember_cartypeValid = false;
                } else {
                    isMember_cartypeValid = true;
                    mCarType = input;
                    validcartype.setTextColor(GREEN);
                    validcartype.setText("사용 가능합니다.");
                }
            }

        });
        mCarColorEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = mCarColorEt.getText().toString();

                if (isMember_carcolorValid && input.equals(mCarColor)) return;
                if (!isNameVaild(validcarcolor, input)) {
                    isMember_carcolorValid = false;
                } else {
                    isMember_carcolorValid = true;
                    mCarColor = input;
                    validcarcolor.setTextColor(GREEN);
                    validcarcolor.setText("사용 가능합니다.");
                }
            }
        });

        mCarIdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = mCarIdEt.getText().toString();

                if (isMember_caridValid && input.equals(mCarId)) return;
                if (!isCarIdVaild(validcarid, input)) {
                    isMember_caridValid = false;
                } else {
                    isMember_caridValid = true;
                    mCarId = input;
                    validcarid.setTextColor(GREEN);
                    validcarid.setText("사용 가능합니다.");
                }
            }
        });

        mPwCheckEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mPw.equals(mPwCheckEt.getText().toString())) {
                    checkPw.setImageResource(R.drawable.o);
                    isMember_pwchkValid = true;
                } else {
                    checkPw.setImageResource(R.drawable.x);
                    isMember_pwchkValid = false;
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInputComplete()) {
                    Thread t = new Thread() {
                        public void run() {
                            try {

                                MemberVO tmp = new MemberVO(vo.getMember_no(), mId, mPw, mName, mTel, vo.getCar_no(), mCarType, mCarId, mCarColor);
                                Map<String, String> map = new HashMap<String, String>();
//                                map.put("MemberVO", tmp);
                                map.put("member_id", mId);
                                map.put("member_pw", mPw);
                                map.put("member_mname", mName);
                                map.put("member_phonenumber", mTel);
                                map.put("car_type", mCarType);
                                map.put("car_color", mCarColor);
                                map.put("car_id", mCarId);

//                                map.put("MemberVO", tmp);
                                String url = "http://70.12.115.73:9090/Chavis/Member/add.do";
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
                            Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            RegisterActivity.this.finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isInputComplete() {
        if (isMember_caridValid && isDupValid && isMember_cartypeValid && isMember_carcolorValid && isMember_idValid &&
                isMember_nameValid && isMember_pwchkValid && isMember_pwValid && isMember_telValid)
            return true;
        else
            return false;
    }

    public boolean idCheck(String input) {
        final String id = input;
        map = new HashMap<String, String>();
        Thread t = new Thread() {
            public void run() {
                try {
                    map.put("member_id", id);
                    String url = "http://70.12.115.73:9090/Chavis/Member/dupcheck.do";
                    HttpUtils http = new HttpUtils(HttpUtils.POST, map, url, getApplicationContext());
                    res = http.request();
                } catch (Exception e) {
                    Log.i("MemberIdError", e.toString());
                }
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Integer.parseInt(res) == 1)
            return true;
        else
            return false;
    }

}