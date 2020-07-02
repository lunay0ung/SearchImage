package com.luna.searchimage.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luna.searchimage.service.source.local.Bookmark
import com.luna.searchimage.service.source.local.BookmarkDatabase
import com.luna.searchimage.service.source.BookmarkRepository
import com.luna.searchimage.model.Image
import com.luna.searchimage.service.source.remote.ImageDataSource
import com.luna.searchimage.service.source.remote.ImageDataSourceFactory
import kotlinx.coroutines.launch


class ImageListViewModel(
    context: Context,
    keyword: String
) : ViewModel()
{

    private val TAG = ImageListViewModel::class.java.simpleName
    private val repository: BookmarkRepository
    var imagePagedList: LiveData<PagedList<Image>>
    private var liveDataSource: LiveData<ImageDataSource>
    init {

        val itemDataSourceFactory =
            ImageDataSourceFactory(
                context,
                keyword
            )
        liveDataSource = itemDataSourceFactory.imageLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ImageDataSource.PAGE_SIZE)
            .build()
        imagePagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()

        val bookmarkDao = BookmarkDatabase
            .getDatabase(context, viewModelScope, context.resources)
            .bookmarkDao()
        repository =
            BookmarkRepository(bookmarkDao)
    }

    fun getAllBookmark(): LiveData<List<Bookmark>> {
        return repository.getAllBookmark()
    }

    fun getBookmarkByKeyword(keyword: String): List<Bookmark> {
        return repository.getBookmarkByKeyword(keyword)
    }

    fun insertBookmark(bookmark: Bookmark) = viewModelScope.launch {
        repository.insertBookmark(bookmark)
    }

    fun deleteBookmark(bookmark: Bookmark) = viewModelScope.launch {
        repository.deleteBookmark(bookmark)
    }

}