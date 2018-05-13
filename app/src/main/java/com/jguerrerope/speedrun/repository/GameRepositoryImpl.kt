package com.jguerrerope.speedrun.repository

import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.jguerrerope.speedrun.api.GameTOMapper
import com.jguerrerope.speedrun.api.SpeedRunService
import com.jguerrerope.speedrun.domain.Game
import com.jguerrerope.speedrun.domain.Listing
import com.jguerrerope.speedrun.extension.switchMap
import io.reactivex.Scheduler
import javax.inject.Inject


class GameRepositoryImpl @Inject constructor(
        private val service: SpeedRunService,
        private val responseMapper: GameTOMapper

) : GameRepository {

    override fun getGameListing(itemsPerPage: Int, prefetchDistance: Int,
                                backgroundScheduler: Scheduler): Listing<Game> {

        val sourceFactory = GameDataSourceFactory(service,
                responseMapper, itemsPerPage,
                backgroundScheduler)

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(prefetchDistance)
                .setPageSize(itemsPerPage)
                .build()

        val pagedList = LivePagedListBuilder(sourceFactory, pagedListConfig)
                .build()

        val networkState =
                sourceFactory.sourceLiveData.switchMap { it.networkState }

        val refreshState =
                sourceFactory.sourceLiveData.switchMap { it.initialLoad }

        return Listing(
                pagedList = pagedList,
                networkState = networkState,
                retry = { sourceFactory.sourceLiveData.value?.retry() },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }
}
