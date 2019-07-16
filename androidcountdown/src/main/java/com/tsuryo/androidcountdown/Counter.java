package com.tsuryo.androidcountdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.tsuryo.androidcountdown.Constants.MAX_UNIT_DEF;
import static com.tsuryo.androidcountdown.Constants.TEXT_SIZE_DEF;


public class Counter extends ConstraintLayout {
    //private TextView mTvYear, mTvMonth, mTvWeek; //todo: add implementation for y,m,w
    private TextView mTvDay, mTvHour, mTvMinute,
            mTvSecond;
    private Integer mTextColor, mTextSize;
    private Integer mMaxTimeUnit;
    private String mDate;
    private boolean mIsShowingTextDesc;

    public Counter(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.timer, this, true);
        getAttributes(context, attrs);
        initViews();
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTvDay.setTextColor(mTextColor);
        mTvHour.setTextColor(mTextColor);
        mTvMinute.setTextColor(mTextColor);
        mTvSecond.setTextColor(mTextColor);
        mTvDay.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTvHour.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTvMinute.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTvSecond.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);

        TimeUnits timeUnits = TimeUnits.values()[mMaxTimeUnit];
        switch (timeUnits) {
            case SECOND:
                mTvDay.setVisibility(GONE);
                mTvHour.setVisibility(GONE);
                mTvMinute.setVisibility(GONE);
                break;
            case MINUTE:
                mTvDay.setVisibility(GONE);
                mTvHour.setVisibility(GONE);
                break;
            case HOUR:
                mTvDay.setVisibility(GONE);
                break;
        }
        if (mDate != null)
            startCounting(mDate);
    }


    private void initViews() {
        /*mTvYear = findViewById(R.id.tv_year);
        mTvMonth = findViewById(R.id.tv_month);
        mTvWeek = findViewById(R.id.tv_week);*/
        mTvDay = findViewById(R.id.tv_day);
        mTvHour = findViewById(R.id.tv_hour);
        mTvMinute = findViewById(R.id.tv_minute);
        mTvSecond = findViewById(R.id.tv_second);
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.Counter, 0, 0);
        mTextColor = typedArray.getColor(R.styleable.Counter_text_color,
                getResources().getColor(R.color.colorAccent));
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.Counter_text_size,
                TEXT_SIZE_DEF);
        mMaxTimeUnit = typedArray.getInt(R.styleable.Counter_max_time_unit,
                MAX_UNIT_DEF.getValue());
        mIsShowingTextDesc = typedArray
                .getBoolean(R.styleable.Counter_textual_description, false);
    }

    /**
     * @param date e.g: 2019-07-16T00:00:00
     */
    public void setDate(String date) {
        mDate = date;
        refresh();
    }

    public void setTextColor(Integer textColor) {
        mTextColor = textColor;
        refresh();
    }

    public void setTextSize(Integer textSize) {
        mTextSize = textSize;
        refresh();
    }

    public void setMaxTimeUnit(TimeUnits maxTimeUnit) {
        mMaxTimeUnit = maxTimeUnit.getValue();
        refresh();
    }

    public void setIsShowingTextDesc(boolean isShowingTextDesc) {
        mIsShowingTextDesc = isShowingTextDesc;
        refresh();
    }

    public Integer getTextColor() {
        return mTextColor;
    }

    public Integer getTextSize() {
        return mTextSize;
    }

    public Integer getMaxTimeUnit() {
        return mMaxTimeUnit;
    }

    public String getDate() {
        return mDate;
    }

    public boolean isShowingTextDesc() {
        return mIsShowingTextDesc;
    }

    private void startCounting(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date = null;
        Date now = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new CountDownTimer(date.getTime() - now.getTime(), 1000) {
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                setText(days, hours, minutes, seconds);
            }

            public void onFinish() {

            }
        }.start();
    }

    private void setText(long days, long hours, long minutes, long seconds) {
        if (days == 0) {
            mTvDay.setVisibility(GONE);
        }
        if (hours == 0) {
            mTvHour.setVisibility(GONE);
        }
        if (mIsShowingTextDesc) {
            mTvDay.setText(getResources().getString(R.string.tday, days));
            mTvHour.setText(getResources().getString(R.string.thour, hours));
            mTvMinute.setText(getResources().getString(R.string.tminute, minutes));
            mTvSecond.setText(getResources().getString(R.string.tsecond, seconds));
        } else {
            mTvDay.setText(getResources().getString(R.string.day, days));
            mTvHour.setText(getResources().getString(R.string.hour, hours));
            mTvMinute.setText(getResources().getString(R.string.minute, minutes));
            mTvSecond.setText(getResources().getString(R.string.second, seconds));
        }
    }

    private void refresh() {
        invalidate();
        requestLayout();
    }
}
