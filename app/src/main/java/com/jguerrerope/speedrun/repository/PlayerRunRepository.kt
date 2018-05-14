package com.jguerrerope.speedrun.repository

import com.jguerrerope.speedrun.domain.PlayerRun
import io.reactivex.Single

/**
 * Repository to handle which data source must be used to provide all PlayerRun related information
 */
interface PlayerRunRepository {
    /**
     * Get a [PlayerRun]
     *
     * @param gameId Game id
     * @return [PlayerRun]
     */
    fun getFirstPlayerRunByGameId(gameId: String): Single<PlayerRun>
}