package com.app.android_pixabay.domain.entities

import android.os.Parcelable
import com.app.android_pixabay.commons.utils.Constants.Companion.HITS_TAG
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PixabayApiResponse(@field:Json(name = HITS_TAG) val hits: List<HitsList>?) : Parcelable