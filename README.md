[![Release](https://jitpack.io/v/tsuryo/Android-Countdown-Timer.svg)](https://jitpack.io/#tsuryo/Android-Countdown-Timer)
# Android-Countdown-Timer
Android library provide a simple to use Countdown Timer View.

<img width="175" alt="Android Countdown timer" src="https://user-images.githubusercontent.com/42518244/61465838-8b08dd00-a981-11e9-9663-a1f0f21724b4.png">

### Prerequisites
Android 5.0+ API 21+
# Features

* Customizable fonts (color, size, fonts resources).
* Choose maximum time unit to show.
* Built in fonts to choose,
  app:counter_font="DIGITAL|DIGITAL_BOLD|DIGITAL_ITALIC|DIGITAL_ITALIC_BOLD|REGULAR"

# Usage
### Java
```Java
        mCounter = findViewById(R.id.counter);
	Date date = format.parse("2019-07-22T18:33:00");
        mCounter.setDate(date);//countdown starts
	//or use: 
	mCounter.setDate("2019-07-19T18:33:00"); //countdown starts
	
	/*
         * Additional attributes:
         * */
        mCounter.setIsShowingTextDesc(true);
        mCounter.setTextColor(R.color.colorPrimary);
        mCounter.setMaxTimeUnit(TimeUnits.DAY);
        mCounter.setTextSize(30);
	mCounter.setTypeFace(ResourcesCompat.getFont(this, R.font.batmfa__));
	
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
```
### XML
```XML
    <com.tsuryo.androidcountdown.Counter
        android:id="@+id/counter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
	
	<!--Additional attributes-->
        app:max_time_unit="HOUR"
        app:text_color="@color/colorPrimary"
        app:text_size="36dp"
        app:custom_font="@font/batmfa__" />

    <com.tsuryo.androidcountdown.Counter
        android:id="@+id/counte1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
	
	<!--Additional attributes-->
        app:max_time_unit="DAY"
        app:text_color="#2196F3"
        app:text_size="36dp"
        app:textual_description="true"
        app:counter_font="DIGITAL_BOLD" />

```
### Installing

Add the JitPack repository to your build file.
Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add the dependency
```
dependencies {
	implementation 'com.github.tsuryo:Android-Countdown-Timer:1.1.1'
}
```
