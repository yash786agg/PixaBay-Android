package com.app.androidPixabay.domain.entities

import android.os.Parcelable
import com.app.androidPixabay.commons.utils.Constants.Companion.HITS_TAG
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PixabayApiResponse(@field:Json(name = HITS_TAG) val hits: List<HitsList>?) : Parcelable
