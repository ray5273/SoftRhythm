package com.example.sanghyeok.softrhythm;

import android.app.Service;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by sanghyeok on 2015-05-11.
 */
public class page1 extends FragmentActivity implements View.OnClickListener {
    SoundPool SP ;
    Button btn1;
    MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);


        btn1=(Button)findViewById(R.id.newbtn);
        btn1.setOnClickListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.newbtn:
                MediaPlayer music=MediaPlayer.create(this,R.raw.yuri);
                music.setVolume(0.8f, 0.8f);
                music.setLooping(true);
                music.start();
                break;

        }

    }

}
