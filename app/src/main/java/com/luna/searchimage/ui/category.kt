package com.luna.searchimage.ui

data class Category (
    val id: Int,
    val name: String)

val categoryList = listOf(
    Category(0, "이미지 검색"),
    Category(1, "즐겨찾기")
)
