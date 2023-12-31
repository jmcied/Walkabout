package org.wit.walkabout.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}
class WalkaboutMemStore : WalkaboutStore {

    val walks = ArrayList<WalkaboutModel>()

    override fun findAll(): List<WalkaboutModel> {
        return walks
    }

    override fun create(walk: WalkaboutModel) {
        walk.id = getId()
        walks.add(walk)
        logAll()
    }

    override fun update(walk: WalkaboutModel) {
        var foundWalk: WalkaboutModel? = walks.find { w -> w.id == walk.id }
        if (foundWalk != null) {
            foundWalk.title = walk.title
            foundWalk.description = walk.description
            foundWalk.difficulty = walk.difficulty
            foundWalk.terrain = walk.terrain
            foundWalk.image = walk.image
            foundWalk.lat = walk.lat
            foundWalk.lng = walk.lng
            foundWalk.zoom = walk.zoom
            logAll()
        }
    }

    override fun delete(walk: WalkaboutModel) {
        walks.remove(walk)
    }
    fun logAll() {
        walks.forEach{ i("${it}") }
    }

    override fun findById(id: Long): WalkaboutModel? {
        return walks.find { it.id == id }
    }
}