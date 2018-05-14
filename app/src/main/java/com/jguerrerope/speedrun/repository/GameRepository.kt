package com.jguerrerope.speedrun.repository

import com.jguerrerope.speedrun.domain.Game
import com.jguerrerope.speedrun.domain.Listing
import io.reactivex.Scheduler

/**
 * Repository to handle which data source must be used to provide all game related information
 */
interface GameRepository {
    /**
     * Gets a [Listing] of Game
     *
     * @param itemsPerPage The number of items that we want to retrieve
     * @param prefetchDistance Defines how many items to load when first load occurs.
     * @param backgroundScheduler The scheduler of background processing
     * @return [Listing]  a Listing for the given Game popular.
     */
    fun getGameListing(itemsPerPage: Int, prefetchDistance: Int, backgroundScheduler: Scheduler): Listing<Game>
}