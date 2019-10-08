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
import com.example.clientapp.fragments.HistoryFragment;
import com.example.clientapp.fragments.NotificationFragment;
import com.example.clientapp.fragments.SettingFragment;
import com.example.clientapp.fragments.StatusFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
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
        loadFragmentClass(new StatusFragment(vo),"차량 상태 정보");

    }

    BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.car:
                    fragment = new StatusFragment(vo);
                    loadFragmentClass(fragment,"차량 상태 정보");
                    return true;
                case R.id.reservation:
                    fragment = new HistoryFragment();
                    loadFragmentClass(fragment,"점검 내역");
                    return true;
                case R.id.setting:
                    fragment = new SettingFragment(vo);
                    loadFragmentClass(fragment,"회원 정보");
                    return true;
                case R.id.notification:
                    fragment = new NotificationFragment();
                    loadFragmentClass(fragment,"알림사항");
                    return true;
            }
            return false;
        }
    };


    void loadFragmentClass(Fragment fragment, String text) {
        actionbar_text.setText(text);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_content, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
