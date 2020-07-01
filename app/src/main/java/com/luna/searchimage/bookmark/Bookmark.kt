package com.luna.searchimage.bookmark

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.luna.searchimage.bookmark.Bookmark.Companion.IMAGE_URL
import com.luna.searchimage.bookmark.Bookmark.Companion.TABLE_NAME
import com.luna.searchimage.bookmark.Bookmark.Companion.THUMB_URL
import kotlinx.android.parcel.Parcelize


//이미지 객체 전체
@Parcelize
@Entity(tableName = TABLE_NAME,
    indices = [Index(value = [IMAGE_URL, THUMB_URL], unique = true)])
data class Bookmark(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = THUMB_URL) val thumbnailUrl: String,
    @ColumnInfo(name = IMAGE_URL) val imageUrl:String,
    @ColumnInfo(name = "siteName") val siteName: String,
    @ColumnInfo(name = "isBookmarked") var isBookmarked: Boolean) : Parcelable {


    companion object {
        const val TABLE_NAME = "bookmark_list"
        const val THUMB_URL = "thumbnailUrl"
        const val IMAGE_URL = "imageUrl"
    }

}

