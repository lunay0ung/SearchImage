package com.luna.searchimage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel


class ImageDetailViewModel: ViewModel() {

    private val TAG = ImageDetailViewModel::class.java.simpleName

    init {
        Log.i(TAG, "ImageDetailViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ImageDetailViewModel destroyed!")
    }
}