package com.jguerrerope.speedrun.api.model.to

import com.google.gson.annotations.SerializedName

/**
 * Class to represent the SpeedRun's Game
 */
data class GameTO(
        @SerializedName("id")
        val id: String,

        @SerializedName("names")
        val names: NameTO,

        @SerializedName("abbreviation")
        val abbreviation: String,

        @SerializedName("weblink")
        val webLink: String,

        @SerializedName("release-date")
        val releaseDate: String,

        @SerializedName("assets")
        val assets: HashMap<String, GameAssetTO>
) {
    companion object {
        const val ASSET_LOGO = "logo"
        const val ASSET_COVER_SMALL = "cover-small"
        const val ASSET_COVER_MEDIUM = "cover-medium"
        const val ASSET_COVER_LARGE = "cover-large"
        const val ASSET_ICON = "icon"
        const val ASSET_TROPHY_1ST = "trophy-1st"
        const val ASSET_TROPHY_2ND = "trophy-2st"
        const val ASSET_TROPHY_3ND = "trophy-3st"
        const val ASSET_TROPHY_4ND = "trophy-4st"
        const val ASSET_BACKGROUND = "background"
        const val ASSET_FOREGROUND = "foreground"
    }
}

