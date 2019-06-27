package com.nimtego.plectrum.data.repository.datasource;

import com.nimtego.plectrum.data.model.rss_itunes.PopularResponse;
import com.nimtego.plectrum.data.model.itunes.AlbumsRepository;
import com.nimtego.plectrum.data.model.itunes.ArtistsRepository;
import com.nimtego.plectrum.data.model.itunes.SongsRepository;
import com.nimtego.plectrum.data.model.wiki.WikiSearchResult;

import io.reactivex.Observable;

public interface DataStore {

    Observable<WikiSearchResult> wikiSearch(String response);

    Observable<SongsRepository> songs(String response);

    Observable<SongsRepository> songsByIdAlbum(int id);

    Observable<ArtistsRepository> artists(String response);

    Observable<AlbumsRepository> albums(String response);

    Observable<SongsRepository> songById(int id);

    Observable<ArtistsRepository> artistById(int id);

    Observable<AlbumsRepository> album(String response);

    Observable<PopularResponse> recent();

    Observable<PopularResponse> topSong(int size);

    Observable<PopularResponse> topAlbum();

    Observable<PopularResponse> hot();

    Observable<PopularResponse> newMusick();
}
