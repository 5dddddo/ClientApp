//package com.example.clientapp;
//
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import java.util.ArrayList;
//
//
//public class SendActivity extends AppCompatActivity {
//
//    ClientService clientService;
//    boolean isService;
//
//    ServiceConnection conn = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder service) {
//            // 서비스와 연결되었을 때 호출되는 메서드
//            ClientService.MyBinder myBinder = (ClientService.MyBinder) service;
//            clientService = myBinder.getService();    // 서비스 객체를 받는다
//            isService = true;
//            Log.i("Main", "서비스 바인딩");
//
////            clientService.connectServer();
//            // 서비스가 바인딩 된 후에야 서비스객체를 사용할 수 있기 때문에
//            // 소켓을 연결하는 서비스 함수를 여기에 선언한다.
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            // 서비스와 연결이 끊어졌을 때 호출되는 메서드
//            isService = false;
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_send);
//
//        final EditText editText = (EditText) findViewById(R.id.keywordEt);
//        Button searchBtn = (Button) this.findViewById(R.id.searchBtn);   // 앞에 this가 생략할수도있는데 여기 activity에서 찾는거
//        // anonymous inner class를 이용한 Event처리 (Android의 전형적인 event처리)
//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 버튼을 눌렀을 때 서비스를 생성하고 실행.
//                Intent i = new Intent();
//                // 명시적 Intent를 사용
//                i = new Intent(SendActivity.this,
//                        ClientService.class);
//                i.putExtra("searchKeyword", editText.getText().toString());
//                bindService(i, conn, Context.BIND_AUTO_CREATE);
//                startService(i);
//            }
//        });
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        Log.i("KAKAOBOOKLog", "데이터가 정상적으로 Activity에 도달!!");
//        // intent에서 데이터 추출해서 ListView에 출력하는 작업을 진행
//        ArrayList<ClientVO> data = intent.getExtras().getParcelableArrayList("resultData");
//
//        // 만약 그림까지 포함하려면 추가적인 작업이 더 필요
//        // ListView에 도서 제목만 먼저 출력
//
//    }
//}