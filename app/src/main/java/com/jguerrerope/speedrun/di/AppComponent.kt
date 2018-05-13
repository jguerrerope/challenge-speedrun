package com.jguerrerope.speedrun.di

import com.jguerrerope.speedrun.SpeedRunApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            (AndroidInjectionModule::class),
            (AppModule::class),
            (ActivityModule::class)
        ]
)
abstract class AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: SpeedRunApp): Builder

        fun build(): AppComponent
    }

    abstract fun inject(app: SpeedRunApp)
}