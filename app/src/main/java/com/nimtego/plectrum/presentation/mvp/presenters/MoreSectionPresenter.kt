package com.nimtego.plectrum.presentation.mvp.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.nimtego.plectrum.data.entity.Album
import com.nimtego.plectrum.data.entity.DashBoardSongsModel
import com.nimtego.plectrum.data.entity.Song
import com.nimtego.plectrum.domain.interactor.MoreSectionInteractor
import com.nimtego.plectrum.domain.interactor.TabContentInteractor
import com.nimtego.plectrum.presentation.mvp.view.MoreSectionView
import com.nimtego.plectrum.presentation.mvp.view.TabContentView
import com.nimtego.plectrum.presentation.mvp.view_model.dashboard.BaseParentViewModel
import com.nimtego.plectrum.presentation.mvp.view_model.dashboard.ChildViewModel
import com.nimtego.plectrum.presentation.mvp.view_model.dashboard.DashBoardModelContainer
import com.nimtego.plectrum.presentation.mvp.view_model.dashboard.SectionViewModel
import com.nimtego.plectrum.presentation.navigation.Screens
import io.reactivex.observers.DisposableObserver
import ru.terrakok.cicerone.Router

@InjectViewState
class MoreSectionPresenter(
        router: Router,
        screenNumber: Int,
        private val interactor: MoreSectionInteractor
) : BasePresenter<MoreSectionView>(router, screenNumber) {

    private var dataSongsModel: List<Song>? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewIsReady()
    }

    fun viewIsReady() {
        dataSongsModel?.let { showModel(it) }.run {
            interactor.execute(object : DisposableObserver<List<Song>>() {
                override fun onComplete() {
                    Log.i("Presenter", "onComplete()")
                }

                override fun onNext(songs: List<Song>) {
                    Log.i("Presenter", "onnext")
                    this@MoreSectionPresenter.dataSongsModel = dataSongsModel
                    this@MoreSectionPresenter.showModel(songs)
                }

                override fun onError(e: Throwable) {
                    Log.i("Presenter", "onerror $e")
//                this@DashBoardPresenter.hideViewLoading()
//                this@DashBoardPresenter.toast("error" + e.localizedMessage)
//                // TODO: 01.11.2018 retry  view (showRetry() + hideRetry() in contract);

                }
            }, MoreSectionInteractor.Params.forRequest(""))
        }
    }

    private fun showModel(songs: List<Song>) {

        viewState.showViewState(songs)
    }

    fun albumClicked(albumModel: Album) {
        router.navigateTo(Screens.AlbumInformationDetail(albumModel.albumId.toString()))
    }

    fun songClicked(songModel: Song) {
        router.navigateTo(Screens.SongInformationDetail(songModel.trackId.toString()))
    }
}