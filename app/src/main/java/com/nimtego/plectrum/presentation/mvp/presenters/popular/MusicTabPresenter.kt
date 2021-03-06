package com.nimtego.plectrum.presentation.mvp.presenters.popular

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.nimtego.plectrum.data.repository.datasource.popular.music.PopularMusicKey
import com.nimtego.plectrum.domain.interactor.popular.PopularMusicInteractor
import com.nimtego.plectrum.presentation.di.modules.navigation.NavigationQualifiers
import com.nimtego.plectrum.presentation.manger.MainItemStorage
import com.nimtego.plectrum.presentation.mvp.model.main_tab_model.BaseParentViewModel
import com.nimtego.plectrum.presentation.mvp.model.main_tab_model.ChildViewModel
import com.nimtego.plectrum.presentation.mvp.model.main_tab_model.ParentTabModelContainer
import com.nimtego.plectrum.presentation.mvp.model.song.AlbumWrapperModel
import com.nimtego.plectrum.presentation.mvp.model.song.SongWrapperModel
import com.nimtego.plectrum.presentation.mvp.presenters.base.BaseContentPresenter
import com.nimtego.plectrum.presentation.mvp.view.TabContentView
import com.nimtego.plectrum.presentation.navigation.Screens
import com.nimtego.plectrum.presentation.ui.widget.adapters.ParentTabAdapter
import io.reactivex.observers.DisposableObserver
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MusicTabPresenter @Inject constructor(
        override var router: Router,
        private val itemStorage: MainItemStorage,
        private val interactor: PopularMusicInteractor
) : BaseContentPresenter<TabContentView>(), ParentTabAdapter.OnItemClickListener {

    private var songsModel: BaseParentViewModel<ChildViewModel>? = null

    override fun prepareViewModel() {
        this.songsModel ?: run {
            interactor.execute(object : DisposableObserver<BaseParentViewModel<ChildViewModel>>() {
                override fun onComplete() {
                    Log.i("Presenter", "onComplete()")
                }

                override fun onNext(songs: BaseParentViewModel<ChildViewModel>) {
                    Log.i("Presenter", "onnext")
                    this@MusicTabPresenter.songsModel = songs
                    this@MusicTabPresenter.showModel()
                }

                override fun onError(e: Throwable) {
                    Log.i("Presenter", "error $e")
                }
            }, PopularMusicInteractor
                    .Params
                    .forRequestWithSize(PopularMusicKey.TOP_ALBUM, 10))
        }
    }

    private fun showModel() {
        this.songsModel?.let {
            this.viewState.showViewState(it)
        }
    }

    override fun sectionClicked(section: ParentTabModelContainer<ChildViewModel>) {
        this.itemStorage.changeCurrentSection(section)
        this.router.navigateTo(
                Screens.MoreContentScreen(NavigationQualifiers.TAB_MUSIC_NAVIGATION)
        )
    }

    override fun childItemClicked(childViewModel: ChildViewModel) {
        this.itemStorage.changeCurrentChildItem(childViewModel)
        this.router.navigateTo(
                when(childViewModel) {
                    is SongWrapperModel -> Screens.SongDetailScreen(navigationQualifier)
                    is AlbumWrapperModel -> Screens.AlbumDetailScreen(navigationQualifier)
                    else -> throw Exception("Implement - ${childViewModel.javaClass.name} not permissible")
                }
        )
    }

    companion object {
        const val navigationQualifier = NavigationQualifiers.TAB_MUSIC_NAVIGATION
    }
}