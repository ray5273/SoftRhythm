package com.example.sanghyeok.softrhythm;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by sanghyeok on 2015-05-21.
 */
public class SplashActivity extends Activity {
    @Override

    //시작할때 3초 동안 화면뜸
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                finish();
            }
        };

        handler.sendEmptyMessageDelayed(0, 2000);

    } //end onCreate Method
}
