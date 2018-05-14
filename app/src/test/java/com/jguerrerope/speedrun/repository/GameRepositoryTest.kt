package com.jguerrerope.speedrun.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.jguerrerope.speedrun.api.GameTOMapper
import com.jguerrerope.speedrun.api.SpeedRunService
import com.jguerrerope.speedrun.api.model.PageResponse
import com.jguerrerope.speedrun.api.model.to.GameTO
import com.jguerrerope.speedrun.api.model.to.PaginationTO
import com.jguerrerope.speedrun.utils.TestUtils
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.stub
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test

class GameRepositoryTest {
    @Suppress("unused")
    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    private val gameTOMapper = GameTOMapper()

    /**
     * asserts that empty list works fine
     */
    @Test
    fun emptyList() {
        val repository = GameRepositoryImpl(
                mock {
                    on { getGamesPageList(0, DEFAULT_ITEM_PER_PAGE) } doReturn
                            Single.just(EMPTY_GAME_PAGE_RESPONSE)
                },
                gameTOMapper
        )

        val listing = repository.getGameListing(
                DEFAULT_ITEM_PER_PAGE, 3, Schedulers.trampoline())
        val pagedList = TestUtils.getPagedList(listing)
        MatcherAssert.assertThat(pagedList.size, CoreMatchers.`is`(0))
    }

    /**
     * asserts loading a full list in multiple pages
     */
    @Test
    fun verifyCompleteList() {
        val service = mock<SpeedRunService> {
            on { getGamesPageList(0, DEFAULT_ITEM_PER_PAGE) } doReturn Single.just(GAME_PAGE_RESPONSE_1)
        }
        val repository = GameRepositoryImpl(service, gameTOMapper)

        // get listing
        val listing = repository.getGameListing(
                DEFAULT_ITEM_PER_PAGE, 3, Schedulers.trampoline())

        MatcherAssert.assertThat(TestUtils.getPagedList(listing),
                CoreMatchers.`is`(gameTOMapper.toEntity(GAME_PAGE_RESPONSE_1.data)))

        // load new page
        service.stub {
            on { getGamesPageList(5, DEFAULT_ITEM_PER_PAGE) } doReturn Single.just(GAME_PAGE_RESPONSE_2)
        }
        TestUtils.getPagedList(listing).loadAround(DEFAULT_ITEM_PER_PAGE - 1)

        val pages = gameTOMapper.toEntity(GAME_PAGE_RESPONSE_1.data) +
                gameTOMapper.toEntity(GAME_PAGE_RESPONSE_2.data)
        MatcherAssert.assertThat(TestUtils.getPagedList(listing), CoreMatchers.`is`(pages))
    }

    companion object {
        private const val DEFAULT_ITEM_PER_PAGE = 5

        private val EMPTY_GAME_PAGE_RESPONSE = PageResponse<GameTO>(
                data = arrayListOf(),
                pagination = PaginationTO(
                        offset = 5,
                        max = 5,
                        size = 5
                )
        )

        private val GAME_PAGE_RESPONSE_1 = PageResponse(
                data = TestUtils.createGameTOList(DEFAULT_ITEM_PER_PAGE),
                pagination = PaginationTO(
                        offset = 5,
                        max = 5,
                        size = 5
                )
        )

        private val GAME_PAGE_RESPONSE_2 = PageResponse(
                data = TestUtils.createGameTOList(DEFAULT_ITEM_PER_PAGE, indexInit = 5),
                pagination = PaginationTO(
                        offset = 5,
                        max = 5,
                        size = 5
                )
        )
    }
}