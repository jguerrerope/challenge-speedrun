package com.jguerrerope.speedrunchallenge.di

import com.jguerrerope.speedrunchallenge.SpeedrunApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            (AndroidInjectionModule::class),
            (AppModule::class)
        ]
)
abstract class AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: SpeedrunApp): Builder

        fun build(): AppComponent
    }

    abstract fun inject(app: SpeedrunApp)
}