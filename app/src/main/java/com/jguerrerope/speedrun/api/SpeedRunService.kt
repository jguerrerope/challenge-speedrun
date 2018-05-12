package com.jguerrerope.speedrun.api

import com.jguerrerope.speedrun.SpeedRunConfiguration
import com.jguerrerope.speedrun.api.model.GamePageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface allows to define the calls that will be used with Retrofit
 */
interface SpeedRunService {

    @GET("/v1/games?max=${SpeedRunConfiguration.NUMBER_OF_ITEMS_PER_PAGE}")
    fun getGamesPageList(@Query("offset") offset: Int): Single<GamePageResponse>

}