package com.nimtego.plectrum.presentation.di.modules.navigation

import com.nimtego.plectrum.presentation.navigation.LocalCiceroneHolder
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton


@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    internal fun provideRouter(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }
}