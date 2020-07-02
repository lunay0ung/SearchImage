package com.luna.searchimage.service.source.local

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark_list")
    fun getAll(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM bookmark_list WHERE keyword = :keyword")
    fun getBookmarkByKeyword(keyword: String): List<Bookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: Bookmark)

    @Query("DELETE FROM bookmark_list WHERE imageUrl = :imageUrl")
    fun deleteBookmark(imageUrl: String)
}