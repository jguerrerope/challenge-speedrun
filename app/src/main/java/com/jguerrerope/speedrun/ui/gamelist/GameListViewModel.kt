package com.jguerrerope.speedrun.ui.gamelist

import android.arch.lifecycle.ViewModel
import com.jguerrerope.speedrun.SpeedRunConfiguration
import com.jguerrerope.speedrun.extension.switchMap
import com.jguerrerope.speedrun.repository.GameRepositoryImpl
import com.jguerrerope.speedrun.ui.viewmodel.AbsentLiveData
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GameListViewModel @Inject constructor(
         repository: GameRepositoryImpl
) : ViewModel() {
    private val result = AbsentLiveData.create(
            repository.getGameListing(
                    SpeedRunConfiguration.NUMBER_OF_ITEMS_PER_PAGE,
                    SpeedRunConfiguration.PREFETCH_DISTANCE,
                    Schedulers.io()
            )
    )

    val games = result.switchMap { it.pagedList }
    val networkState = result.switchMap { it.networkState }
    val refreshState = result.switchMap { it.refreshState }

    fun retry() = result.value?.retry?.invoke()

    fun refresh() = result.value?.refresh?.invoke()
}