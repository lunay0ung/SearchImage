package com.luna.searchimage.model

import com.google.gson.annotations.SerializedName

class Meta {
/*
total_count	Integer	검색된 문서 수
pageable_count	Integer	total_count 중 노출 가능 문서 수
is_end	Boolean	현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
* */
    @field:SerializedName("total_count")
    var totalCount: Int = 0

    @field:SerializedName("pageable_count")
    var pageableCount: Int = 0

    @field:SerializedName("is_end")
    var isEnd: Boolean = false

}