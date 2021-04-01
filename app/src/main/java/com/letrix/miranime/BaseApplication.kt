package com.letrix.miranime

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.letrix.anime.utils.TimberTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        if (BuildConfig.DEBUG) {
            Timber.plant(TimberTree())
        }
    }
}