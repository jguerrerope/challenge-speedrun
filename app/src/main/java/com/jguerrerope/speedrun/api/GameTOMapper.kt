package com.jguerrerope.speedrun.api

import com.jguerrerope.speedrun.api.model.to.GameTO
import com.jguerrerope.speedrun.domain.Game
import javax.inject.Inject

class GameTOMapper @Inject constructor() {

    fun toEntity(value: GameTO): Game {
        val imageLarge = arrayOf(GameTO.ASSET_COVER_LARGE, GameTO.ASSET_COVER_MEDIUM)
                .map { value.assets[it]?.uri }
                .first() ?: ""

        val imageSmall = arrayOf(GameTO.ASSET_COVER_SMALL, GameTO.ASSET_LOGO)
                .map { value.assets[it]?.uri }
                .first() ?: ""
        return Game(
                id = value.id,
                title = value.names.international,
                imageLarge = imageLarge,
                imageSmall = imageSmall
        )
    }


    fun toEntity(values: List<GameTO>): List<Game> = values.map { toEntity(it) }
}
