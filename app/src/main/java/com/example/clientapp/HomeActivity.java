package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends FragmentActivity {
    BackPressCloseHandler backPressCloseHandler;
    private SettingFragment settingFragment;
    private CarStatusFragment carStatusFragment;
    private NotificationFragment notificationFragment;
    private ReservationFragment reservationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setDefaultFragment();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        settingFragment = new SettingFragment();
        carStatusFragment = new CarStatusFragment();
        notificationFragment = new NotificationFragment();
        reservationFragment = new ReservationFragment();

        final Intent i = getIntent();
        final String member_id = i.getExtras().getString("member_id");
        backPressCloseHandler = new BackPressCloseHandler(this);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if (tabId == 1) {
                    transaction.replace(R.id.contentContainer, carStatusFragment).commit();
                } else if (tabId == 2) {
                    transaction.replace(R.id.contentContainer, reservationFragment).commit();
                } else if (tabId == 3) {
                    transaction.replace(R.id.contentContainer, settingFragment).commit();
                } else if (tabId == 4) {
                    transaction.replace(R.id.contentContainer, notificationFragment).commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    public void setDefaultFragment() {
        //화면에 보여지는 fragment를 추가하거나 바꿀 수 있는 객체를 만든다.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //첫번째로 보여지는 fragment는 firstFragment로 설정한다.
        fragmentTransaction.add(R.id.contentContainer, settingFragment);
        //fragment의 변경사항을 반영시킨다.
        fragmentTransaction.commit();
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentContainer, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }


}
