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
    private View collapsedView;
    private View expandedView;

    /**
     * Set initial params
     * @param mWindowManager
     * @param mOverlayView
     * @param params
     */
    public void setParams(WindowManager mWindowManager, View mOverlayView,
                          WindowManager.LayoutParams params){
        this.params = params;
        this.mWindowManager = mWindowManager;
        this.mOverlayView = mOverlayView;
    }

    public void setViews(View collapsedView, View expandedView){
        this.collapsedView = collapsedView;
        this.expandedView = expandedView;
    }

    /**
     * Specify movements of the widget on the home screen
     * @param v
     * @param event
     * @return
     */
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
                collapsedView.setVisibility(View.GONE);
                expandedView.setVisibility(View.VISIBLE);
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