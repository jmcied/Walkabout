package org.wit.walkabout.main

import android.app.Application
import org.wit.walkabout.models.WalkaboutMemStore
import timber.log.Timber
import timber.log.Timber.i
class MainApp : Application() {

    // val walks = ArrayList<WalkaboutModel>()
    val walks = WalkaboutMemStore()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Walkabout started")
    }
}