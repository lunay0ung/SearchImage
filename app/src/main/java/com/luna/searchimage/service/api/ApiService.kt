package com.luna.searchbooks.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/*
>>>> https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide

GET /v2/search/image
Host: dapi.kakao.com
Authorization: KakaoAK {app_key}

query	String	검색을 원하는 질의어	O
sort	String	결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy	X
page	Integer	결과 페이지 번호, 1~50 사이의 값, 기본 값 1	X
size	Integer	한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10	X
* */
interface ApiService {
    @Headers("Authorization: KakaoAK 687c76ae50331595a4a7a8113be4d707")
    @GET("/v2/search/image")
    fun searchImage(
        @Query("page") page: Int,
        @Query("query") keyword: String
        ) : Call<ImageSearchResponse>
}