package com.luna.searchimage.service.source.remote

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.luna.searchimage.model.Image
import com.luna.searchimage.service.source.remote.ImageDataSource

class ImageDataSourceFactory(
    context: Context,
    var keyword: String
) : DataSource.Factory<Int, Image>() {

    val imageLiveDataSource = MutableLiveData<ImageDataSource>()
    val mCtx = context
    override fun create(): DataSource<Int, Image> {
        val imageDataSource =
            ImageDataSource(
                mCtx,
                keyword
            )
        imageLiveDataSource.postValue(imageDataSource)
        return imageDataSource
    }
}