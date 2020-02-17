package com.app.android_pixabay.commons.communicator

import com.app.android_pixabay.domain.entities.HitsList

interface HitsItem {
    fun onHitsItemClickListener(hitsList: HitsList?)
}