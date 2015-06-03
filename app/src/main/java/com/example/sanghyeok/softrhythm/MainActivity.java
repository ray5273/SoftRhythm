package com.example.sanghyeok.softrhythm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    Button btn_gameStart;
    Button btn_archives;
    Button btn_downloads;
    Button btn_help;
    private TimerTask mTask;
    private Timer mTimer;

    private static MediaPlayer BGM;
    float volume=1.0f;
    float delTime=0.05f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SplashActivity.class));  // make splash page

        btn_gameStart=(Button)findViewById(R.id.gameStart);
        btn_archives=(Button)findViewById(R.id.archives);
        btn_downloads=(Button)findViewById(R.id.downloads);
        btn_help=(Button)findViewById(R.id.help);

        btn_gameStart.setOnClickListener(this);
        btn_archives.setOnClickListener(this);
        btn_downloads.setOnClickListener(this);
        btn_help.setOnClickListener(this);

        BGM=MediaPlayer.create(this,R.raw.mainactivity);
        BGM.setLooping(true);
        BGM.start();
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
        switch(v.getId()){
            case R.id.gameStart:
                Intent intent = new Intent(this, selectSongActivity.class);
                startActivity(intent);
                BGM.stop();
                finish();
                break;
            case R.id.downloads:
                Intent intent1 = new Intent(this, downloadsActivity.class);
                startActivity(intent1);
                BGM.stop();
                break;
            case R.id.archives:
                Intent intent2 = new Intent(this, archivesActivity.class);
                startActivity(intent2);
                BGM.stop();
                break;
            case R.id.help:
                Intent intent3 = new Intent(this, helpActivity.class);
                startActivity(intent3);
                BGM.stop();
                break;
        }
    }

}
