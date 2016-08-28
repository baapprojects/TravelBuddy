package com.hari.aund.travelbuddy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.hari.aund.travelbuddy.R;

public class SplashActivity extends AppCompatActivity
        implements View.OnClickListener {

    private final String LOG_TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        TextView startTextView = (TextView) findViewById(R.id.start_text_id);
        startTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_text_id) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Log.d(LOG_TAG, "onClick : Intent Triggered " + "MainActivity");
        }
    }
}
