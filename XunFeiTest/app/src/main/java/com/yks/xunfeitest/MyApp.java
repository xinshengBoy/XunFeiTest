package com.yks.xunfeitest;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

/**
 * Created by admin on 2017/4/27.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        SpeechUtility.createUtility(MyApp.this, "appid=" + getString(R.string.app_id));
        super.onCreate();
    }
}
