package com.jguerrerope.speedrun.api.model.to

import com.google.gson.annotations.SerializedName


/**
 * Class to represent the SpeedRun's Run
 */
data class RunTO(
        @SerializedName("id")
        val id: String,

        @SerializedName("videos")
        val videos: RunVideoTO,

        @SerializedName("times")
        val times: RunTimesTO,

        @SerializedName("comment")
        val comment: String,

        @SerializedName("players")
        val players: List<RunPlayerTO>
)


