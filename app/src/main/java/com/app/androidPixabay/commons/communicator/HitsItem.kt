package com.app.androidPixabay.commons.communicator

import com.app.androidPixabay.domain.entities.HitsList

interface HitsItem {
    fun onHitsItemClickListener(hitsList: HitsList?)
}
