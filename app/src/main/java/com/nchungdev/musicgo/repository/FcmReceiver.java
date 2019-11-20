package com.nchungdev.musicgo.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FcmReceiver extends BroadcastReceiver {

    private final MutableLiveData<Song> mData = new MutableLiveData<>();

    public LiveData<Song> getData() {
        return mData;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // entry point of data

    }
}