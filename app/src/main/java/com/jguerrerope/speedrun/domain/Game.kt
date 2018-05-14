package com.jguerrerope.speedrun.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * Class that represents a Game in the app.
 */

@Parcelize
data class Game(
        val id: String,
        val title: String,
        val imageLarge: String,
        val imageSmall: String,
        val imageLogo: String
) : Parcelable