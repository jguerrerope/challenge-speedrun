package com.jguerrerope.speedrun.di


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.jguerrerope.speedrun.ui.gamelist.GameListViewModel
import com.jguerrerope.speedrun.ui.viewmodel.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GameListViewModel::class)
    abstract fun bindGameListViewModel(viewModel: GameListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}