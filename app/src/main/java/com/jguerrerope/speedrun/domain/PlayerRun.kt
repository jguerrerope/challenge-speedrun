package com.jguerrerope.speedrun.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerRun(
        val id: String,
        val name: String,
        val video: String,
        val time: String
) : Parcelable