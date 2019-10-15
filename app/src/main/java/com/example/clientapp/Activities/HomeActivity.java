package com.example.clientapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
    private  BottomNavigationView bottomNavigationView;
    private BackPressCloseHandler backPressCloseHandler;
    private TextView actionbar_text;
    private MemberVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        actionbar_text = (TextView) findViewById(R.id.actionbar_text);
        setContentView(R.layout.activity_home);



        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

        Intent i = getIntent();
        vo = i.getExtras().getParcelable("vo");
        String f = i.getExtras().get("fragment").toString();
        if (f.equals("reservation")) {
            actionbar_text.setText("점검 내역");
            loadFragmentClass(new HistoryFragment());
        } else {
            actionbar_text.setText("차량 상태 정보");
            loadFragmentClass(new StatusFragment());
        }


    }

    BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.car:
                    actionbar_text.setText(vo.getCar_id());
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
        b.putParcelable("vo",vo);
        fragment.setArguments(b);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_content, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logoutBtn:
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
