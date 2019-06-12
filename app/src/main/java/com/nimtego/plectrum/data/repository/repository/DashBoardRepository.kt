package com.nimtego.plectrum.data.repository.repository

import com.nimtego.plectrum.data.entity.DashBoardModel
import com.nimtego.plectrum.data.entity.mapper.EntityDataMapper
import com.nimtego.plectrum.data.model.rss_itunes.Feed
import com.nimtego.plectrum.data.model.rss_itunes.PopularResponse
import com.nimtego.plectrum.data.repository.datasource.DataStoreFactory
import com.nimtego.plectrum.domain.repository.RepositoryK
import io.reactivex.Observable
import io.reactivex.functions.Function4

class DashBoardRepository(
        private val dataStoreFactory: DataStoreFactory<PopularResponse>,
        private val mapper: EntityDataMapper
) : RepositoryK<DashBoardModel> {

    override fun query(request: String): Observable<DashBoardModel> {
        //todo
        val dataStore = this.dataStoreFactory.createCloudDataStore()
        val hotOb = dataStore.hot()
        val newMusicOb = dataStore.newMusick()
        val topAlbumOb = dataStore.topAlbum()
        val topSongOb = dataStore.topSong()
        return Observable.zip<PopularResponse, PopularResponse, PopularResponse, PopularResponse, DashBoardModel>(topAlbumOb,
                topSongOb,
                hotOb,
                newMusicOb,
                Function4<PopularResponse, PopularResponse, PopularResponse, PopularResponse, DashBoardModel>
                { topAlbum, topSong, hotSong, newMusic ->
                    mapper.dashBoardModel(topSong.feed, topAlbum.feed, newMusic.feed, hotSong.feed)
                }
        )


    }
}