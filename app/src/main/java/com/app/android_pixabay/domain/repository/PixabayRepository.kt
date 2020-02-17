package com.app.android_pixabay.domain.repository

import com.app.android_pixabay.BuildConfig
import com.app.android_pixabay.commons.utils.Constants.Companion.PER_PAGE
import com.app.android_pixabay.datasource.api.PixabayApi

class PixabayRepository(private val pixabayApi: PixabayApi) {

    suspend fun fetchPixabayData(key : String,page : Int)
            = pixabayApi.getPixabayDataAsync(BuildConfig.PIXABAY_KEY,key,page,PER_PAGE).await()
}