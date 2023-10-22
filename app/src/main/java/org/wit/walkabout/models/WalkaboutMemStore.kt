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
            logAll()
        }
    }
    fun logAll() {
        walks.forEach{ i("${it}") }
    }
}