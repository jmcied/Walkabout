package org.wit.walkabout.main

import android.app.Application
import org.wit.walkabout.models.WalkaboutJSONStore
import org.wit.walkabout.models.WalkaboutStore
import timber.log.Timber
import timber.log.Timber.i
class MainApp : Application() {

    lateinit var walks: WalkaboutStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        //walks = WalkaboutMemStore()
        walks = WalkaboutJSONStore(applicationContext)
        i("Walkabout started")
    }
}