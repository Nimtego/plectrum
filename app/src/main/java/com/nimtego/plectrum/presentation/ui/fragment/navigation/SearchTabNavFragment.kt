package com.nimtego.plectrum.presentation.ui.fragment.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nimtego.plectrum.App
import com.nimtego.plectrum.presentation.di.modules.navigation.NavigationQualifiers
import com.nimtego.plectrum.presentation.mvp.presenters.navigation.SearchTabNavPresenter
import com.nimtego.plectrum.presentation.navigation.NavigationHandler
import com.nimtego.plectrum.presentation.navigation.ParentHolderFragmentNavigator
import com.nimtego.plectrum.presentation.navigation.Screens
import com.nimtego.plectrum.presentation.ui.common.ParentRouterProvider
import com.nimtego.plectrum.presentation.ui.fragment.base.BaseTabSearchNavFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject
import javax.inject.Named

class SearchTabNavFragment : BaseTabSearchNavFragment() {

    @field:[Inject Named(NavigationQualifiers.LOCAL_NAVIGATION_ROUTER_HANDLER)]
    lateinit var navigationHolderVariable: NavigationHandler

    @Inject
    @InjectPresenter
    override lateinit var presenter: SearchTabNavPresenter

    private val navigationQualifier: String
        get() = requireNotNull(this.arguments?.getString(NAVIGATION_QUALIFIERS))

    override val navigatorHolder: NavigatorHolder by lazy {
        this.navigationHolderVariable.getNavigatorHolder(this.navigationQualifier)
    }

    @ProvidePresenter
    fun provideRepositoryPresenter(): SearchTabNavPresenter {
        this.presenter.setNavigationQualifiers(this.navigationQualifier)
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.navigator?.applyCommands(arrayOf(Replace(Screens.SearchContentScreen(this.navigationQualifier))))
    }

    override fun provideNavigator(): Navigator? {
        return context?.let {
            SearchTabNavigator(childFragmentManager,
                    it as AppCompatActivity,
                    this.layoutContainer,
                    (parentFragment as ParentRouterProvider).getParentRouter())
        }
    }

// MARK: - Inner Types

    private inner class SearchTabNavigator(
            fragmentManager: FragmentManager?,
            activity: AppCompatActivity,
            container: Int,
            parentRouter: Router
    ) : ParentHolderFragmentNavigator(activity, fragmentManager, container, parentRouter) {

        override fun createFragment(screen: SupportAppScreen): Fragment? {
            return when (screen) {
                is Screens.SearchContentScreen -> screen.fragment
                is Screens.SearchItemInformationScreen -> screen.fragment
                else -> throw Exception("Screen - ${screen.screenKey} not permissible")
            }
        }
    }

    companion object {
        fun getInstance(navigationQualifier: String): SearchTabNavFragment {
            val fragment = SearchTabNavFragment()
            val arguments = Bundle()

            arguments.putString(NAVIGATION_QUALIFIERS, navigationQualifier)
            fragment.arguments = arguments

            return fragment
        }
    }
}