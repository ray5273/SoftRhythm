package com.example.sanghyeok.softrhythm;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    Button btn_intent1;
    Button btn_intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SplashActivity.class));  // make splash page
        btn_intent1=(Button)findViewById(R.id.newbtn);
        btn_intent2=(Button)findViewById((R.id.button2));
        btn_intent1.setOnClickListener(this);
        btn_intent2.setOnClickListener(this);
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
            case R.id.newbtn:
                Intent intent = new Intent(this, page1.class);
                startActivity(intent);

                break;
            case R.id.button2:
                Intent intent1 = new Intent(this, page2.class);
                startActivity(intent1);
                break;
        }
    }

}
