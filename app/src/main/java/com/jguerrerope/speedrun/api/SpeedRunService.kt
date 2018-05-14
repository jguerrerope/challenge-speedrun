package com.jguerrerope.speedrun.api

import com.jguerrerope.speedrun.api.model.DefaultResponse
import com.jguerrerope.speedrun.api.model.PageResponse
import com.jguerrerope.speedrun.api.model.to.GameTO
import com.jguerrerope.speedrun.api.model.to.RunTO
import com.jguerrerope.speedrun.api.model.to.UserTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * This interface allows to define the calls that will be used with Retrofit
 */
interface SpeedRunService {

    @GET("v1/games")
    fun getGamesPageList(@Query("offset") offset: Int,
                         @Query("max") max: Int): Single<PageResponse<GameTO>>

    @GET("v1/runs")
    fun getRunPageList(@Query("game") gameId: String,
                       @Query("offset") offset: Int,
                       @Query("max") max: Int): Single<PageResponse<RunTO>>


    @GET("v1/users/{userId}")
    fun getUserById(@Path("userId") userId: String): Single<DefaultResponse<UserTO>>
}