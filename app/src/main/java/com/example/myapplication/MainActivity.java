package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 123;
    private Button button;

    /**
     * After onCreate(), system will ask for permission to overlay the widget.
     * onClickListener method already settled
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askForSystemOverlayPermission();
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        /*
        https://stackoverflow.com/questions/49646530/why-is-this-used-in-setonclicklistener
         */
    }

    /**
     * Ask to overlay the home screen by creating intent that parses the package of the
     * current activity
     */
    private void askForSystemOverlayPermission(){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    /**
     * What happens on clicking the button
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            startService(new Intent(MainActivity.this, FloatingWidgetService.class));
            finish();
        } else if (Settings.canDrawOverlays(this)) {
            startService(new Intent (MainActivity.this, FloatingWidgetService.class));
            finish();
        } else {
            askForSystemOverlayPermission();
            errorToast();
        }
    }

    /**
     * What happens when the main activity is paused
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                Settings.canDrawOverlays(this)) {
            startService(new Intent(MainActivity.this, FloatingWidgetService.class)
                        .putExtra("activity_background", true));
        }
    }

    private void errorToast(){
        Toast.makeText(this, "Can't start application without permission!", Toast.LENGTH_LONG).show();
    }
}