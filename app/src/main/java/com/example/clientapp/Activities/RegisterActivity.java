package com.example.clientapp.Activities;

import android.graphics.Color;
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

import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import static android.graphics.Color.GREEN;

public class RegisterActivity extends AppCompatActivity {
    boolean isMember_noValid = false;
    boolean isMember_idValid = false;
    boolean isMember_pwValid = false;
    boolean isMember_pwchkValid = false;
    boolean isMember_nameValid = false;
    boolean isMember_telValid = false;
    boolean isMember_cartypeValid = false;
    boolean isMember_caridValid = false;

    private EditText mIdEt;
    private EditText mPwEt;
    private EditText mPwCheckEt;
    private EditText mNameEt;
    private EditText mTelEt;
    private EditText mCarTypeEt;
    private EditText mCarIdEt;
    private ImageView checkPw;

    private TextView validid;
    private TextView validpw;
    private TextView validpwch;
    private TextView validname;
    private TextView validtel;
    private TextView validcartype;
    private TextView validcarid;

    private Button registerBtn;

    private String mId;
    private String mPw;
    private String mName;
    private String mTel;
    private String mCarType;
    private String mCarId;

    private MemberVO vo = new MemberVO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mIdEt = (EditText) findViewById(R.id.mIdEt);
        mPwEt = (EditText) findViewById(R.id.mPwEt);
        mPwCheckEt = (EditText) findViewById(R.id.mPwCheckEt);
        mNameEt = (EditText) findViewById(R.id.mNameEt);
        mTelEt = (EditText) findViewById(R.id.mTelEt);
        mCarTypeEt = (EditText) findViewById(R.id.mCarTypeEt);
        mCarIdEt = (EditText) findViewById(R.id.mCarIdEt);
        checkPw = (ImageView) findViewById(R.id.checkPw);

        validid = (TextView) findViewById(R.id.validid);
        validpw = (TextView) findViewById(R.id.validpw);
        validpwch = (TextView) findViewById(R.id.validpwch);
        validname = (TextView) findViewById(R.id.validname);
        validtel = (TextView) findViewById(R.id.validtel);
        validcartype = (TextView) findViewById(R.id.validcartype);
        validcarid = (TextView) findViewById(R.id.validcarid);

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

                if (isMember_idValid && input.equals(mId)) return;

