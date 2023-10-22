package org.wit.walkabout.models

import timber.log.Timber.i

class WalkaboutMemStore : WalkaboutStore {

    val walks = ArrayList<WalkaboutModel>()

    override fun findAll(): List<WalkaboutModel> {
        return walks
    }

    override fun create(placemark: WalkaboutModel) {
        walks.add(placemark)
    }
    fun logAll() {
        walks.forEach{ i("${it}") }
    }
}