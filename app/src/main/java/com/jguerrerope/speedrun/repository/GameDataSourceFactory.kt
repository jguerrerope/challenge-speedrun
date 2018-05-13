package com.jguerrerope.speedrun.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.jguerrerope.speedrun.api.GameTOMapper
import com.jguerrerope.speedrun.api.SpeedRunService
import com.jguerrerope.speedrun.domain.Game
import io.reactivex.Scheduler

class GameDataSourceFactory(
        private val service: SpeedRunService,
        private val responseMapper: GameTOMapper,
        private val itemsPerPage: Int,
        private val backgroundScheduler: Scheduler
) : DataSource.Factory<Int, Game>() {
    val sourceLiveData = MutableLiveData<GamePositionalDataSource>()

    override fun create(): DataSource<Int, Game> {
        val source = GamePositionalDataSource(service, responseMapper,
                itemsPerPage, backgroundScheduler)
        sourceLiveData.postValue(source)
        return source
    }
}
