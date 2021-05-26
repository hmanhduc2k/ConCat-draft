package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.gesture.Gesture;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Testing push and commit on git
 */
import android.view.GestureDetector;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, View.OnLongClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.HiBtn:
                Toast.makeText(this, "Hi, I am Duc", Toast.LENGTH_LONG).show();
                Toast.makeText(this, "Hi, I am Hung", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHi = findViewById(R.id.HiBtn);
        btnHi.setOnClickListener(this);

        btnHi.setOnLongClickListener(this);
    }


    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, "Long press", Toast.LENGTH_SHORT).show();
        return true;
    }
}