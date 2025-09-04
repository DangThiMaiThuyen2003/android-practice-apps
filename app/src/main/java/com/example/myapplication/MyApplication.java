package com.example.myapplication;

import android.app.Application;

public class MyApplication extends Application {
    private MyViewModel sharedViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedViewModel = new MyViewModel(this);
    }

    public MyViewModel getSharedViewModel() {
        return sharedViewModel;
    }
} 