package org.wit.walkabout.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalkaboutModel(var title: String = "",
                        var description: String = "",
                        var difficulty: String = "",
                        var terrain: String = "") : Parcelable