                if (input.equals("공학수학마스터")) {
                    isMember_idValid = false;
                    mId = "";
                    validid.setText("이미 존재하는 닉네임 입니다.");
                } else if (!isIdValid(input)) {
                    isMember_idValid = false;
                } else {
                    isMember_idValid = true;
                    mId = input;
                    validid.setText("사용 가능한 아이디입니다.");
                    validid.setTextColor(GREEN);
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
                else if (!isPwVaild(input)) {
                    isMember_pwValid = false;
                } else {
                    isMember_pwValid = true;
                    mPw = input;
                    validpw.setText("사용 가능합니다.");
                    validpw.setTextColor(GREEN);
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
                if (!isNameVaild(input)) {
                    isMember_nameValid = false;
                } else {
                    isMember_nameValid = true;
                    mName = input;
                    validname.setText("사용 가능합니다.");
                    validname.setTextColor(GREEN);
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
                String input = mTelEt.getText().toString();
                if (isMember_telValid && input.equals(mTel)) return;

                if (!isTelVaild(input)) {
                    isMember_telValid = false;
                } else {
                    isMember_telValid = true;
                    mTel = input;
                    validtel.setText("사용 가능합니다.");
                    validtel.setTextColor(GREEN);
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

                if (!isCarTypeVaild(input)) {
                    isMember_cartypeValid = false;
                } else {
                    isMember_cartypeValid = true;
                    mCarType = input;
                    validcartype.setText("사용 가능합니다.");
                    validcartype.setTextColor(Color.RED);
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
                if (!isCarIdVaild(input)) {
                    isMember_caridValid = false;
                } else {
                    isMember_caridValid = true;
                    mCarId = input;
                    validcarid.setText("사용 가능합니다.");
                    validcarid.setTextColor(GREEN);
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

                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
//                    RegisterRunnable registerRunnable = new RegisterRunnable(vo);
//                    Thread t = new Thread(registerRunnable);
//                    t.start();
                } else {
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class RegisterRunnable implements Runnable {
        Boolean status = false;
        MemberVO vo;

        public RegisterRunnable(MemberVO vo) {
            this.vo = vo;
        }

        @Override
        public void run() {
            try {
                URL url = new URL("http://70.12.115.57:9090/TestProject/register.do");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("Connection", "Keep-Alive");
                con.setRequestProperty("charset", "utf-8");
                con.setDoInput(true);
                con.setDoOutput(true);

                Log.i("ClientService", "Http connection 됐다!!");

                OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));

                ObjectMapper mapper = new ObjectMapper();
                String sendMsg = mapper.writeValueAsString(vo);

                osw.write(sendMsg);
                osw.flush();

                String input;
                StringBuffer sb = new StringBuffer();
                // stream을 통해 data 읽어오기
                while ((input = br.readLine()) != null) {
                    sb.append(input);
                }
                Log.i("ClientReceiveData", sb.toString());
                br.close();
                con.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isIdValid(String input) {
        if (input.length() == 0) {

            validid.setTextColor(Color.RED);
            validid.setText("아이디를 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z0-9]{5,10}$", input)) {
            validid.setTextColor(Color.RED);
            validid.setText("5~10자의 영문 대/소문자, 숫자만 사용 가능합니다.");
            return false;
        }
        return true;
    }

    public boolean isPwVaild(String input) {
        if (input.length() == 0) {
            validpw.setTextColor(Color.RED);
            validpw.setText("비밀번호를 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,15}$", input)) {
            validpw.setTextColor(Color.RED);
            validpw.setText("8~15자의 영문 대/소문자, 숫자만 사용 가능합니다.");
            return false;
        }
        return true;
    }

    public boolean isNameVaild(String input) {
        if (input.length() == 0) {
            validname.setText("이름을 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^[가-힣]{2,10}$", input)) {
            validname.setTextColor(Color.RED);
            validname.setText("2글자 이상의 한글을 입력해주세요.");
            return false;
        }
        return true;
    }

    public boolean isTelVaild(String input) {
        if (input.length() == 0) {
            validtel.setTextColor(Color.RED);
            validtel.setText("핸드폰 번호를 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", input)) {
            validtel.setTextColor(Color.RED);
            validtel.setText("올바른 번호가 아닙니다.");
            return false;
        }
        return true;
    }

    public boolean isCarTypeVaild(String input) {
        if (input.length() == 0) {
            validcartype.setTextColor(Color.RED);
            validcartype.setText("차종을 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{2,}$", input)) {
            validcartype.setTextColor(Color.RED);
            validcartype.setText("특수문자는 사용할 수 없습니다.");
            return false;
        }
        return true;
    }

    public boolean isCarIdVaild(String input) {
        if (input.length() == 0) {
            validcarid.setTextColor(Color.RED);
            validcarid.setText("차 번호를 입력하세요.");
            return false;
        }
        if (!(Pattern.matches("^\\d{2}[가-힣]{1}\\d{4}$", input)
                || Pattern.matches("^[가-힣]{2}\\d{2}[가-힣]{1}\\d{4}$", input))) {
            validcarid.setTextColor(Color.RED);
            validcarid.setText("올바른 차 번호가 아닙니다.");
            return false;
        }
        return true;
    }

    public boolean isInputComplete() {
        if (isMember_caridValid && isMember_cartypeValid && isMember_idValid &&
                isMember_nameValid && isMember_pwchkValid && isMember_pwValid && isMember_telValid)
            return true;
        else
            return false;
    }

}



