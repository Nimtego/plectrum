package com.nimtego.itunes.presentation.main;


import com.nimtego.itunes.data.entity.Album;
import com.nimtego.itunes.data.entity.Artist;
import com.nimtego.itunes.data.entity.Song;
import com.nimtego.itunes.data.rest.pojo.AlbumResult;
import com.nimtego.itunes.presentation.base.BaseContract;


import java.util.List;

import io.reactivex.Observable;

public interface AlbumsCollectionContract {
    interface Presenter<V extends View, I extends Interactor> extends BaseContract.Presenter<V, I> {

        void search();

        void pushInRV(int position);

        void longPushInRV(int position);

        void viewIsReady();
    }

    interface View<P extends Presenter> extends BaseContract.View<P> {

        String getSearchText();

        void clearList();

        void setSearchList(List<AlbumResult> list);
    }

    interface Interactor extends BaseContract.Interactor{
        Observable<List<Album>> albums(String response);
        Observable<List<Artist>> artists(String response);
        Observable<List<Song>> songs(String response);
    }
    @Deprecated
    interface OnFinishedListener {

        void onFinished(List<Album> albums);
        void onFailure(Throwable t);
    }
}