package com.example.clientapp.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.VO.MemberVO;
import com.example.clientapp.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private Boolean flag = true;
    private EditText clientIdEt;
    private EditText clientPwEt;
    private EditText checkPwEt;
    private EditText clientNameEt;
    private EditText telEt;
    private EditText carIdEt;
    private ImageView checkPw;
    private TextView validid;
    private TextView validpw;
    private TextView validpwch;
    private TextView validname;
    private TextView validtel;
    private TextView validcarid;

    private Button registerBtn;
    private Spinner carSp;
    private MemberVO vo = new MemberVO();

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

                String input ;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        clientIdEt = (EditText) findViewById(R.id.clientIdEt);
        clientPwEt = (EditText) findViewById(R.id.clientPwEt);
        checkPwEt = (EditText) findViewById(R.id.checkPwEt);
        clientNameEt = (EditText) findViewById(R.id.clientNameEt);
        telEt = (EditText) findViewById(R.id.telEt);
        carIdEt = (EditText) findViewById(R.id.carIdEt);
        carSp = (Spinner) findViewById(R.id.carSp);
        checkPw = (ImageView) findViewById(R.id.checkPw);
        validid = (TextView) findViewById(R.id.validid);
        validpw = (TextView) findViewById(R.id.validpw);
        validpwch = (TextView) findViewById(R.id.validpwch);
        validname = (TextView) findViewById(R.id.validname);
        validtel = (TextView) findViewById(R.id.validtel);
        validcarid = (TextView) findViewById(R.id.validcarid);

        registerBtn = (Button) findViewById(R.id.registerBtn);
        carSp = (Spinner) findViewById(R.id.carSp);



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clientIdEt.getText().toString().length() < 5 || clientIdEt.getText().toString().length() > 10) {
                    if (clientIdEt.getText().toString().length() == 0)
                        validid.setText("필수 정보입니다.");
                    else
                        validid.setText("5~10자의 영문 소문자, 숫자만 사용 가능합니다.");
                    flag = false;
                }

                if (clientPwEt.getText().toString().length() < 8 || clientPwEt.getText().toString().length() > 15) {
                    if (clientPwEt.getText().toString().length() == 0)
                        validpw.setText("필수 정보입니다.");
                    else
                        validpw.setText("8~15자의 영문 대/소문자, 숫자를 사용하세요.");
                    flag = false;
                }
                if (clientPwEt.getText().toString().length() != 0 && clientPwEt.getText().toString().equals(checkPwEt.getText().toString())) {
                    validpwch.setText("일치합니다.");
                    flag = true;
                } else {
                    validpwch.setText("비밀번호를 확인하세요.");
                    flag = false;
                }
                if (clientNameEt.getText().toString().length() == 0) {
                    validname.setText("필수 정보입니다.");
                    flag = false;
                }
                if (telEt.getText().toString().length() < 10) {
                    if (telEt.getText().toString().length() == 0)
                        validtel.setText("필수 정보입니다.");
                    else
                        validtel.setText("형식에 맞지 않는 번호입니다.");
                    flag = false;
                }
                if (carIdEt.getText().toString().length() < 5) {
                    if (carIdEt.getText().toString().length() == 0)
                        validcarid.setText("필수 정보입니다.");
                    else
                        validcarid.setText("형식에 맞지 않는 번호입니다.");
                    flag = false;
                }

                if (flag) {
//                    vo.setCAR_ID     (carIdEt.getText().toString());
//                    vo.setCAR_TYPE       (carSp.getSelectedItem().toString());
//                    vo.setCLIENT_ID(clientIdEt.getText().toString());
//                    vo.setCLIENT_NAME(clientNameEt.getText().toString());
//                    vo.setCLIENT_NUM("2");
//                    vo.setPASSWORD(clientPwEt.getText().toString());
//                    vo.setTEL(telEt.getText().toString());


                    RegisterRunnable registerRunnable = new RegisterRunnable(vo);
                    Thread t = new Thread(registerRunnable);
                    t.start();
                }
            }
        });

        checkPwEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (clientPwEt.getText().toString().equals(checkPwEt.getText().toString()))
                    checkPw.setImageResource(R.drawable.o);
                else
                    checkPw.setImageResource(R.drawable.x);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}



