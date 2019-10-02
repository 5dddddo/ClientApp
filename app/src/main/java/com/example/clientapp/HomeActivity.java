package com.example.clientapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clientapp.VO.MemberVO;
import com.example.clientapp.fragments.CarFragment;
import com.example.clientapp.fragments.NotificationFragment;
import com.example.clientapp.fragments.ReservationFragment;
import com.example.clientapp.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    BackPressCloseHandler backPressCloseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        Intent i = getIntent();
        MemberVO vo = i.getExtras().getParcelable("vo");

    }

    BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.car:
                    fragment = new CarFragment();
                    loadFragmentClass(fragment);
                    return true;
                case R.id.reservation:
                    fragment = new ReservationFragment();
                    loadFragmentClass(fragment);
                    return true;
                case R.id.setting:
                    fragment = new SettingFragment();
                    loadFragmentClass(fragment);
                    return true;
                case R.id.notification:
                    fragment = new NotificationFragment();
                    loadFragmentClass(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragmentClass(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }



}
