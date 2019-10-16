package com.example.clientapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.clientapp.R;
import com.example.clientapp.VO.MemberVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView mImageView = (ImageView) findViewById(R.id.gif);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(mImageView);
        Glide.with(this).load(R.raw.car).into(gifImage);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new SplashHandler(), 3000);
        Log.i("LoadingActivity", "LoadingActivity");
    }

    private class SplashHandler implements Runnable {
        private MemberVO vo;

        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            LoadingActivity.this.finish();

            SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
            String data = preferences.getString("myObject", "NO");

            if (data.equals("NO")) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                LoadingActivity.this.finish();
            } else {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    vo = mapper.readValue(data, MemberVO.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent h = new Intent(getApplicationContext(), HomeActivity.class);
                h.putExtra("vo", vo);
                h.putExtra("fragment", "login");
                startActivity(h);
                LoadingActivity.this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}
