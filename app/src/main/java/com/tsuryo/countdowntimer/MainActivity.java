package com.tsuryo.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

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
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        try {
            Date date = format.parse("2020-06-10T18:33:00");
            mCounter.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mCounter1.setDate("2020-06-10T18:33:00");

        mCounter.setListener(new Counter.Listener() {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: Counter - " + millisUntilFinished);
            }

            @Override
            public void onTick(long days, long hours, long minutes, long seconds) {
                Log.d(TAG, "onTick: Counter - " + days + "d " +
                        hours + "h " +
                        minutes + "m " +
                        seconds + "s " );
            }
        });

//        mCounter.setIsShowingTextDesc(true);
//        mCounter.setTextColor(R.color.colorPrimary);
//        mCounter.setMaxTimeUnit(TimeUnits.DAY);
//        mCounter.setTextSize(30);
//        mCounter1.setTypeFace(ResourcesCompat.getFont(this, R.font.batmfa__));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
