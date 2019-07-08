package com.nimtego.plectrum.presentation.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nimtego.plectrum.App
import com.nimtego.plectrum.R
import com.nimtego.plectrum.presentation.di.modules.navigation.NavigationQualifiers
import com.nimtego.plectrum.presentation.mvp.presenters.MovieTabPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.RouterProvider
import com.nimtego.plectrum.presentation.mvp.view.TabContentView
import com.nimtego.plectrum.presentation.mvp.view_model.main_tab_model.BaseParentViewModel
import com.nimtego.plectrum.presentation.mvp.view_model.main_tab_model.ChildViewModel
import com.nimtego.plectrum.presentation.ui.widget.SpaceItemDecorator
import com.nimtego.plectrum.presentation.ui.widget.adapters.ParentTabAdapter
import com.nimtego.plectrum.presentation.utils.BackButtonListener
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import javax.inject.Named

class MovieTabFragment : BaseFragment(), TabContentView, RouterProvider, BackButtonListener {

    override val layoutRes: Int = R.layout.bottom_parent_tab_fragment

    private var parentContainerRecyclerView: RecyclerView? = null

    @field:[Inject Named(NavigationQualifiers.TAB_MOVIE_NAVIGATION)]
    internal lateinit var tabMovieRouter: Router

    @field:[Inject Named(NavigationQualifiers.BOTTOM_BAR_NAVIGATION)]
    internal lateinit var parentRouter: Router

    @Inject
    @InjectPresenter
    internal lateinit var presenter: MovieTabPresenter

    @ProvidePresenter
    fun provideRepositoryPresenter(): MovieTabPresenter {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRV()
        this.presenter.viewIsReady(getContainerName())
    }

    private fun getContainerName(): String {
        return arguments.getString(TAB_NAME)
    }

    override fun getRouter(): Router {
        return tabMovieRouter
    }

    override fun onBackPressed(): Boolean {
        tabMovieRouter.exit()
        return true
    }

    protected fun initRV() {
        this.parentContainerRecyclerView = view
                ?.findViewById(R.id.recycler_view_parent_tab_container)
        this.parentContainerRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MovieTabFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false)
            addItemDecoration(SpaceItemDecorator(spacing = 32,
                    spanCount = 1,
                    paddingTop = 24,
                    paddingBottom = 24))
//            itemAnimator = DefaultItemAnimator()
        }
    }

//Mark: view override

    override fun showViewState(data: BaseParentViewModel<ChildViewModel>) {
        this.parentContainerRecyclerView?.apply {
            adapter = ParentTabAdapter(data, this.context).apply {
                setOnItemClickListener(presenter)
            }
        }
    }

    companion object {
        fun getInstance(): MovieTabFragment {
            val fragment = MovieTabFragment()

            val arguments = Bundle()
            arguments.putString(TAB_NAME, TAB)
            fragment.arguments = arguments

            return fragment
        }
        val TAB_NAME = "TAB_NAME"
        val TAB = "MOVIE_TAB"
    }
}