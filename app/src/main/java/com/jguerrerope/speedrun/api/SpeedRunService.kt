package com.jguerrerope.speedrun.api

import com.jguerrerope.speedrun.api.model.GamePageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface allows to define the calls that will be used with Retrofit
 */
interface SpeedRunService {

    @GET("v1/games")
    fun getGamesPageList(@Query("offset") offset: Int,
                         @Query("max") max: Int): Single<GamePageResponse>

}