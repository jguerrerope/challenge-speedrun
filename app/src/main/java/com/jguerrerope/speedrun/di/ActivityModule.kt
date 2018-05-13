package com.jguerrerope.speedrun.di

import com.jguerrerope.speedrun.ui.gamelist.GameListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(ViewModelModule::class)])
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeGameListActivity(): GameListActivity
}


