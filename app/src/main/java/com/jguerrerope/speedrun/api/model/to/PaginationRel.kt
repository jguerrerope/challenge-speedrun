package com.jguerrerope.speedrun.api.model.to

import android.support.annotation.StringDef
import com.google.gson.annotations.SerializedName

/**
 * Class to represent the SpeedRun's Pagination Rel
 */
data class PaginationRel(
        @SerializedName("rel")
        @TypeRel
        val rel: String,

        @SerializedName("uri")
        val uri: String

) {

    companion object {
        @StringDef(TYPE_REL_PREV, TYPE_REL_NEXT)
        @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
        annotation class TypeRel

        const val TYPE_REL_PREV = "prev"
        const val TYPE_REL_NEXT = "next"
    }
}

