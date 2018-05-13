package com.jguerrerope.speedrun.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PositionalDataSource
import com.jguerrerope.speedrun.api.GameTOMapper
import com.jguerrerope.speedrun.api.SpeedRunService
import com.jguerrerope.speedrun.domain.Game
import com.jguerrerope.speedrun.domain.NetworkState
import com.jguerrerope.speedrun.extension.logd
import com.jguerrerope.speedrun.extension.loge
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy


class GamePositionalDataSource(
        private val service: SpeedRunService,
        private val responseMapper: GameTOMapper,
        private val itemsPerPage: Int,
        private val backgroundScheduler: Scheduler
) : PositionalDataSource<Game>() {

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()
    var disposables = CompositeDisposable()
    private var retry = {}
    private var reachEnd: Boolean = false


    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Game>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        disposables += service.getGamesPageList(0, itemsPerPage)
                .subscribeOn(backgroundScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            logd("loadInitial.onSuccess")
                            if (it.pagination.size == 0) reachEnd = true
                            networkState.value = NetworkState.LOADED
                            initialLoad.value = NetworkState.LOADED
                            callback.onResult(responseMapper.toEntity(it.data), 0, it.data.size)
                        },
                        onError = {
                            loge("loadInitial.onError", it)
                            val erorr = NetworkState.error(it)
                            networkState.value = erorr
                            initialLoad.value = erorr
                            retry = { loadInitial(params, callback) }
                        }
                )
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Game>) {
        val offset = params.startPosition
        networkState.postValue(NetworkState.LOADING)

        disposables += service.getGamesPageList(offset, itemsPerPage)
                .subscribeOn(backgroundScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            logd("loadRange.onSuccess")
                            if (it.pagination.size == 0) reachEnd = true
                            networkState.value = NetworkState.LOADED
                            callback.onResult(responseMapper.toEntity(it.data))
                        },
                        onError = {
                            loge("loadRange.onError", it)
                            networkState.value = NetworkState.error(it)
                            retry = { loadRange(params, callback) }
                        }
                )
    }

    fun retry() = retry.invoke()
}