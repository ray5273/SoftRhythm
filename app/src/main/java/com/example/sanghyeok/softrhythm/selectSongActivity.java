package com.example.sanghyeok.softrhythm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sanghyeok on 2015-05-27.
 */
public class selectSongActivity extends FragmentActivity implements View.OnClickListener{
    // 리스트뷰 선언
    private ListView listview;
    private Button back;
    // 데이터를 연결할 Adapter
    DataAdapter adapter;

    // 데이터를 담을 자료구조
    ArrayList<CData> alist;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_song);

        back = (Button)findViewById(R.id.back_btn);
        back.setOnClickListener(this);
        // 선언한 리스트뷰에 사용할 리스뷰연결
        listview = (ListView) findViewById(R.id.songlist);

        // 객체를 생성합니다
        alist = new ArrayList<CData>();

        // 데이터를 받기위해 데이터어댑터 객체 선언
        adapter = new DataAdapter(this, alist);

        // 리스트뷰에 어댑터 연결
        listview.setAdapter(adapter);

        // ArrayAdapter를 통해서 ArrayList에 자료 저장
        // 하나의 String값을 저장하던 것을 CData클래스의 객체를 저장하던것으로 변경
        // CData 객체는 생성자에 리스트 표시 텍스트뷰1의 내용과 텍스트뷰2의 내용 그리고 사진이미지값을 어댑터에 연결

        // CData클래스를 만들때의 순서대로 해당 인수값을 입력
        // 한줄 한줄이 리스트뷰에 뿌려질 한줄 한줄이라고 생각하면 됩니다.
        adapter.add(new CData(getApplicationContext(), "가수명: 친구1",
                "곡명 : 000-111-1111", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구2",
                "곡명 : 000-222-2222", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구3",
                "곡명 : 000-333-3333", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구4",
                "곡명 : 000-444-4444", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구1",
                "곡명 : 000-111-1111", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구2",
                "곡명 : 000-222-2222", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구3",
                "곡명 : 000-333-3333", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구4",
                "곡명 : 000-444-4444", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구1",
                "곡명 : 000-111-1111", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구2",
                "곡명 : 000-222-2222", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구3",
                "곡명 : 000-333-3333", R.drawable.ic_launcher));
        adapter.add(new CData(getApplicationContext(), "가수명: 친구4",
                "곡명 : 000-444-4444", R.drawable.ic_launcher));

    }

    private class DataAdapter extends ArrayAdapter<CData> {
        // 레이아웃 XML을 읽어들이기 위한 객체
        private LayoutInflater mInflater;

        public DataAdapter(Context context, ArrayList<CData> object) {

            // 상위 클래스의 초기화 과정
            // context, 0, 자료구조
            super(context, 0, object);
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        // 보여지는 스타일을 자신이 만든 xml로 보이기 위한 구문
        @Override
        public View getView(int position, View v, ViewGroup parent) {
            View view = null;

            // 현재 리스트의 하나의 항목에 보일 컨트롤 얻기

            if (v == null) {

                // XML 레이아웃을 직접 읽어서 리스트뷰에 넣음
                view = mInflater.inflate(R.layout.song_list, null);
            } else {

                view = v;
            }

            // 자료를 받는다.
            final CData data = this.getItem(position);

            if (data != null) {
                // 화면 출력
                TextView tv = (TextView) view.findViewById(R.id.singerName);
                TextView tv2 = (TextView) view.findViewById(R.id.songName);

                tv.setText(data.getLabel());
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(20);
                tv.setTypeface(Typeface.createFromAsset(getAssets(),"bm_hanna.ttf"));

                tv2.setText(data.getData());
                tv2.setTextColor(Color.WHITE);
                tv2.setTextSize(20);
                tv2.setTypeface(Typeface.createFromAsset(getAssets(), "bm_hanna.ttf"));

                ImageView iv = (ImageView) view.findViewById(R.id.albumImage);

                // 이미지뷰에 뿌려질 해당 이미지값을 연결 즉 세번째 인수값
                iv.setImageResource(data.getData2());

            }

            return view;

        }

    }

    // CData안에 받은 값을 직접 할당

    class CData {

        private String m_szLabel;
        private String m_szData;
        private int m_szData2;

        public CData(Context context, String p_szLabel, String p_szDataFile,
                     int p_szData2) {

            m_szLabel = p_szLabel;

            m_szData = p_szDataFile;

            m_szData2 = p_szData2;

        }

        public String getLabel() {
            return m_szLabel;
        }

        public String getData() {
            return m_szData;
        }

        public int getData2() {
            return m_szData2;
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

    public void onClick(View v){
        switch(v.getId()){
            case R.id.back_btn:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}
