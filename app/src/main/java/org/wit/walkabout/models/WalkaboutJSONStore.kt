package org.wit.walkabout.models

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.reflect.TypeToken
import org.wit.walkabout.helpers.exists
import org.wit.walkabout.helpers.read
import org.wit.walkabout.helpers.write
import timber.log.Timber
import java.lang.reflect.Type
import java.util.Random

const val JSON_FILE = "walks.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<WalkaboutModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class WalkaboutJSONStore(private val context: Context) : WalkaboutStore {

    var walks = mutableListOf<WalkaboutModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<WalkaboutModel> {
        logAll()
        return walks
    }

    override fun create(walk: WalkaboutModel) {
        walk.id = generateRandomId()
        walks.add(walk)
        serialize()
    }


    override fun update(walk: WalkaboutModel) {
        val walksList = findAll() as ArrayList<WalkaboutModel>
        var foundWalk: WalkaboutModel? = walksList.find { w -> w.id == walk.id }
        if (foundWalk != null) {
            foundWalk.title = walk.title
            foundWalk.description = walk.description
            foundWalk.difficulty = walk.difficulty
            foundWalk.terrain = walk.terrain
            foundWalk.image = walk.image
            foundWalk.lat = walk.lat
            foundWalk.lng = walk.lng
            foundWalk.zoom = walk.zoom
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(walks, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        walks = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun delete(walk: WalkaboutModel) {
        walks.remove(walk)
        serialize()
    }
    private fun logAll() {
        walks.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}