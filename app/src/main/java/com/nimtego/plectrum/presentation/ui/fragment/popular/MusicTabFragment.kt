package com.nimtego.plectrum.presentation.ui.fragment.popular

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nimtego.plectrum.App
import com.nimtego.plectrum.presentation.mvp.model.main_tab_model.BaseParentViewModel
import com.nimtego.plectrum.presentation.mvp.model.main_tab_model.ChildViewModel
import com.nimtego.plectrum.presentation.mvp.presenters.popular.MusicTabPresenter
import com.nimtego.plectrum.presentation.ui.widget.adapters.ParentTabAdapter
import javax.inject.Inject

class MusicTabFragment : BaseTabFragment() {

    @Inject
    @InjectPresenter
    internal lateinit var presenter: MusicTabPresenter

    @ProvidePresenter
    fun provideRepositoryPresenter(): MusicTabPresenter {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        this.presenter.onBackPressed()
        return true
    }

//Mark: view override

    override fun showViewState(data: BaseParentViewModel<ChildViewModel>) {
        this.parentContainerRecyclerView?.apply {
            adapter = ParentTabAdapter(data.sectionViewModel).apply {
                setOnItemClickListener(presenter)
            }
        }
    }

    companion object {
        fun getInstance(): MusicTabFragment {
            val fragment = MusicTabFragment()

            val arguments = Bundle()
            arguments.putString(TAB_NAME, TAB)
            fragment.arguments = arguments

            return fragment
        }

        const val TAB_NAME = "TAB_NAME"
        const val TAB = "MUSIC_TAB"
    }
}