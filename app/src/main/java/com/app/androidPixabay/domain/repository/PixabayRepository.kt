package com.app.androidPixabay.domain.repository

import com.app.androidPixabay.BuildConfig
import com.app.androidPixabay.commons.utils.Constants.Companion.PER_PAGE
import com.app.androidPixabay.datasource.api.PixabayApi

class PixabayRepository(private val pixabayApi: PixabayApi) {

    suspend fun fetchPixabayData(key: String, page: Int) =
        pixabayApi.getPixabayDataAsync(BuildConfig.PIXABAY_KEY, key, page, PER_PAGE).await()
}
