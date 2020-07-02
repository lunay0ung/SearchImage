package com.luna.searchimage.service.source

import androidx.lifecycle.LiveData
import com.luna.searchimage.service.source.local.Bookmark
import com.luna.searchimage.service.source.local.BookmarkDao


open class BookmarkRepository(private val bookmarkDao: BookmarkDao) {

    fun getAllBookmark(): LiveData<List<Bookmark>> {
        return bookmarkDao.getAll()
    }

    fun getBookmarkByKeyword(keyword: String): List<Bookmark> {
        return bookmarkDao.getBookmarkByKeyword(keyword)
    }

    fun insertBookmark(bookmark: Bookmark) {
        bookmarkDao.insert(bookmark)
    }

    suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarkDao.deleteBookmark(bookmark.imageUrl)
    }
}