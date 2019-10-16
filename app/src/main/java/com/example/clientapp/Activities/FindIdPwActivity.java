package com.example.clientapp.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.clientapp.BackPressCloseHandler;
import com.example.clientapp.FindIdPwFragment.IDFragment;
import com.example.clientapp.FindIdPwFragment.PWFragment;
import com.example.clientapp.R;
import com.google.android.material.tabs.TabLayout;

public class FindIdPwActivity extends AppCompatActivity {
    private IDFragment idFragment;
    private PWFragment pwFragment;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_pw);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backPressCloseHandler = new BackPressCloseHandler(this);
        idFragment = new IDFragment();
        pwFragment = new PWFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.Findfrag_content, idFragment).commit();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("아이디 찾기"));
        tabs.addTab(tabs.newTab().setText("비밀번호 찾기"));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if (position == 0)
                    selected = idFragment;
                else
                    selected = pwFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.Findfrag_content, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
