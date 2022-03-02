package com.baker.smart.lib.demo;

import android.app.Application;

import com.baker.smart.guy.lib.SmartGuyEngine;

public class BaseApplication extends Application {

    public static final String ttsClientId = "your clientId";
    public static final String ttsSecret = "your secret";
    public static final String asrClientId = "your clientId";
    public static final String asrSecret = "your secret";

    @Override
    public void onCreate() {
        super.onCreate();

        SmartGuyEngine.getInstance().initEngine(BaseApplication.this);
        SmartGuyEngine.getInstance().initTts(ttsClientId, ttsSecret);
        SmartGuyEngine.getInstance().initAsr(asrClientId, asrSecret);
    }
}
