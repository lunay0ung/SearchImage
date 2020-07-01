package com.luna.searchimage.bookmark

import androidx.lifecycle.LiveData
import com.luna.searchimage.bookmark.Bookmark
import com.luna.searchimage.bookmark.BookmarkDao
import com.luna.searchimage.data.Image


open class BookmarkRepository(private val bookmarkDao: BookmarkDao) {

    fun getAllBookmark(): LiveData<List<Bookmark>> {
        return bookmarkDao.getAll()
    }

    fun getBookmark(id: Int): LiveData<Bookmark> {
        return bookmarkDao.getBookmark(id)
    }

    fun insertBookmark(bookmark: Bookmark) {
        bookmarkDao.insert(bookmark)
    }

    suspend fun updateBookmark(bookmark: Bookmark) {
        bookmarkDao.update(bookmark)
    }

    suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarkDao.deleteBookmark(bookmark.imageUrl)
    }
}