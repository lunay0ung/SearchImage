package com.luna.searchimage.ui.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luna.searchimage.data.Image
import com.luna.searchimage.data.ImageDataSource
import com.luna.searchimage.data.ImageDataSourceFactory


class ImageListViewModel(
    context: Context,
    var keyword: String
) : ViewModel() {

    private val TAG = ImageListViewModel::class.java.simpleName
    var imagePagedList: LiveData<PagedList<Image>>
    private var liveDataSource: LiveData<ImageDataSource>
    init {

        Log.d(TAG, ">>> 검색 스타트: $keyword")
        val itemDataSourceFactory = ImageDataSourceFactory(context, keyword)
        liveDataSource = itemDataSourceFactory.imageLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ImageDataSource.PAGE_SIZE)
            .build()
        imagePagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()

    }
}