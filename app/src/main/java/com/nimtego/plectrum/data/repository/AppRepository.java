package com.nimtego.plectrum.data.repository;

import com.nimtego.plectrum.App;
import com.nimtego.plectrum.data.cache.AlbumCache;
import com.nimtego.plectrum.data.cache.FileManager;
import com.nimtego.plectrum.data.entity.mapper.EntityDataMapperK;
import com.nimtego.plectrum.data.repository.datasource.DataStore;
import com.nimtego.plectrum.data.repository.datasource.DataStoreFactory;
import com.nimtego.plectrum.data.rest.pojo.AlbumResult;
import com.nimtego.plectrum.data.rest.pojo.ArtistsRepository;
import com.nimtego.plectrum.domain.Repository;
import com.nimtego.plectrum.presentation.information_view.album.model.AlbumDetailsModelK;
import com.nimtego.plectrum.presentation.information_view.artist.model.ArtistDetailsModelK;
import com.nimtego.plectrum.presentation.information_view.song.model.SongDetailsModelK;
import com.nimtego.plectrum.presentation.main.model.AlbumModelK;
import com.nimtego.plectrum.presentation.main.model.ArtistModelK;
import com.nimtego.plectrum.presentation.main.model.SongModelK;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AppRepository implements Repository {

    private DataStoreFactory dataStoreFactory;
    private EntityDataMapperK mapper;


    public AppRepository(DataStoreFactory dataStoreFactory, EntityDataMapperK mapper) {
        this.dataStoreFactory = dataStoreFactory;
        this.mapper = mapper;
    }

    @Inject
    public AppRepository() {
        this(new DataStoreFactory(App.getAppContext(),
                new AlbumCache(App.getAppContext(),
                        new FileManager())), new EntityDataMapperK());
    }

    @Override
    public Observable<List<SongModelK>> songs(String request) {
        final DataStore dataStore = this.dataStoreFactory.createCloudDataStore();
        return dataStore.songs(request).map(this.mapper::transformSongs);
    }

    @Override
    public Observable<List<ArtistModelK>> artists(String request) {
        final DataStore dataStore = this.dataStoreFactory.createCloudDataStore();
        return dataStore.artists(request)
                .map(ArtistsRepository::getResults)
                .flatMap(list -> Observable.fromIterable(list).take(3)
                        .flatMap(artistResult -> changeLink(artistResult.getArtistLinkUrl())
                                .map(url -> {
                                    artistResult.setArtistLinkUrl(url);
                                    return artistResult;
                                }))
                        .toList()
                        .toObservable()
                        .map(mapper::transformArtists));
    }

    private Observable<String> changeLink(String oldUrl) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String url = "EMPTY";
                Elements element = Jsoup.connect(oldUrl)
                        .get()
                        .getElementsByClass("we-artwork ember-view we-artist-header__background we-artwork--round we-artwork--no-border");
                if (!element.isEmpty()) {
                    url = element.select("img")
                            .get(0)
                            .attr("src");
                }
                return url;
            }

        });
    }

    @Override
    public Observable<List<AlbumModelK>> albums(String request) {
        final DataStore dataStore = this.dataStoreFactory.createCloudDataStore();
        return dataStore.albums(request).map(this.mapper::transformAlbums);
    }


    @Override
    public Observable<SongDetailsModelK> songDeteil(String request) {
        final DataStore dataStore = this.dataStoreFactory.createCloudDataStore();
        return dataStore.songById(Integer.valueOf(request)).map(result ->
                this.mapper.transformSongDetail(result.getResults().get(0)));
    }

    @Override
    public Observable<ArtistModelK> artist(String request) {
        return null;
    }

    @Override
    public Observable<AlbumDetailsModelK> albumDeteil(String request) {
        final DataStore dataStore = this.dataStoreFactory.createCloudDataStore();
        return dataStore.album(request)
                .flatMap(album -> {
                    AlbumResult albumResult = album.getFirst();
                    return Observable.zip(dataStore
                                    .songsByIdAlbum(albumResult.getCollectionId()),
                            dataStore.wikiSearch(albumResult.getArtistName()),
                            (song, wiki) -> {
                                AlbumDetailsModelK albumDetail =
                                        mapper.transformAlbumDetail(albumResult);
                                //todo imut
//                                albumDetail.setSongs(mapper.transformSongs(song));
//                                albumDetail.setWikiInformation(wiki.isEmpty() ?
//                                        "No information in wiki"
//                                        : mapper.wikiInformationArtist(wiki));
                                return albumDetail;
                            });
                });
    }

    @Override
    public Observable<ArtistDetailsModelK> artistDetail(String id) {
        final DataStore dataStore = this.dataStoreFactory.createCloudDataStore();
        return Observable.zip(dataStore.artistById(Integer.valueOf(id)),
                dataStore.album(id), (artist, albums) -> {
                    ArtistDetailsModelK artistDetails = mapper.transformArtistDetail(artist.getResults().get(0));
                    //todo imut
//                    artistDetails.setAlbums(mapper.transformAlbums(albums));
                    return artistDetails;
                }).flatMap(result -> changeLink(result.getArtistArtwork())
                .map(url -> {
                    //todo  imut
//                            result.setArtistArtwork(url);
                    return result;
                }));
    }
}
