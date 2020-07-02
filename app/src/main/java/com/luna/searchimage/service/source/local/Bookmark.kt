package com.luna.searchimage.service.source.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.luna.searchimage.service.source.local.Bookmark.Companion.IMAGE_URL
import com.luna.searchimage.service.source.local.Bookmark.Companion.TABLE_NAME
import com.luna.searchimage.service.source.local.Bookmark.Companion.THUMB_URL
import kotlinx.android.parcel.Parcelize


//이미지 객체 전체
@Parcelize
@Entity(tableName = TABLE_NAME,
    indices = [Index(value = [IMAGE_URL, THUMB_URL], unique = true)])
data class Bookmark(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "keyword") val keyword: String,
    @ColumnInfo(name = "collection") val collection: String,
    @ColumnInfo(name = THUMB_URL) val thumbnailUrl: String,
    @ColumnInfo(name = IMAGE_URL) val imageUrl: String,
    @ColumnInfo(name = "width") val width:Int,
    @ColumnInfo(name = "height") val height:Int,
    @ColumnInfo(name = "siteName") val siteName: String,
    @ColumnInfo(name = "doc_url") val docUrl: String,
    @ColumnInfo(name = "datetime") val datetime: String,
    @ColumnInfo(name = "isBookmarked") var isBookmarked: Boolean) : Parcelable {


    companion object {
        const val TABLE_NAME = "bookmark_list"
        const val THUMB_URL = "thumbnailUrl"
        const val IMAGE_URL = "imageUrl"
    }

}

/*
collection	String	컬렉션
thumbnail_url	String	미리보기 이미지 URL
image_url	String	이미지 URL
width	Integer	이미지의 가로 길이
height	Integer	이미지의 세로 길이
display_sitename	String	출처
doc_url	String	문서 URL
datetime	Datetime	문서 작성시간. ISO 8601. [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
 */