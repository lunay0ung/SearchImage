package com.luna.searchimage.service.source.local

import android.content.Context
import android.content.res.Resources
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [(Bookmark::class)], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    private class BookmarkDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val bookmarkDao = database.bookmarkDao()
                }
            }
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: BookmarkDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope, resources: Resources): BookmarkDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    BookmarkDatabase::class.java,
                    "bookmark_database")
                    .addCallback(
                        BookmarkDatabaseCallback(
                            coroutineScope
                        )
                    )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}