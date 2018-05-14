package com.jguerrerope.speedrun.api.model

import com.google.gson.annotations.SerializedName
import com.jguerrerope.speedrun.api.model.to.PaginationTO

data class PageResponse<T> constructor(
        @SerializedName("pagination")
        val pagination: PaginationTO,

        @SerializedName("data")
        val data: List<T>
)


