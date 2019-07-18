package com.tsuryo.androidcountdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.tsuryo.androidcountdown.Constants.MAX_UNIT_DEF;
import static com.tsuryo.androidcountdown.Constants.TEXT_SIZE_DEF;
import static java.util.concurrent.TimeUnit.MILLISECONDS;


public class Counter extends ConstraintLayout {
    //private TextView mTvYear, mTvMonth, mTvWeek; //todo: add implementation for y,m,w
    private TextView mTvDay, mTvHour, mTvMinute,
            mTvSecond;
    private Integer mTextColor, mTextSize;
    private Integer mMaxTimeUnit;
    private String mDate;
    private boolean mIsShowingTextDesc;
    private Typeface mTypeFace;

    public Counter(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.timer, this, true);
        initViews();
        getAttributes(context, attrs);
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
        if (mTypeFace != null)
            setFont();
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
        mTypeFace = getTypeFace(typedArray.getInt(R.styleable.Counter_counter_font,
                CounterFont.REGULAR.getValue()));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            if (typedArray.getFont(R.styleable.Counter_custom_font) != null)
                mTypeFace = typedArray.getFont(R.styleable.Counter_custom_font);
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

    public void setTypeFace(Typeface typeFace) {
        mTypeFace = typeFace;
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

    public Typeface getTypeFace() {
        return mTypeFace;
    }

    public boolean isShowingTextDesc() {
        return mIsShowingTextDesc;
    }

    private Typeface getTypeFace(int fontType) {
        Typeface tf = null;
        CounterFont counterFont = CounterFont.values()[fontType];
        switch (counterFont) {
            case REGULAR:
                break;
            case DIGITAL:
                tf = ResourcesCompat.getFont(getContext(), R.font.digi);
                break;
            case DIGITAL_BOLD:
                tf = ResourcesCompat.getFont(getContext(), R.font.digib);
                break;
            case DIGITAL_ITALIC:
                tf = ResourcesCompat.getFont(getContext(), R.font.digii);
                break;
            case DIGITAL_ITALIC_BOLD:
                tf = ResourcesCompat.getFont(getContext(), R.font.digit);
                break;
        }
        return tf;
    }

    private void setFont() {
        if (mTypeFace != null) {
            mTvDay.setTypeface(mTypeFace);
            mTvHour.setTypeface(mTypeFace);
            mTvMinute.setTypeface(mTypeFace);
            mTvMinute.setTypeface(mTypeFace);
            mTvSecond.setTypeface(mTypeFace);
        }
    }

    private void startCounting(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
                Locale.getDefault());
        Date date = null;
        Date now = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new CountDownTimer(date.getTime() - now.getTime(), 1000) {
            public void onTick(long millisUntilFinished) {
                long mMinutes = MILLISECONDS.toMinutes(millisUntilFinished);
                long mHours = MILLISECONDS.toHours(millisUntilFinished);

                long days = MILLISECONDS.toDays(millisUntilFinished);
                long hours = days >= 1 && mMaxTimeUnit < TimeUnits.HOUR.getValue() ?
                        MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(days) :
                        MILLISECONDS.toHours(millisUntilFinished);
                long minutes = mHours >= 1 && mMaxTimeUnit < TimeUnits.MINUTE.getValue() ?
                        MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS
                                .toMinutes(MILLISECONDS.toHours(millisUntilFinished)) :
                        MILLISECONDS.toMinutes(millisUntilFinished);
                long seconds = mMinutes >= 1 && mMaxTimeUnit < TimeUnits.SECOND.getValue() ?
                        MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                MILLISECONDS.toMinutes(millisUntilFinished)) :
                        MILLISECONDS.toSeconds(millisUntilFinished);
                setText(days, hours, minutes, seconds);
            }

            public void onFinish() {

            }
        }.start();
    }

    private void setText(long days, long hours, long minutes, long seconds) {
        if (days == 0 && mMaxTimeUnit >
                TimeUnits.DAY.getValue()) {
            mTvDay.setVisibility(GONE);
        }
        if (hours == 0 && mMaxTimeUnit >
                TimeUnits.HOUR.getValue()) {
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
