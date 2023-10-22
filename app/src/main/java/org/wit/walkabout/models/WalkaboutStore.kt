package org.wit.walkabout.models

interface WalkaboutStore {
    fun findAll(): List<WalkaboutModel>
    fun create(placemark: WalkaboutModel)
}