package com.example.sanghyeok.softrhythm;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sanghyeok on 2015-06-20.
 */

public class MyGameView extends SurfaceView implements SurfaceHolder.Callback {

    //-------------------------//
    //         variables
    // ------------------------//
    public  static int RUN = 1;
    public  static int PAUSE = 2;
    public  int mMode = RUN;



    private boolean isPlay = false;


    private SurfaceHolder mHolder;
    private MyThread   mThread;
    private Context mContext;

    public  static int Width, Height, cx, cy;
    private int x1, y1, x2, y2;
    private int sx1, sy1, sx2, sy2;
    private Bitmap imgBack1;

    private long counter = 0;
    private  boolean canRun = true;


    private Bitmap[]   Button = new Bitmap[3];
    private Rect[]     ButtonRect = new Rect[3];
    private Rect[]     Perfect = new Rect[3];

    private Integer[]  Button_x = new Integer[3];
    private Integer[]  Button_y = new Integer[3];

    private Bitmap[]   NoteImage = new Bitmap[1];

    private Bitmap[]   Effect = new Bitmap[3];


    private int nw, nh;
    private int bw, bh;
    private int ew, eh;

    private int velocity;

    private int period;

    private int Effect_type;

    private int combo;
    private int combo_max;

    private int score;
    private int perfect_score;
    private int good_score;
    private int perfect_count;
    private int good_count;
    private int miss_count;

    private int life;

    private ArrayList<Note> Note;

    Paint paint = new Paint();

    MediaPlayer mPlayer = new MediaPlayer();

    public String artist=selectSongActivity.artist;
    public String title=selectSongActivity.title;
    public String filePath;

