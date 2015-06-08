package com.example.sanghyeok.softrhythm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
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
    String[] folders;
    String[] dir_name;
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
        listview.setOnItemClickListener(new  ListViewItemClickListener());        // ArrayAdapter를 통해서 ArrayList에 자료 저장
        // 하나의 String값을 저장하던 것을 CData클래스의 객체를 저장하던것으로 변경
        // CData 객체는 생성자에 리스트 표시 텍스트뷰1의 내용과 텍스트뷰2의 내용 그리고 사진이미지값을 어댑터에 연결

        // CData클래스를 만들때의 순서대로 해당 인수값을 입력
        // 한줄 한줄이 리스트뷰에 뿌려질 한줄 한줄이라고 생각하면 됩니다.'

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path = path + "/SoftRhythm/Data";

        File dir = new File(path);

        folders=getTitleList();

        if( !dir.exists() || folders.length==0 )
            adapter.add(new CData(getApplicationContext(),"곡이 없습니다",
                    "곡을 다운받아 주십시오", R.drawable.ic_launcher));

        if(folders!=null)
          for(int j = 0 ;j<folders.length;j++) {                  //폴더의 정보들을 불러와서 listView 에 추가 정보가 없을시 곡이없습니다 출력
                dir_name = folders[j].split("_");
                adapter.add(new CData(getApplicationContext(), dir_name[0],
                      dir_name[1], R.drawable.ic_launcher));
          }

    }

    private class ListViewItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
            alertDlg.setPositiveButton( "확인", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which ) {                  //선택적으로 정보를 받아야함
                    Intent intent = new Intent(getApplicationContext(), gameActivity.class); // 잘모르겠네여기 왜 getApplicationContext쓰지?
                    startActivity(intent);
                    finish();
                }
            });
            alertDlg.setNegativeButton("취소",new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which ) {

            }
            });
            alertDlg.setMessage("해당곡을 실행하시겠습니까?");
            alertDlg.show();
        }
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

    private String[] getTitleList() //알아 보기 쉽게 메소드 부터 시작합니다.
    {
        try
        {
            FileFilter fileFilter = new FileFilter() {
                public boolean accept(File file) {
                    return file.isDirectory();
                }
            };

            File file = new File(Environment.getExternalStorageDirectory()+"/SoftRhythm/Data/"); //경로를 SD카드로 잡은거고 그 안에 있는 A폴더 입니다. 입맛에 따라 바꾸세요.

            File[] files = file.listFiles(fileFilter);//위에 만들어 두신 필터를 넣으세요. 만약 필요치 않으시면 fileFilter를 지우세요.

            String [] titleList = new String [files.length]; //파일이 있는 만큼 어레이 생성했구요

            for(int i = 0;i < files.length;i++)

            {
                titleList[i] = files[i].getName();	//루프로 돌면서 어레이에 하나씩 집어 넣습니다.
            }//end for
            return titleList;

        } catch( Exception e )
        {
            return null;
        }//end catch()

    }//end getTitleList

}


