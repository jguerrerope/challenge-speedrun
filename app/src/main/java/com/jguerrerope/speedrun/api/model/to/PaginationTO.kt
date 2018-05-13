package com.jguerrerope.speedrun.api.model.to

import com.google.gson.annotations.SerializedName


/**
 * Class to represent the SpeedRun's Pagination
 */
data class PaginationTO(
        @SerializedName("offset")
        val offset: Int,

        @SerializedName("max")
        val max: Int,

        @SerializedName("size")
        val size: Int
)