package com.app.android_pixabay

import android.app.Application
import com.app.android_pixabay.di.injectFeature
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class BaseApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // CONFIGURATION ---
    private fun configureDi() =
        startKoin {
            androidContext(this@BaseApplication)
            injectFeature()
        }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}