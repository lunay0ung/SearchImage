package com.luna.searchimage.bookmark

import androidx.lifecycle.LiveData


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

    suspend fun updateBookmark(bookmark: Bookmark) {
        bookmarkDao.update(bookmark)
    }

    suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarkDao.deleteBookmark(bookmark.imageUrl)
    }
}