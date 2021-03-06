package com.nimtego.plectrum.data.repository.datasource.popular.movie

import com.nimtego.plectrum.data.cache.Cache
import com.nimtego.plectrum.data.cache.PopularResponseCache
import com.nimtego.plectrum.data.model.rss_itunes.PopularResponse
import com.nimtego.plectrum.data.repository.datasource.popular.movie.PopularMovieKey
import io.reactivex.Observable
import javax.inject.Inject

class DiskPopularMovie @Inject constructor(
        private val cache: PopularResponseCache
) : PopularMovieDataStore {

    override fun topMovie(responseSize: Int): Observable<PopularResponse> {
        return cache.get(PopularMovieKey.TOP_MOVIE.key)
    }
}