package com.example.sanghyeok.softrhythm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sanghyeok on 2015-06-03.
 */
public class resultActivity extends FragmentActivity implements OnClickListener{
    private ImageView album_art;
    private ImageView grade;
    private TextView cool,good,bad,miss,combo,total;
    private TextView artist_text,title_text;
    private Button go_main,restart_game_btn;
    String artist_title;
    int sum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);

        artist_title=selectSongActivity.artist+"_"+selectSongActivity.title;

        go_main=(Button)findViewById(R.id.go_main);
        restart_game_btn=(Button)findViewById(R.id.replay_game);

        artist_text=(TextView)findViewById(R.id.artist);
        artist_text.setText(selectSongActivity.artist);
        artist_text.setTypeface(Typeface.createFromAsset(getAssets(), "bm_hanna.ttf"));

        title_text=(TextView)findViewById(R.id.title);
        title_text.setText(selectSongActivity.title);
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



        Intent getData = getIntent();
        cool.setText("Cool :" + getData.getExtras().getString("cool"));
        good.setText("Good :"+getData.getExtras().getString("good"));
        bad.setText("Bad :" + getData.getExtras().getString("bad"));
        miss.setText("Miss :" + getData.getExtras().getString("miss"));
        combo.setText("Combo :" + getData.getExtras().getString("combo"));
        sum=Integer.parseInt(getData.getExtras().getString("cool"))+Integer.parseInt(getData.getExtras().getString("good"));
        total.setText("Total : "+sum);



        //Ƽ�� ��������
        if(sum>) {
            // drawable ���ҽ����� naverlogo ���� ȣ�� �Ͽ� Drawable�� ���
            Drawable drawable = getResources().getDrawable(R.drawable.challenger);

            // id : imageView01 <ImageView>�� �����´�.
            // imageView01 �� ���ҽ����� ������ naverlogo ���
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
        }else if(){
            // drawable ���ҽ����� naverlogo ���� ȣ�� �Ͽ� Drawable�� ���
            Drawable drawable = getResources().getDrawable(R.drawable.diamond);

            // id : imageView01 <ImageView>�� �����´�.
            // imageView01 �� ���ҽ����� ������ naverlogo ���
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
        }else if(){
            // drawable ���ҽ����� naverlogo ���� ȣ�� �Ͽ� Drawable�� ���
            Drawable drawable = getResources().getDrawable(R.drawable.platinum);

            // id : imageView01 <ImageView>�� �����´�.
            // imageView01 �� ���ҽ����� ������ naverlogo ���
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);

        }else if(){
            // drawable ���ҽ����� naverlogo ���� ȣ�� �Ͽ� Drawable�� ���
            Drawable drawable = getResources().getDrawable(R.drawable.gold);

            // id : imageView01 <ImageView>�� �����´�.
            // imageView01 �� ���ҽ����� ������ naverlogo ���
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
        }else if(){
            // drawable ���ҽ����� naverlogo ���� ȣ�� �Ͽ� Drawable�� ���
            Drawable drawable = getResources().getDrawable(R.drawable.silver);

            // id : imageView01 <ImageView>�� �����´�.
            // imageView01 �� ���ҽ����� ������ naverlogo ���
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
        }else {
            // drawable ���ҽ����� naverlogo ���� ȣ�� �Ͽ� Drawable�� ���
            Drawable drawable = getResources().getDrawable(R.drawable.bronze);

            // id : imageView01 <ImageView>�� �����´�.
            // imageView01 �� ���ҽ����� ������ naverlogo ���
            grade = (ImageView) findViewById(R.id.grade_img);
            grade.setImageDrawable(drawable);
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

                break;
            case R.id.go_main:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
