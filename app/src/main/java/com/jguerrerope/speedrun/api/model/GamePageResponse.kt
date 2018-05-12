package com.jguerrerope.speedrun.api.model

import com.google.gson.annotations.SerializedName
import com.jguerrerope.speedrun.api.model.to.GameTO
import com.jguerrerope.speedrun.api.model.to.PaginationTO

data class GamePageResponse(
        @SerializedName("data")
        val data: List<GameTO>,

        @SerializedName("pagination")
        val pagination: PaginationTO
)

