package com.nchungdev.musicgo.repository

import androidx.lifecycle.MutableLiveData

class DistinctLiveData<T> : MutableLiveData<T>() {

    private var current: T? = null

    override fun setValue(value: T?) {
        if (current == value) {
            return
        }
        current = value
        super.setValue(value)
    }
}
