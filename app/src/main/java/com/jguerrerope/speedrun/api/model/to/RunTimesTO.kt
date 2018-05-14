package com.jguerrerope.speedrun.api.model.to

import com.google.gson.annotations.SerializedName


data class RunTimesTO(
        @SerializedName("primary")
        val primary: String?,

        @SerializedName("realtime")
        val realTime: String?
)
