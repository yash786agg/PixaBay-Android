package com.app.androidPixabay.di

import com.app.androidPixabay.BuildConfig
import com.app.androidPixabay.commons.utils.UiHelper
import com.app.androidPixabay.datasource.api.PixabayApi
import com.app.androidPixabay.datasource.api.createNetworkClient
import com.app.androidPixabay.domain.repository.PixabayRepository
import com.app.androidPixabay.ui.pixabay.list.PixabayListVM
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule,
            repositoryModule,
            networkModule,
            uiHelperModule
        )
    )
}

val viewModelModule = module {
    viewModel { PixabayListVM(pixabayRepository = get()) }
}

val repositoryModule = module {
    single { PixabayRepository(pixabayApi = get()) }
}

val networkModule = module {
    single { pixabayApi }
}

val uiHelperModule = module {
    single { UiHelper(androidContext()) }
}

private val retrofit: Retrofit = createNetworkClient(BuildConfig.BASE_URL)

private val pixabayApi: PixabayApi = retrofit.create(PixabayApi::class.java)
