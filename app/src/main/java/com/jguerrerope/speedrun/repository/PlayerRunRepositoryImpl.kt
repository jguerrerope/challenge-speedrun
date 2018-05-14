package com.jguerrerope.speedrun.repository

import com.jguerrerope.speedrun.api.SpeedRunService
import com.jguerrerope.speedrun.domain.PlayerRun
import io.reactivex.Single
import javax.inject.Inject

/**
 * Repository to handle which data source must be used to provide all PlayerRun related information
 */

class PlayerRunRepositoryImpl @Inject constructor(
        private val service: SpeedRunService
) : PlayerRunRepository {

    override fun getFirstPlayerRunByGameId(gameId: String): Single<PlayerRun> {
        return service.getRunPageList(gameId = gameId, offset = 0, max = 1)
                .flatMap { runResponse ->
                    when {
                        runResponse.data.isEmpty() ->
                            Single.error<PlayerRun>(RuntimeException("Not run available"))

                        runResponse.data.first().players.isEmpty() ||
                                runResponse.data.first().players.first().id.isNullOrBlank() ->
                            Single.error<PlayerRun>(RuntimeException("Not players available"))

                        else ->
                            service.getUserById(runResponse.data.first().players.first().id!!)
                                    .map { userResponse ->

                                        val userTO = userResponse.data
                                        val runTO = runResponse.data.first()
                                        val time = runTO.times.realTime ?: runTO.times.primary ?: ""
                                        PlayerRun(
                                                id = "${userTO.id}_${runTO.id}",
                                                name = userTO.names.international,
                                                video = runTO.videos.links?.first()?.uri ?: "",
                                                time = time
                                        )
                                    }
                    }
                }
    }
}