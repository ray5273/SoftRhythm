package com.example.sanghyeok.softrhythm;

import android.view.Display;
import android.view.WindowManager;

public class Note {
    public  int x, y;    //starting position
    public int velocity;        //velocity

    // ---------------------------------
    //             constructor
    // ---------------------------------
    public Note(int _x, int _y, int _velocity) {

        x = _x;
        y = _y;

        velocity = _velocity;
    }

    // ---------------------------------
    //             move
    // ---------------------------------
    public boolean Move() {

        y += velocity;

        if (x < 0 || x > MyGameView.Width || y < 0 || y > MyGameView.Height)
            return true;
        else
            return false;
    }

} // Note ³¡