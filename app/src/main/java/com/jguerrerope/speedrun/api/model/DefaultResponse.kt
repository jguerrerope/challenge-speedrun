package com.jguerrerope.speedrun.api.model

import com.google.gson.annotations.SerializedName

class DefaultResponse<T>(
        @SerializedName("data")
        var data: T
)



