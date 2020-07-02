package com.luna.searchimage

import android.app.Application
import androidx.room.Room
import com.luna.searchimage.service.source.local.BookmarkDatabase

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        instance = this
        App.database = Room.databaseBuilder(this, BookmarkDatabase::class.java,
            "bookmark-db").build()
    }


    companion object {
        lateinit var instance : App
        var database: BookmarkDatabase? = null
    }
}