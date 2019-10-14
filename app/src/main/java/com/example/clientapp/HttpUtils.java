package com.example.clientapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class HttpUtils {
    private Context context;
    private HttpURLConnection con;
    private static Map<String, String> map;
    private static String url;
    public static final int POST = 1;
    public static final int GET = 0;
    private static int Method = GET;

    public HttpUtils(int method, String url, Context context) {
        HttpUtils.Method = method;
        HttpUtils.url = url;
        this.context = context;
    }

    public HttpUtils(int method, Map<String, String> map, String url, Context context) {
        HttpUtils.Method = method;
        HttpUtils.map = map;
        HttpUtils.url = url;
        this.context = context;
    }

    public String request() { //key&value로 전송하고 json으로 받기.
        try {
            URL Url = new URL(url);
            con = (HttpURLConnection) Url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("charset", "utf-8");
            con.setDefaultUseCaches(false);
            con.setUseCaches(false);
            con.setDoInput(true);

            if (Method == HttpUtils.POST) {
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                String sendMsg = mapper.writeValueAsString(map);
                os.write(sendMsg.getBytes("UTF-8"));
                os.flush();
                os.close();
            } else if (Method == HttpUtils.GET) {
                StringBuilder sb = new StringBuilder();
                con.setRequestMethod("GET");
                con.setDoOutput(false); //DoOutPut설정시 GET으로 설정해도 자동으로 POST로 바뀐다.
            }
//            setCookieHeader();
            //사용자가 로그인해서 세션 쿠키를 서버로부터 발급받은적 있다면 그 다음 요청 헤더 부터는 그 세션 쿠키를 포함해서 전송해야 함.

            Log.d("HttpUtils", url + "로 HTTP 요청 전송");
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) { //이때 요청이 보내짐.
                Log.d("LOG", "HTTP_OK를 받지 못했습니다.");
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            Log.i("Server로부터 받은 데이터", res);
//            getCookieHeader();
            return res;
        } catch (MalformedURLException e) { // for URL.
            e.printStackTrace();
        } catch (IOException e) { // for openConnection().
            e.printStackTrace();
        } finally {
            if (con != null)
                con.disconnect();
        }
        return null;

    }

    private void getCookieHeader() {//Set-Cookie에 배열로 돼있는 쿠키들을 스트링 한줄로 변환
        List<String> cookies = con.getHeaderFields().get("Set-Cookie");
        //cookies -> [JSESSIONID=D3F829CE262BC65853F851F6549C7F3E; Path=/smartudy; HttpOnly] -> []가 쿠키1개임.
        //Path -> 쿠키가 유효한 경로 ,/smartudy의 하위 경로에 위의 쿠키를 사용 가능.
        if (cookies != null) {
            for (String cookie : cookies) {
                String sessionid = cookie.split(";\\s*")[0];
                //JSESSIONID=FB42C80FC3428ABBEF185C24DBBF6C40를 얻음.
                //세션아이디가 포함된 쿠키를 얻었음.
                //이제 해야할일은, 그 세션아이디를 sharedpreference에 저장하는것. 저장하기 전에 기존에 저장된 세션아이디가 있는지 확인한다.
                //기존에 저장된 세션의 아이디가 현재 수신한 세션의 아이디와 일치하는 경우 서버의 세션이 만료되지 않았다는 의미이다.
                //기존에 저장된 세션의 아이디가 현재 수신한 세션의 아이디와 일치하지 않는 경우 서버의 세션이 만료되어 다른 세션 아이디를 발급 했다는것이다.
                //기존에 저장된 세션의 아이디가 존재하고, 현재 수신한 세션의 아이디와 일치 하지 않는 경우 서버의 세션 아이디로 교체 해야 한다.
                //기존에 저장된 세션의 아이디가 없는 경우, 그냥 서버의 세션 아이디를 저장 하면 된다.
                //요청을 보낼때마다 sharedpref에서 세션아이디 필드가 존재하는지 확인해서 있으면 쿠키를 포함해서 보내고, 없으면 보내지 않는다.
                setSessionIdInSharedPref(sessionid);
            }
        }
    }

    private void setCookieHeader() {
        SharedPreferences pref = context.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE);
        String sessionid = pref.getString("sessionid", null);
        if (sessionid != null) {
            Log.d("LOG", "세션 아이디" + sessionid + "가 요청 헤더에 포함 되었습니다.");
            con.setRequestProperty("Cookie", sessionid);
        }
    }

    private void setSessionIdInSharedPref(String sessionid) {
        SharedPreferences pref = context.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (pref.getString("sessionid", null) == null) { //처음 로그인하여 세션아이디를 받은 경우
            Log.d("LOG", "처음 로그인하여 세션 아이디를 pref에 넣었습니다." + sessionid);
        } else if (!pref.getString("sessionid", null).equals(sessionid)) { //서버의 세션 아이디 만료 후 갱신된 아이디가 수신된경우
            Log.d("LOG", "기존의 세션 아이디" + pref.getString("sessionid", null) + "가 만료 되어서 "
                    + "서버의 세션 아이디 " + sessionid + " 로 교체 되었습니다.");
        }
        edit.putString("sessionid", sessionid);
        edit.apply(); //비동기 처리
    }

    public static boolean isIdValid(TextView valid, String input) {
        if (input.length() == 0) {
            valid.setTextColor(Color.RED);
            valid.setText("아이디를 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z0-9]{5,10}$", input)) {
            valid.setTextColor(Color.RED);
            valid.setText("5~10자의 영문 대/소문자, 숫자만 사용 가능합니다.");
            return false;
        }
        return true;
    }

    public static boolean isPwVaild(TextView valid, String input) {
        if (input.length() == 0) {
            valid.setTextColor(Color.RED);
            valid.setText("비밀번호를 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,15}$", input)) {
            valid.setTextColor(Color.RED);
            valid.setText("8~15자의 영문 대/소문자, 숫자만 사용 가능합니다.");
            return false;
        }
        return true;
    }

    public static boolean isNameVaild(TextView valid, String input) {
        if (input.length() == 0) {
            valid.setTextColor(Color.RED);
            valid.setText("이름을 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^[가-힣]{2,10}$", input)) {
            valid.setTextColor(Color.RED);
            valid.setText("2글자 이상의 한글을 입력해주세요.");
            return false;
        }
        return true;
    }

    public static boolean isTelVaild(TextView valid, String input) {
        if (input.length() == 0) {
            valid.setTextColor(Color.RED);
            valid.setText("핸드폰 번호를 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", input)) {
            valid.setTextColor(Color.RED);
            valid.setText("올바른 번호가 아닙니다.");
            return false;
        }
        return true;
    }

    public static boolean isCarTypeVaild(TextView valid, String input) {
        if (input.length() == 0) {
            valid.setTextColor(Color.RED);
            valid.setText("차종을 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^[가-힣a-zA-Z0-9]+$", input)) {
            valid.setTextColor(Color.RED);
            valid.setText("특수문자는 사용할 수 없습니다.");
            return false;
        }
        return true;
    }

    public static boolean isCarIdVaild(TextView valid, String input) {
        if (input.length() == 0) {
            valid.setTextColor(Color.RED);
            valid.setText("차 번호를 입력하세요.");
            return false;
        }
        if (!Pattern.matches("^\\d{2}[가-힣]{1}\\d{4}$", input)) {
            valid.setTextColor(Color.RED);
            valid.setText("올바른 차 번호가 아닙니다.");
            return false;
        }
        return true;
    }


}
