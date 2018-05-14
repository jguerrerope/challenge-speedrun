package com.jguerrerope.speedrun.ui.gamedetails

import android.arch.lifecycle.MutableLiveData
import com.jguerrerope.speedrun.domain.PlayerRun
import com.jguerrerope.speedrun.domain.Resource
import com.jguerrerope.speedrun.extension.logd
import com.jguerrerope.speedrun.extension.loge
import com.jguerrerope.speedrun.repository.PlayerRunRepositoryImpl
import com.jguerrerope.speedrun.ui.viewmodel.RxViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GameDetailsViewModel @Inject constructor(
        private val repository: PlayerRunRepositoryImpl
) : RxViewModel() {

    val playerRun = MutableLiveData<Resource<PlayerRun>>()

    fun loadPlayerRun(gameId: String) {
        playerRun.value = Resource.loading()
        disposables += repository.getFirstPlayerRunByGameId(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            logd("loadPlayerRun.onSuccess")
                            playerRun .value = Resource.success(it)
                        },
                        onError = {
                            loge("loadPlayerRun.onError", it)
                            playerRun .value = Resource.error(it)
                        }
                )
    }
}