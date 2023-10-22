package org.wit.walkabout.models

interface WalkaboutStore {
    fun findAll(): List<WalkaboutModel>
    fun create(walk: WalkaboutModel)
    fun update(walk: WalkaboutModel)
}