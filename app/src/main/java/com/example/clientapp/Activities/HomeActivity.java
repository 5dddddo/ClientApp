package com.example.clientapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.clientapp.BackPressCloseHandler;
import com.example.clientapp.Homefragments.HistoryFragment;
import com.example.clientapp.Homefragments.NotificationFragment;
import com.example.clientapp.Homefragments.SettingFragment;
import com.example.clientapp.Homefragments.StatusFragment;
import com.example.clientapp.R;
import com.example.clientapp.Service.RealService;
import com.example.clientapp.VO.MemberVO;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private BackPressCloseHandler backPressCloseHandler;
    private TextView actionbar_text;
    private MemberVO vo;
    private ImageButton logoutBtn;
    private Intent serviceIntent = RealService.serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        actionbar_text = (TextView) findViewById(R.id.actionbar_text);
        setContentView(R.layout.activity_home);
        backPressCloseHandler = new BackPressCloseHandler(this);
        logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        if (RealService.serviceIntent == null) {
            serviceIntent = new Intent(HomeActivity.this, RealService.class);
            startService(serviceIntent);
        } else {
            serviceIntent = RealService.serviceIntent;
//            Toast.makeText(getApplicationContext(), "already", Toast.LENGTH_LONG).show();
        }

        Intent i = getIntent();
        vo = i.getExtras().getParcelable("vo");
        String f = i.getExtras().get("fragment").toString();
        if (f.equals("reservation")) {
            actionbar_text.setText(vo.getCar_id());
            loadFragmentClass(new HistoryFragment());
        } else {
            actionbar_text.setText("내차현황");
            loadFragmentClass(new StatusFragment());
        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "Cancel Click", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        RealService.logoutIntent.putExtra("key", "1");
                        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                        HomeActivity.this.finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.car:
                    actionbar_text.setText("내차현황");
                    fragment = new StatusFragment();
                    loadFragmentClass(fragment);
                    return true;
                case R.id.reservation:
                    actionbar_text.setText("점검내역");
                    fragment = new HistoryFragment();
                    loadFragmentClass(fragment);
                    return true;
                case R.id.setting:
                    actionbar_text.setText("회원정보");
                    fragment = new SettingFragment();
                    loadFragmentClass(fragment);
                    return true;
                case R.id.notification:
                    actionbar_text.setText("공지사항");
                    fragment = new NotificationFragment();
                    loadFragmentClass(fragment);
                    return true;
            }
            return false;
        }
    };


    public void loadFragmentClass(Fragment fragment) {
        Bundle b = new Bundle();
        b.putParcelable("vo", vo);
        fragment.setArguments(b);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_content, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (serviceIntent != null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }

    }
}
