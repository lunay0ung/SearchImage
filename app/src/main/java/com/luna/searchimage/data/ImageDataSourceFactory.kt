package com.luna.searchimage.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class ImageDataSourceFactory(
    context: Context,
    var keyword: String
) : DataSource.Factory<Int, Image>() {

    val imageLiveDataSource = MutableLiveData<ImageDataSource>()
    val mCtx = context
    override fun create(): DataSource<Int, Image> {
        val imageDataSource = ImageDataSource(mCtx, keyword)
        imageLiveDataSource.postValue(imageDataSource)
        return imageDataSource
    }
}