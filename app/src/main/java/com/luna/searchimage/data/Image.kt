package com.luna.searchimage.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/*
class Image {
    @field:SerializedName("collection")
    var collection: String? = null
    @field:SerializedName("thumbnail_url")
    var thumbnailUrl: String? = null
    @field:SerializedName("image_url")
    var imgUrl: String? = null
    @field:SerializedName("width")
    var width: Int? = null
    @field:SerializedName("height")
    var height: Int? = null
    @field:SerializedName("display_sitename")
    var siteName: String? = null
    @field:SerializedName("doc_url")
    var docUrl: String? = null
    @field:SerializedName("datetime")
    var datetime: String? = null
    var isBookmarked = false
}

 */
data class Image (
    //@PrimaryKey(autoGenerate = true) var id: Int,
    @field:SerializedName("collection")
    var collection: String? = null,
    @field:SerializedName("thumbnail_url")
    var thumbnailUrl: String? = null,
    @field:SerializedName("image_url")
    var imgUrl: String? = null,
    @field:SerializedName("width")
    var width: Int? = null,
    @field:SerializedName("height")
    var height: Int? = null,
    @field:SerializedName("display_sitename")
    var siteName: String? = null,
    @field:SerializedName("doc_url")
    var docUrl: String? = null,
    @field:SerializedName("datetime")
    var datetime: String? = null,
    var isBookmarked : Boolean = false
)

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