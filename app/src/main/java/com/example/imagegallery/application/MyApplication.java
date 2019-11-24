package com.example.imagegallery.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}