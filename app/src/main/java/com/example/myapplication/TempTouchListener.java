package com.example.myapplication;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class TempTouchListener implements View.OnTouchListener{
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    private View mOverlayView;

    public void setParams(WindowManager mWindowManager, View mOverlayView,
                          WindowManager.LayoutParams params){
        this.params = params;
        this.mWindowManager = mWindowManager;
        this.mOverlayView = mOverlayView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = params.x;
                initialY = params.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            case MotionEvent.ACTION_MOVE:
                float xDiff = Math.round(event.getRawX() - initialTouchX);
                float yDiff = Math.round(event.getRawY() - initialTouchY);
                params.x = initialX + (int) xDiff;
                params.y = initialY + (int) yDiff;
                mWindowManager.updateViewLayout(mOverlayView, params);
                return true;
        }
        return false;
    }
}