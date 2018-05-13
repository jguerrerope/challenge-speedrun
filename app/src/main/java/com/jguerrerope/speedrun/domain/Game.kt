package com.jguerrerope.speedrun.domain


/**
 * Class that represents a Game in the app.
 */
data class Game(
        val id: String,
        val title: String,
        val imageLarge: String,
        val imageSmall: String
)