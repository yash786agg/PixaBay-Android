package com.app.android_pixabay.datasource.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.app.android_pixabay.datasource.api.NetworkState
import com.app.android_pixabay.domain.entities.HitsList
import com.app.android_pixabay.domain.repository.PixabayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PixabayDataSource(private val pixabayRepository: PixabayRepository, private val scope: CoroutineScope,
                        private val query: String) : PageKeyedDataSource<Int, HitsList>() {

    // FOR DATA ---
    private val networkState = MutableLiveData<NetworkState<Int>>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, HitsList>) {
        executeQuery(1) { callback.onResult(it, null, 2) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, HitsList>) {
        executeQuery(params.key) { callback.onResult(it, params.key + 1) }
    }

    // UTILS ---
    private fun executeQuery(page : Int, callback:(List<HitsList>) -> Unit) {
        networkState.postValue(NetworkState.Loading())
        scope.launch {
            try {
                val response = pixabayRepository.fetchPixabayData(query,page)
                response.let {
                    if(response.isSuccessful) {
                        response.body()?.hits?.let { data -> callback(data) }
                        networkState.postValue(NetworkState.Success())
                    }
                    else networkState.postValue(NetworkState.Error())
                }
            }
            catch (exception : HttpException) {
                networkState.postValue(NetworkState.Error())
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, HitsList>) {}

    // PUBLIC API ---
    fun refresh() = this.invalidate()
    fun getNetworkState() : LiveData<NetworkState<Int>> = networkState
}