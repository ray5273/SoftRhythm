package com.example.sanghyeok.softrhythm;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by sanghyeok on 2015-06-20.
 */
public class mainGameActivity extends Activity {
    private MyGameView mGameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mGameView = new MyGameView(this);
        setContentView(mGameView);
    }

    // ---------------------------------
    //   옵션 메뉴
    // ---------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Quit");
        menu.add(0, 2, 0, "Pause");
        menu.add(0, 3, 0, "Resume");
        return true;
    }

    // ---------------------------------
    //   옵션 메뉴 호출
    // ---------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                System.exit(0);
                break;
            case 2:
                mGameView.mMode = mGameView.PAUSE;
                break;
            case 3:
                mGameView.mMode = mGameView.RUN;
        }
        return true;
    }

} // end Activity