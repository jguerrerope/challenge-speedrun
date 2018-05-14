package com.jguerrerope.speedrun.api.model.to

import com.google.gson.annotations.SerializedName


/**
 * Class to represent the SpeedRu's Game Asset
 */
data class GameAssetTO(
        @SerializedName("uri")
        val uri: String,

        @SerializedName("width")
        val width: Int,

        @SerializedName("height")
        val height: Int
)