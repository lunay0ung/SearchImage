package com.luna.searchimage.bookmark

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookmarkListItem(
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "thumbnailUrl") val firstName: String = "",
    @ColumnInfo(name = "imageUrl") val lastName: String = "",
    @ColumnInfo(name = "siteName") val country: String = "",
    @ColumnInfo(name = "imageUrl") val imageUrl: String = "",
    @ColumnInfo(name = "bookmark") val bookmark: Boolean = false
) : Parcelable


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