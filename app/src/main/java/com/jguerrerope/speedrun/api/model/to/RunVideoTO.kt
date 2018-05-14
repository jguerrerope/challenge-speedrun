package com.jguerrerope.speedrun.api.model.to

import com.google.gson.annotations.SerializedName


data class RunVideoTO(
        @SerializedName("links")
        val links: List<LinkTO>?
)


