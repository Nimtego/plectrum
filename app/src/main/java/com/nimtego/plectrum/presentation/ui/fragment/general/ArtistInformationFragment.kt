package com.nimtego.plectrum.presentation.ui.fragment.general

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.nimtego.plectrum.R
import com.nimtego.plectrum.presentation.di.modules.navigation.NavigationQualifiers
import com.nimtego.plectrum.presentation.mvp.model.music.ArtistModel
import com.nimtego.plectrum.presentation.mvp.presenters.general.ArtistInformationPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.general.InformationPresenter
import com.nimtego.plectrum.presentation.mvp.view.ArtistInformationView
import com.nimtego.plectrum.presentation.ui.fragment.base.BaseFragment
import com.nimtego.plectrum.presentation.utils.BackButtonListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.artist_information_fragment.*
import javax.inject.Inject
import javax.inject.Named


class ArtistInformationFragment : BaseFragment(), ArtistInformationView, BackButtonListener {

    override val layoutRes: Int = R.layout.artist_information_fragment

    private lateinit var price: TextView
    private lateinit var date: TextView
    private lateinit var artistName: TextView
    private lateinit var albumImage: ImageView
    private lateinit var information: TextView
    private lateinit var pb: ProgressBar
    private lateinit var albumsRv: RecyclerView
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    //todo create presenter and other
    @field:[Inject Named(NavigationQualifiers.BOTTOM_BAR_NAVIGATION)]
    @InjectPresenter
    internal lateinit var presenter: ArtistInformationPresenter

    @ProvidePresenter
    fun provideRepositoryPresenter(): ArtistInformationPresenter {
        val navigationQualifier = requireNotNull(this.arguments?.getString(TAB_NAME))
        this.presenter.setNavigationQualifier(navigationQualifier)
        return presenter
    }

    override fun onBackPressed(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initView() {
        this.artistName = author_value
        this.date = release_date_value
        this.price = price_value
        this.information = information_value
        this.albumImage = image_album
        this.collapsingToolbarLayout = collapsing_toolbar
        this.pb = image_progress_bar

        initAlbumsListRV()
    }

    private fun initAlbumsListRV() {
        this.albumsRv = album_rv
        albumsRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            ViewCompat.setNestedScrollingEnabled(this, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun showViewState(artistModel: ArtistModel) {
        artistName.text = artistModel.artistName
        //price.setText(data.getArtistArtwork())
        //information!!.setText(artistDetailsModel.getWikiInformation())
        collapsingToolbarLayout.title = artistModel.artistName
//        val albumsAdapter = AlbumsAdapterForArtist(artistDetailsModel.getAlbums(),
//                this.getActivity())
//        albumsAdapter.setOnItemClickListener(object : AlbumsAdapterForArtist.OnItemClickListener() {
//            fun onUserItemClicked(albumModel: AlbumModel) {
//                mPresenter.albumClicked(albumModel)
//            }
//        })
//        albumsRv.adapter = albumsAdapter

        Picasso.get().load(artistModel.artistArtwork
                .replace("135x135", "570x570"))
                .into(albumImage, object : Callback {
                    override fun onSuccess() {
                            pb.visibility = View.GONE
                    }

                    override fun onError(e: Exception) {
                            pb.visibility = View.GONE
                    }
                })
    }

    companion object {
        fun getInstance(navigationQualifier: String): ArtistInformationFragment {
            val fragment = ArtistInformationFragment()

            val arguments = Bundle()
            arguments.putString(TAB_NAME, navigationQualifier)
            fragment.arguments = arguments

            return fragment
        }

        const val TAB_NAME = "TabName"
    }
}