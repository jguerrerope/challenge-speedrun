package com.jguerrerope.speedrun.utils

import android.arch.paging.PagedList
import com.jguerrerope.speedrun.api.model.to.GameTO
import com.jguerrerope.speedrun.api.model.to.NameTO
import com.jguerrerope.speedrun.domain.Game
import com.jguerrerope.speedrun.domain.Listing
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

object TestUtils {


    /**
     * created GameTO list
     */
    fun createGameTOList(size: Int,indexInit: Int = 1): List<GameTO> {
        return (0 until size).map {
            val index = it + indexInit
            GameTO(
                    id = "id $index",
                    names = NameTO(
                            international = "international $index",
                            japanese = "japanese $index",
                            twitch = "twitch $index"
                    ),
                    abbreviation = "abbreviation $index",
                    webLink = "webLink = $index",
                    releaseDate = "releaseDate $index",
                    assets = hashMapOf()
            )
        }
    }

    /**
     * extract the latest paged list from the listing
     */
    fun getPagedList(listing: Listing<Game>): PagedList<Game> {
        val observer = LoggingObserver<PagedList<Game>>()
        listing.pagedList.observeForever(observer)
        MatcherAssert.assertThat(observer.value, CoreMatchers.`is`(CoreMatchers.notNullValue()))
        return observer.value!!
    }

}