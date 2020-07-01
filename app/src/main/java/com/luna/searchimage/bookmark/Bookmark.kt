package com.luna.searchimage.bookmark

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luna.searchimage.bookmark.Bookmark.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize


//이미지 객체 전체
@Parcelize
@Entity(tableName = TABLE_NAME)
data class Bookmark(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String,
    @ColumnInfo(name = "imageUrl") val imageUrl:String,
    @ColumnInfo(name = "siteName") val siteName: String,
    @ColumnInfo(name = "isBookmarked") var isBookmarked: Boolean) : Parcelable {


    companion object {
        const val TABLE_NAME = "bookmark_list"
    }

}

