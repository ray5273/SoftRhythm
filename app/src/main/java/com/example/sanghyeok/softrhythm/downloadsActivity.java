package com.example.sanghyeok.softrhythm;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanghyeok on 2015-05-27.
 */
public class downloadsActivity  extends FragmentActivity implements View.OnClickListener{
    private ListView listview;
    private Button back;
    // 데이터를 연결할 Adapter
    DataAdapter adapter;
    private DownloadManager mDownloadManager; //다운로드 매니저.
    private int mDownloadQueueId; //다운로드 큐 아이디..
    private String mFileName ; //파일다운로드 완료후...파일을 열기 위해 저장된 위치를 입력해둔다.
    public static String artist;
    public static String title;
    String server="http://203.252.53.3";//"http://221.140.84.135";

    // 데이터를 담을 자료구조
    private ArrayList<CData> alist;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.downloads);
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
        listview.setOnItemClickListener(new  ListViewItemClickListener());

        StringBuilder sb = new StringBuilder();


        try {

            URL url = new URL(server+"/music_exist.php");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {



                conn.setConnectTimeout(10000);

                conn.setUseCaches(false);

                //In server, get download data and print to listview

                adapter.add(new CData(getApplicationContext(), String.valueOf(conn.getResponseCode()) + "\n"
                        + String.valueOf(HttpURLConnection.HTTP_OK),
                        "", R.drawable.ic_launcher));

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader br = new BufferedReader(

                            new InputStreamReader(conn.getInputStream()));



                    while (true) {

                        String line = br.readLine();

                        if (line == null)

                            break;

                        sb.append(line + "\n");

                    }

                    br.close();



                } else {

                    adapter.add(new CData(getApplicationContext(), "http_not",
                            "", R.drawable.ic_launcher));


                }

                conn.disconnect();

            }

        } catch (Exception e) {

            adapter.add(new CData(getApplicationContext(), e.toString(),
                    "", R.drawable.ic_launcher));

        }



        String jsonString = sb.toString();



        try {

            String songname = "";
            String singer="";

            JSONArray ja = new JSONArray(jsonString);


            adapter.clear();
            for (int i = 0; i < ja.length(); i++) {

                JSONObject jo = ja.getJSONObject(i);

                songname=jo.getString("songname");
                singer=jo.getString("singer");
                adapter.add(new CData(getApplicationContext(), singer,
                        songname, R.drawable.ic_launcher));

            }



            //결과 출력



        } catch (JSONException e) {

            // TODO Auto-generated catch block

            adapter.add(new CData(getApplicationContext(), e.toString(),
                    "", R.drawable.ic_launcher));

        }



    }
    private class ListViewItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
        {
            AlertDialog.Builder alertDlg = new AlertDialog.Builder(view.getContext());
            alertDlg.setPositiveButton( "확인", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which ) {                  //선택적으로 정보를 받아야함
                    artist = (String) ((TextView)view.findViewById(R.id.singerName)).getText();// 개쩔었다.
                    title=   (String) ((TextView)view.findViewById(R.id.songName)).getText();


                    startDownload(artist,title);
                    Toast toast= Toast.makeText(getApplicationContext(),title+artist,Toast.LENGTH_LONG);
                    toast.show();

                }
            });
            alertDlg.setNegativeButton("취소",new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick( DialogInterface dialog, int which ) {

                }
            });
            alertDlg.setMessage("해당곡을 다운로드 하시겠습니까?");
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
                // in textview give  title, and singername
                TextView tv = (TextView) view.findViewById(R.id.singerName);
                TextView tv2 = (TextView) view.findViewById(R.id.songName);

                tv.setText(data.getLabel());
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(20);
                tv.setTypeface(Typeface.createFromAsset(getAssets(), "bm_hanna.ttf"));

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

    public void onClick(View v){
        switch(v.getId()){
            case R.id.back_btn:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
    //여기서부터 다운로드 관련

    private void startDownload(String artist,String title) {
        //String url = "http://221.140.84.135/";
        String url = server+"/";
        String url1= url;
        String url2= url1;
        url+=artist+"_"+title+".dat";
        url1+=artist+"_"+title+".txt";
        url2+=artist+"_"+title+".png";
        new downloadFileAsync(this).execute(url, "dat", "1");           //파일 갯수만큼 downloadFileAsync 를 추가해주면된다. 2째꺼는 파일 형식
        new downloadFileAsync(this).execute(url1, "txt", "1");
        new downloadFileAsync(this).execute(url2, "png", "1");

    }



}


