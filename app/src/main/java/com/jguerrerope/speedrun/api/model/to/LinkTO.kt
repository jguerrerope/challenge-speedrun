package com.jguerrerope.speedrun.api.model.to

import com.google.gson.annotations.SerializedName

data class LinkTO(
        @SerializedName("uri")
        val uri: String
)