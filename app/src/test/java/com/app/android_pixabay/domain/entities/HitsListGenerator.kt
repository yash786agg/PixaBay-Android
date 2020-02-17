package com.app.android_pixabay.domain.entities

object HitsListGenerator {

    fun getSuccessHitsListData() : PixabayApiResponse {
        return PixabayApiResponse(listOf(HitsList(3063284,
            "https://pixabay.com/get/55e0d340485aa814f6da8c7dda79367b143cd6e75b506c4870277bdc954dc45db8_1280.jpg",
            1081,244,"rose, flower, petal","annca",933,
            "https://cdn.pixabay.com/photo/2018/01/05/16/24/rose-3063284_150.jpg")))
    }

    fun getEmptyHitsListData() : PixabayApiResponse {
        return PixabayApiResponse(listOf())
    }
}