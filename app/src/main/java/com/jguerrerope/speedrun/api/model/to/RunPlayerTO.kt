package com.jguerrerope.speedrun.api.model.to

import com.google.gson.annotations.SerializedName

data class RunPlayerTO(
        @SerializedName("rel")
        val rel: String,

        @SerializedName("id")
        val id: String?,

        @SerializedName("uri")
        val uri: String
)

