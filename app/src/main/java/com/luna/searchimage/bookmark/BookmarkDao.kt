package com.luna.searchimage.bookmark

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.luna.searchimage.bookmark.Bookmark
import com.luna.searchimage.data.Image


@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmark_list")
    fun getAll(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM bookmark_list WHERE id = :id")
    fun getBookmark(id: Int): LiveData<Bookmark>

    @Insert
    fun insert(bookmark: Bookmark)

    @Update
    fun update(bookmark: Bookmark)

    @Query("DELETE FROM bookmark_list WHERE imageUrl = :imageUrl")
    fun deleteBookmark(imageUrl: String)
}