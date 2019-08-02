package com.nimtego.plectrum.presentation.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.nimtego.plectrum.presentation.manger.UserSearchItemStorage
import com.nimtego.plectrum.presentation.mvp.view.MainBottomNavigationView
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

@InjectViewState
class BottomNavigationPresenter @Inject constructor(
        private val bottomNavigationRouter: Router,
        private val appRouter: Router,
        private val userSearchItemStorage: UserSearchItemStorage
) : BaseNavigationPresenter<MainBottomNavigationView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        this.userSearchItemStorage.getCurrentSearchTextObservable().subscribe {
            this@BottomNavigationPresenter.viewState.systemMessage(it)
        }
    }

    fun replaceFragment(screenName: SupportAppScreen) {
        this.bottomNavigationRouter.replaceScreen(screenName)
    }

    override fun onBackPressed(): Boolean {
        this.bottomNavigationRouter.exit()
        return true
    }

    fun searchTextSubmit(text: String) {
        this.userSearchItemStorage.overrideCurrentSearchText(text)
    }
}