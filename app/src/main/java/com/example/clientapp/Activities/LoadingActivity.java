package com.example.clientapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.clientapp.R;

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

        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            LoadingActivity.this.finish();
//
//            SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
//            String data = preferences.getString("myObject", "NO");
//
////            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
////            OpeningActivity.this.finish();
//            // call LoginActivity
//            if (data.equals("NO")) {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                LoadingActivity.this.finish();
//            } else {
//                // Call MapsActivity
//                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                LoadingActivity.this.finish();
//            }
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}
