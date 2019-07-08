package com.nimtego.plectrum.presentation.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.nimtego.plectrum.presentation.mvp.view.TabNavigationView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MusicNavigationPresenter @Inject constructor(
        private val musicTabRouter: Router,
        private val bottomBarRouter: Router
) : BasePresenter<TabNavigationView>() {
//
//    @field:[Inject Named(NavigationQualifiers.TAB_MUSIC_NAVIGATION)]
//    internal lateinit var  musicTabNavigationRouter: Router
//
//    @field:[Inject Named(NavigationQualifiers.BOTTOM_BAR_NAVIGATION)]
//    internal lateinit var bottomBarRouter: Router

    fun onBackPressed(): Boolean {
        this.musicTabRouter.exit()
        return true
    }
}