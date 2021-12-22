package com.baker.smart.lib.demo;

import android.app.Application;

import com.baker.smart.guy.lib.SmartGuyEngine;

public class BaseApplication extends Application {

    public static final String ttsClientId = "d957f3355c7144c8afa2d0e0950b0563";
    public static final String ttsSecret = "40940788f0734f1b801d71a96d98d76e";
    public static final String asrClientId = "a34947e4d03240b88ba10e551e24ed38";
    public static final String asrSecret = "f62071b8845245cea4aa55aaddb76285";

    @Override
    public void onCreate() {
        super.onCreate();

        SmartGuyEngine.getInstance().initEngine(BaseApplication.this);
        SmartGuyEngine.getInstance().initTts(ttsClientId, ttsSecret);
        SmartGuyEngine.getInstance().initAsr(asrClientId, asrSecret);
    }
}
