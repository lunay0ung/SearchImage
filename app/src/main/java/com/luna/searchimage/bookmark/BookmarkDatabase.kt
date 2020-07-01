package com.luna.searchimage.bookmark

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
                    prePopulateDatabase(bookmarkDao)
                }
            }
        }

        private suspend fun prePopulateDatabase(bookmarkDao: BookmarkDao) {
           /*
            val jsonString = resources.openRawResource(R.raw.players).bufferedReader().use {
                it.readText()
            }
            val typeToken = object : TypeToken<List<Player>>() {}.type
            val tennisPlayers = Gson().fromJson<List<Player>>(jsonString, typeToken)
            bookmarkDao.insertAllPlayers(tennisPlayers)
            */

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