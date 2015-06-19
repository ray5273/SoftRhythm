package com.example.sanghyeok.softrhythm;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sanghyeok on 2015-06-03.
 */


public class resultActivity extends FragmentActivity implements View.OnClickListener{
    private ImageView album_art;
    private ImageView grade;
    private TextView cool,good,bad,miss,combo,total;
    private TextView artist_text,title_text;
    private Button go_main,restart_game_btn;
    String artist_title;
    String server="http://203.252.53.3";//"http://221.140.84.135";
    String artist,title;
    String grade_str;
    private int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);

        Intent getData = getIntent();
        artist=getData.getExtras().getString("artist");
        title=getData.getExtras().getString("title");


        artist_title=artist+"_"+title;

        go_main=(Button)findViewById(R.id.go_main_result);
        restart_game_btn=(Button)findViewById(R.id.replay_game);
        go_main.setOnClickListener(this);

        artist_text=(TextView)findViewById(R.id.artist);
        artist_text.setText(artist);
        artist_text.setTypeface(Typeface.createFromAsset(getAssets(), "bm_hanna.ttf"));

        title_text=(TextView)findViewById(R.id.title);
        title_text.setText(title);
        title_text.setTypeface(Typeface.createFromAsset(getAssets(), "bm_hanna.ttf"));

        try{
            album_art = (ImageView)findViewById(R.id.album_art);
            String imgpath = Environment.getExternalStorageDirectory()+"/SoftRhythm/Data/";
            imgpath+=artist_title+"/"+artist_title+".png";
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            album_art.setImageBitmap(bm);
            Toast.makeText(getApplicationContext(), "load ok", Toast.LENGTH_SHORT).show();
        }catch(Exception e){Toast.makeText(getApplicationContext(), "load error", Toast.LENGTH_SHORT).show();}


        cool=(TextView)findViewById(R.id.cool);
        good=(TextView)findViewById(R.id.good);
        bad=(TextView)findViewById(R.id.bad);
        miss=(TextView)findViewById(R.id.miss);
        combo=(TextView)findViewById(R.id.combo);
        total=(TextView)findViewById(R.id.total);




        cool.setText("Perfect :" + getData.getExtras().getString("cool"));
        good.setText("Good :"+getData.getExtras().getString("good"));
        bad.setText("Bad :" + getData.getExtras().getString("bad"));
        miss.setText("Miss :" + getData.getExtras().getString("miss"));
        combo.setText("Combo :" + getData.getExtras().getString("combo"));
        sum=Integer.parseInt(getData.getExtras().getString("cool"))+Integer.parseInt(getData.getExtras().getString("good"));
        total.setText("Total : "+sum);
        //티어 결정구간
        if(sum>20000) {
            // drawable 리소스에서 naverlogo 파일 호출 하여 Drawable에 등록
            Drawable drawable = getResources().getDrawable(R.drawable.challenger);
            grade_str="challenger";
            // id : imageView01 <ImageView>를 가져온다.
            // imageView01 에 리소스에서 가져온 naverlogo 등록
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
        }else if(sum>15000){
            // drawable 리소스에서 naverlogo 파일 호출 하여 Drawable에 등록
            Drawable drawable = getResources().getDrawable(R.drawable.diamond);
            grade_str="diamond";
            // id : imageView01 <ImageView>를 가져온다.
            // imageView01 에 리소스에서 가져온 naverlogo 등록
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
        }else if(sum>10000){
            // drawable 리소스에서 naverlogo 파일 호출 하여 Drawable에 등록
            Drawable drawable = getResources().getDrawable(R.drawable.platinum);
            grade_str="platinum";
            // id : imageView01 <ImageView>를 가져온다.
            // imageView01 에 리소스에서 가져온 naverlogo 등록
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);

        }else if(sum>5000){
            // drawable 리소스에서 naverlogo 파일 호출 하여 Drawable에 등록
            Drawable drawable = getResources().getDrawable(R.drawable.gold);
            grade_str="gold";
            // id : imageView01 <ImageView>를 가져온다.
            // imageView01 에 리소스에서 가져온 naverlogo 등록
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
        }else if(sum>2500){
            // drawable 리소스에서 naverlogo 파일 호출 하여 Drawable에 등록
            Drawable drawable = getResources().getDrawable(R.drawable.silver);
            grade_str="silver";
            // id : imageView01 <ImageView>를 가져온다.
            // imageView01 에 리소스에서 가져온 naverlogo 등록
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
        }else {
            // drawable 리소스에서 naverlogo 파일 호출 하여 Drawable에 등록
            Drawable drawable = getResources().getDrawable(R.drawable.bronze);
            grade_str="bronze";
            // id : imageView01 <ImageView>를 가져온다.
            // imageView01 에 리소스에서 가져온 naverlogo 등록
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
        }

        try {

            Log.v("artist",artist);
            Log.v("artist",title);
            Log.v("artist",grade_str);
            Log.v("total:",Integer.toString(sum));
            // URL url = new URL("http://221.140.84.135/test.php");
            //
            String nee=server+"/insert.php"+"?artist="+artist+"&title="+title+"&grade="+grade_str+"&total="+sum;
            URL url = new URL(server+"/insert.php"+"?artist="+artist+"&title="+title+"&grade="+grade_str+"&total="+sum);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            Log.v(nee,nee);

            if (conn != null) {

                conn.setConnectTimeout(10000);

                conn.setUseCaches(true);

            }
        } catch (Exception e) {
            Log.v("dddd","ddddd");
        }

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.replay_game:
                finish();
                break;
            case R.id.go_main_result:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