    // ---------------------------------
    //    ReadSprite
    // ---------------------------------
    public void ReadSprite(Context context) {
        //Display
        Display display = ((WindowManager)context.getSystemService(context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Width = display.getWidth();
        Height = display.getHeight();
        cx = Width / 2;
        cy = Height / 2;

        Resources res = context.getResources();

        imgBack1 = BitmapFactory.decodeResource(res, R.drawable.cosmos);
        imgBack1 = Bitmap.createScaledBitmap(imgBack1, Width, Height, true);


        //initializing variables
        x1 = cx;
        y1 = cy;

        sx1 = 1;
        sy1 = 1;
        sx2 = 2;
        sy2 = 2;

        nw = Width / 3;     //Note width and height
        nh = Height / 20;

        velocity = Height / 192;       //Note velocity

        bw = Width / 3;         //Button width and height
        bh = Height / 15;

        ew = Width / 2;         //Effect width and height
        eh = Height / 5;

        period = 2000/11;       //period per Note

        Effect_type = -1;       //type of effect

        perfect_score = 120;    //score of note
        good_score = 100;

        paint.setTextSize(Width / 10);
        paint.setColor(Color.WHITE);

        life = 20;      //life


        //initializing images

        Button[0] = BitmapFactory.decodeResource(res, R.drawable.button1);
        Button[0] = Bitmap.createScaledBitmap(Button[0],bw, bh, true);

        Button[1] = BitmapFactory.decodeResource(res, R.drawable.button2);
        Button[1] = Bitmap.createScaledBitmap(Button[1],bw, bh, true);

        Button[2] = BitmapFactory.decodeResource(res, R.drawable.button3);
        Button[2] = Bitmap.createScaledBitmap(Button[2],bw, bh, true);


        NoteImage[0] = BitmapFactory.decodeResource(res, R.drawable.note);
        NoteImage[0] = Bitmap.createScaledBitmap(NoteImage[0], nw,nh, true);


        Effect[0] = BitmapFactory.decodeResource(res, R.drawable.perfect);
        Effect[0] = Bitmap.createScaledBitmap(Effect[0], ew, eh, true);

        Effect[1] = BitmapFactory.decodeResource(res, R.drawable.good);
        Effect[1] = Bitmap.createScaledBitmap(Effect[1], ew, eh, true);

        Effect[2] = BitmapFactory.decodeResource(res, R.drawable.miss);
        Effect[2] = Bitmap.createScaledBitmap(Effect[2], ew, eh, true);


        for (int i = 0; i < 3; i++) {

            Button_x[i] = bw*(i+1);
            Button_y[i] = Height - Height / 20;

            ButtonRect[i] = new Rect(Button_x[i] - bw, Button_y[i] - bh, Button_x[i], Button_y[i]);
            Perfect[i] = new Rect(Button_x[i] - bw*3/4, Button_y[i] - bh*3/4, Button_x[i] - bw/4, Button_y[i] - bh/4);
        }

    }

    // ---------------------------------
    //          MyGame View
    // ---------------------------------
    public MyGameView(Context context) {
        super(context);


        artist=selectSongActivity.artist;
        title=selectSongActivity.title;

        filePath=artist+"_"+title;

        SurfaceHolder mHolder = getHolder();
        mHolder.addCallback(this);

        ReadSprite(context);

        mThread = new MyThread(mHolder, context);
        mThread.FileRead();
        setFocusable(true);
        Note = new ArrayList<Note>();


    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }


    // ---------------------------------
    //    MyThread
    // ---------------------------------
    public class MyThread extends Thread {

        public MyThread(SurfaceHolder Holder, Context context) {
            mHolder = Holder;
            mContext = context;
        }

        // ---------------------------------
        //      Run
        // ---------------------------------
        public void run() {
            Rect src = new Rect();
            Rect dst = new Rect();
            dst.set(0, 0, Width, Height);
            Rect src1 = new Rect();

            while (canRun) {
                Canvas canvas = null;

                if(isPlay == false) {
                    try {
                        isPlay = true;

                        mPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/SoftRhythm/" + "/Data/" + filePath + "/" + filePath + ".dat");

                        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mPlayer.start();

                            }
                        });

                        mPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try{
                    canvas = mHolder.lockCanvas();

                    synchronized (mHolder) {

                        ScrollImage();
                        src.set(x1, y1, x1 + cx, y1 + cy);
                        src1.set(x2, y2, x2 + cx, y2 + cy);

                        canvas.drawBitmap(imgBack1, src, dst, null);       //BackgroundImage

                        MoveAndDrawNote(canvas);    //Note


                        if(Effect_type >= 0)    //Effect
                            canvas.drawBitmap(Effect[Effect_type], cx-ew/2, cy-eh/2, null);


                        if(combo > 0)        //combo
                            canvas.drawText("combo" + combo + "!", cx - Width / 6, cy + Height / 13, paint);


                        canvas.drawText("score : " + score + "", cx - Width / 5, Height / 19, paint);     //score


                        for (int i = 0; i < 3; i++)     //Button
                            canvas.drawBitmap(Button[i], Button_x[i] - bw, Button_y[i] - bh, null);

                    }
                } finally {
                    if (canvas != null)
                        mHolder.unlockCanvasAndPost(canvas);
                }
            } // while
        } // run

        public void onPrePared(MediaPlayer player){

            player.start();
        }
        // ---------------------------------
        //          FileRead
        // ---------------------------------
        public void FileRead(){

           //read file and get notes from sdcard

            File file = new File(Environment.getExternalStorageDirectory()+"/SoftRhythm/"+"/Data/"+filePath+"/"+filePath+".txt");
            FileReader filereader = null;

            try {
                filereader = new FileReader(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            final BufferedReader reader = new BufferedReader(filereader);


            final Timer timer = new Timer();
            TimerTask timetask = new TimerTask() {

                @Override
                public void run() {

                    int i;

                    try {
                        if((i = reader.read()) != -1) {

                            i -= 48;

                            if(i>0)
                                MakeNote(i-1);

                        }

                        else        //file end, game over
                        {
                            timer.cancel();
                            Intent intent = new Intent(getContext(), resultActivity.class);
                            intent.putExtra("cool",perfect_score * perfect_count+"");
                            intent.putExtra("good",good_score * good_count + "");
                            intent.putExtra("bad","0");
                            intent.putExtra("miss", "0");
                            intent.putExtra("combo", combo_max + "");
                            intent.putExtra("artist",artist);
                            intent.putExtra("title",title);
                            getContext().startActivity(intent);
                            System.exit(0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            timer.schedule(timetask, 0, period);

        }

        // ---------------------------------
        //            MakeNote
        // ---------------------------------
        private void MakeNote(int i) {

            synchronized (mHolder) {

                Note.add(new Note(Button_x[i], 0, velocity));   //add Note

            }
        }


        // ---------------------------------
        //          MoveAndDrawNote
        // ---------------------------------
        private void MoveAndDrawNote(Canvas canvas) {
            if(mMode == PAUSE || Note.size() == 0) {

            }
            for(int i=0; i < Note.size(); i++) {
                if (Note.get(i).Move() == true) {   //out of display

                    Note.remove(i);

                    Effect_type = 2;

                    combo = 0;

                    miss_count++;   //miss count

                    life--;


                    //game over
                    if(life == 0) {
                        Intent intent = new Intent(getContext(), resultActivity.class);
                        intent.putExtra("cool",perfect_score * perfect_count+"");
                        intent.putExtra("good",good_score * good_count + "");
                        intent.putExtra("bad",miss_count+"");
                        intent.putExtra("miss", miss_count+"");
                        intent.putExtra("combo", combo_max + "");
                        intent.putExtra("artist",artist);
                        intent.putExtra("title",title);
                        getContext().startActivity(intent);
                        System.exit(0);
                    }

                    break;
                }
            }

            for(int i=0; i < Note.size(); i++) {

                canvas.drawBitmap(NoteImage[0], Note.get(i).x - nw, Note.get(i).y - nh, null);
            }
        } // MoveAndDrawNote


        // ---------------------------------
        //          ScrollImage
        // ---------------------------------
        public void ScrollImage() {
            if (mMode == PAUSE)
                return;

            counter++;

            y2 += sy2;

            if(x2 < 0)
                x2 = cx;
            else if(x2 > cx)
                x2 = 0;
            else if(y2 < 0)
                y2 = cy;
            else if(y2 > cy)
                y2 = 0;

            if (counter % 2 == 0) {

                y1 += sy1;

                if(x1 < 0)
                    x1 = cx;
                else if(x1 > cx)
                    x1 = 0;
                else if(y1 < 0)
                    y1 = cy;
                else if(y1 > cy)
                    y1 = 0;
            }
        }
    } // Thread




    // ---------------------------------
    //        onTouchEvent
    // ---------------------------------
    public boolean onTouchEvent(MotionEvent event) {
        if(mMode == PAUSE)
            return false;


        if(event.getAction() == MotionEvent.ACTION_DOWN) {

            //get position
            int x = (int) event.getX();
            int y = (int) event.getY();


            //checking clicked point
            int i;
            for(i = 0; i < 3; i++) {
                if(ButtonRect[i].contains(x, y) == true)
                    break;
            }

            //button is clicked
            if(i<3) {

                int j;
                for(j = 0; j < Note.size(); j++) {

                    int cnx = Note.get(j).x - nw / 2;
                    int cny = Note.get(j).y - nh / 2;

                    if(ButtonRect[i].contains(cnx, cny) == true) {

                        Note.remove(j);

                        combo++;
                        if(combo > combo_max)
                            combo_max = combo;

                        //Effect_type and score
                        if(Perfect[i].contains(cnx,cny) == true)      //perfect
                        {
                            Effect_type = 0;
                            perfect_count++;
                            score += perfect_score;
                        }
                        else                                         //good
                        {
                            Effect_type = 1;
                            good_count++;
                            score += good_score;
                        }

                        break;
                    }
                }

            }

            //button is not clicked
            else
                Effect_type = -1;

        } // if
        return false;
    } // touch

} // end SurfaceView