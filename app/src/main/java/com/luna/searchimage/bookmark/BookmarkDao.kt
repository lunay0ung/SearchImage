package com.luna.searchimage.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.*
import com.luna.searchimage.bookmark.Bookmark
import com.luna.searchimage.data.Image


@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark_list")
    fun getAll(): LiveData<List<Bookmark>>
    //fun getAll(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM bookmark_list WHERE keyword = :keyword")
    fun getBookmarkByKeyword(keyword: String): List<Bookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: Bookmark)

    @Update
    fun update(bookmark: Bookmark)

    @Query("DELETE FROM bookmark_list WHERE imageUrl = :imageUrl")
    fun deleteBookmark(imageUrl: String)
}