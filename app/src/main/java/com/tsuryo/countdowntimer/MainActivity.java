package com.tsuryo.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.tsuryo.androidcountdown.Counter;
import com.tsuryo.androidcountdown.TimeUnits;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private Counter mCounter;
    private Counter mCounter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCounter = findViewById(R.id.counter);
        mCounter1 = findViewById(R.id.counte1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCounter.setDate("2019-07-16T16:33:00");
        mCounter1.setDate("2019-07-16T16:33:00");

//        mCounter.setIsShowingTextDesc(true);
//        mCounter.setTextColor(R.color.colorPrimary);
//        mCounter.setMaxTimeUnit(TimeUnits.DAY);
//        mCounter.setTextSize(30);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
