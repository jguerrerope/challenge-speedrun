package com.jguerrerope.speedrun.api.model.to

import com.google.gson.annotations.SerializedName

/**
 * Class to represent the SpeedRun's Game Name
 */
data class GameNameTO(
        @SerializedName("international")
        val international: String,

        @SerializedName("japanese")
        val japanese: String,

        @SerializedName("twitch")
        val twitch: String
)