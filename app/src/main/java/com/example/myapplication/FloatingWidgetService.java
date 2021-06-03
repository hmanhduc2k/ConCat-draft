package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.andremion.counterfab.CounterFab;

/**
 * Credit to Journal Dev. This is an attempt to replicate
 * the floating button overlaying the user's home screen
 */

public class FloatingWidgetService extends Service {


    private WindowManager mWindowManager;
    private View mOverlayView;
    CounterFab counterFab;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Specific for overlaying layout: Creation of the widget, permitting
     * the widget to overlay home screen, set initial stage and actions
     */
    @Override
    public void onCreate() {
        super.onCreate();

        setTheme(R.style.Theme_MyApplication);

        mOverlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mOverlayView, params);

        counterFab = mOverlayView.findViewById(R.id.fabHead);
        counterFab.setCount(1);

        TempTouchListener tempTouchListener = new TempTouchListener();
        tempTouchListener.setParams(mWindowManager, mOverlayView, params);
        counterFab.setOnTouchListener(tempTouchListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOverlayView != null)
            mWindowManager.removeView(mOverlayView);
    }

}