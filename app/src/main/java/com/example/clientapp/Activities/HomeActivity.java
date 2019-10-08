package com.example.clientapp.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.clientapp.BackPressCloseHandler;
import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;
import com.example.clientapp.fragments.NotificationFragment;
import com.example.clientapp.fragments.StatusFragment;
import com.example.clientapp.fragments.ReservationFragment;
import com.example.clientapp.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    BackPressCloseHandler backPressCloseHandler;
    TextView actionbar_text;
    MemberVO vo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        actionbar_text = (TextView) findViewById(R.id.actionbar_text);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        vo = new MemberVO(1, "oea0805", "1234",
                "오은애", "01056113427", 1, "짱짱", "써나타");

//
//        Intent i = getIntent();
//        MemberVO vo = i.getExtras().getParcelable("vo");
        actionbar_text.setText("차량 상태 정보");
        loadFragmentClass(new NotificationFragment());

    }

    BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.car:
                    actionbar_text.setText("차량 상태 정보");
                    fragment = new NotificationFragment();
                    loadFragmentClass(fragment);
                    return true;
                case R.id.reservation:
                    actionbar_text.setText("점검 내역");
                    fragment = new ReservationFragment();
                    loadFragmentClass(fragment);
                    return true;
                case R.id.setting:
                    actionbar_text.setText("회원 정보");
                    fragment = new SettingFragment(vo);
                    loadFragmentClass(fragment);
                    return true;
                case R.id.notification:
                    actionbar_text.setText("알림사항");
                    fragment = new StatusFragment();
                    loadFragmentClass(fragment);
                    return true;
            }
            return false;
        }
    };


    private void loadFragmentClass(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_content, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
