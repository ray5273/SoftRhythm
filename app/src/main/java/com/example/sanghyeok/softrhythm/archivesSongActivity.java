package com.example.sanghyeok.softrhythm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sanghyeok on 2015-06-10.
 */
public class archivesSongActivity extends FragmentActivity implements View.OnClickListener{
    private ListView listview;
    private Button back;
    //  �����͸� ������ Adapter
    DataAdapter adapter;
        String server="http://203.252.53.3";
    // �����͸� ���� �ڷᱸ��
    private ArrayList<CData> alist;
    String artist,title;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.archives_song);
        back = (Button)findViewById(R.id.backpage);
        back.setOnClickListener(this);
        // ������ ����Ʈ�信 ����� �����俬��
        listview = (ListView) findViewById(R.id.songlist);

        // ��ü�� �����մϴ�
        alist = new ArrayList<CData>();

        // �����͸� �ޱ����� �����;���� ��ü ����

        adapter = new DataAdapter(this, alist);

        // ����Ʈ�信 ����� ����
        listview.setAdapter(adapter);

        StringBuilder sb = new StringBuilder();


        try {
            artist=archivesActivity.artist;
            title=archivesActivity.title;

            // URL url = new URL("http://221.140.84.135/test.php");
            URL url = new URL(server+"/rank.php"+"?artist="+artist+"&title="+title);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {



                conn.setConnectTimeout(10000);

                conn.setUseCaches(false);

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

            String total = "";
            String grade="";

            JSONArray ja = new JSONArray(jsonString);


            adapter.clear();
            for (int i = 0; i < ja.length(); i++) {

                JSONObject jo = ja.getJSONObject(i);

                //�����
                //
                grade=jo.getString("grade");
                total=jo.getString("total");
                if(grade.equals("challenger"))
                    adapter.add(new CData(getApplicationContext(), "# "+Integer.toString(i+1),
                        total, R.drawable.challenger));
                else if(grade.equals("diamond"))
                    adapter.add(new CData(getApplicationContext(), "# "+Integer.toString(i+1),
                            total, R.drawable.bronze));
                else if(grade.equals("platinum"))
                    adapter.add(new CData(getApplicationContext(), "# "+Integer.toString(i+1),
                            total, R.drawable.platinum));
                else if(grade.equals("gold"))
                    adapter.add(new CData(getApplicationContext(),"# "+ Integer.toString(i+1),
                            total, R.drawable.gold));
                else if(grade.equals("silver"))
                    adapter.add(new CData(getApplicationContext(),"# "+ Integer.toString(i+1),
                            total, R.drawable.silver));
                else if(grade.equals("bronze"))
                    adapter.add(new CData(getApplicationContext(), "# "+Integer.toString(i+1),
                            total, R.drawable.bronze));

            }



            //��� ���



        } catch (JSONException e) {

            // TODO Auto-generated catch block

            adapter.add(new CData(getApplicationContext(), e.toString(),
                    "", R.drawable.ic_launcher));

        }



    }


    private class DataAdapter extends ArrayAdapter<CData> {
        // ���̾ƿ� XML�� �о���̱� ���� ��ü
        private LayoutInflater mInflater;

        public DataAdapter(Context context, ArrayList<CData> object) {

            // ���� Ŭ������ �ʱ�ȭ ����
            // context, 0, �ڷᱸ��
            super(context, 0, object);
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        // �������� ��Ÿ���� �ڽ��� ���� xml�� ���̱� ���� ����
        @Override
        public View getView(int position, View v, ViewGroup parent) {
            View view = null;

            // ���� ����Ʈ�� �ϳ��� �׸� ���� ��Ʈ�� ���

            if (v == null) {

                // XML ���̾ƿ��� ���� �о ����Ʈ�信 ����
                view = mInflater.inflate(R.layout.song_list, null);
            } else {

                view = v;
            }

            // �ڷḦ �޴´�.
            final CData data = this.getItem(position);

            if (data != null) {
                // ȭ�� ���
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

                // �̹����信 �ѷ��� �ش� �̹������� ���� �� ����° �μ���
                iv.setImageResource(data.getData2());

            }

            return view;

        }

    }

// CData�ȿ� ���� ���� ���� �Ҵ�

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
            case R.id.backpage:
                Intent intent = new Intent(this, archivesActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}



