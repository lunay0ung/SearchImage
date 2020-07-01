package com.luna.searchimage.bookmark

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.luna.searchimage.data.Image

class BookmarkViewModel(application: Application) : AndroidViewModel(application){


    private val repository: BookmarkRepository

    init {
        val bookmarkDao = BookmarkDatabase
            .getDatabase(application, viewModelScope, application.resources)
            .bookmarkDao()
        repository = BookmarkRepository(bookmarkDao)
    }

    fun getAllBookmark(): LiveData<List<Bookmark>> {
        return repository.getAllBookmark()
    }


}