package com.app.android_pixabay.ui.pixabay.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.android_pixabay.commons.base.BaseViewModel
import com.app.android_pixabay.commons.utils.Constants.Companion.PER_PAGE
import com.app.android_pixabay.datasource.api.NetworkState
import com.app.android_pixabay.datasource.pagination.PixabayDataSourceFactory
import com.app.android_pixabay.domain.entities.HitsList
import com.app.android_pixabay.domain.repository.PixabayRepository

class PixabayListVM(pixabayRepository: PixabayRepository) : BaseViewModel() {

    // FOR DATA ---
    private val pixabayDataSource = PixabayDataSourceFactory(pixabayRepository = pixabayRepository,scope = ioScope)

    // OBSERVABLES ---
    val pixabayData: LiveData<PagedList<HitsList>> = LivePagedListBuilder(pixabayDataSource.sortById(), pagedListConfig()).build()
    
    val networkState : LiveData<NetworkState<Int>>? =
        Transformations.switchMap(pixabayDataSource.dataSource) { it.getNetworkState() }

    // PUBLIC API ---

    /**
     * Fetch a list of [HitsList] by Query
     */
    fun fetchKeyQuery(query : String) {
        if (pixabayDataSource.getQuery() == query.trim()) return
        pixabayDataSource.updateQuery(query.trim())
    }

    // UTILS ---
    private fun pagedListConfig() = PagedList.Config.Builder().setPageSize(PER_PAGE).build()

    //https://stackoverflow.com/questions/51320874/sort-array-items-when-using-pagedlistadapter
    /** sorts HitsList by ID in descending order */
    private fun DataSource.Factory<Int, HitsList>.sortById(): DataSource.Factory<Int, HitsList> {
        return mapByPage {
            it.sortedWith(compareByDescending { item -> item.id })
        }
    }
}