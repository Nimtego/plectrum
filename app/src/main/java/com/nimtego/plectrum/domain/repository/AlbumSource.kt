package com.nimtego.plectrum.domain.repository

import io.reactivex.Observable

interface AlbumSource<R> {
    fun getAlbumsByRequest(request: String): Observable<List<R>>
    fun getAlbumsByAuthorId(id: Int): Observable<List<R>>
    fun getAlbumBySongId(id: Int): Observable<R>
    fun getAlbumById(id: Int): Observable<R>
}