package com.jguerrerope.speedrun.ui.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jorge.guerrero on 11/2017.
 */
abstract class RxViewModel : ViewModel() {
    var disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }
}
