package com.app.android_pixabay.datasource.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.app.android_pixabay.domain.entities.HitsList
import com.app.android_pixabay.domain.repository.PixabayRepository
import kotlinx.coroutines.CoroutineScope

class PixabayDataSourceFactory(private val pixabayRepository: PixabayRepository,
                               private val scope : CoroutineScope,
                               private var query : String = "") : DataSource.Factory<Int, HitsList>()
{
    val dataSource = MutableLiveData<PixabayDataSource>()

    override fun create(): DataSource<Int, HitsList> {

        val dataSource = PixabayDataSource(pixabayRepository,scope,query)
        this.dataSource.postValue(dataSource)
        return dataSource
    }

    // --- PUBLIC API
    fun getQuery() = query

    private fun getSource() = dataSource.value

    fun updateQuery(query : String) {
        this.query = query
        getSource()?.refresh()
    }
}