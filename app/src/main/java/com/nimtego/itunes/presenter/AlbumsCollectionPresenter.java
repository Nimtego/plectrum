package com.nimtego.itunes.presenter;

import android.support.annotation.NonNull;

import com.nimtego.itunes.App;
import com.nimtego.itunes.model.ModelManager;
import com.nimtego.itunes.mvp_contracts.AlbumsCollectionContract;
import com.nimtego.itunes.service.AlbumResult;
import com.nimtego.itunes.service.AlbumsRepository;
import com.nimtego.itunes.service.FabricParam;
import com.nimtego.itunes.service.ITunesApi;
import com.nimtego.itunes.utils.IpTags;
import com.nimtego.itunes.view.InformationAlbumActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumsCollectionPresenter
        extends BasePresenter<AlbumsCollectionContract.View>
        implements AlbumsCollectionContract.Presenter<AlbumsCollectionContract.View> {

    private ModelManager<AlbumResult> mModelManager;

    public AlbumsCollectionPresenter() {
        mModelManager = App.getModelManager();
    }

    @Override
    public void search() {
        final String message = view.getsearchText();
        view.toast(message);
        ITunesApi iTunesApi = App.getApi();
        view.showLoading();
        Call<AlbumsRepository> call = iTunesApi.getAlbum(FabricParam.searchAlbumParam(message, 100));
        call.enqueue(new Callback<AlbumsRepository>() {
            @Override
            public void onResponse(@NonNull Call<AlbumsRepository> call, @NonNull final Response<AlbumsRepository> response) {
                AlbumsRepository mResultEntityList = response.body();
                List<AlbumResult> resultEntity = mResultEntityList.getResults();
                mModelManager.setAlbumCollection(resultEntity, message);
                view.hideLoading();
                Collections.sort(resultEntity, new Comparator<AlbumResult>() {
                    @Override
                    public int compare(AlbumResult o1, AlbumResult o2) {
                        return o1.getCollectionName().compareTo(o2.getCollectionName());
                    }
                });
                view.setSearchList(mModelManager.getListAlbum());
/*                view.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setSearchList(mResultEntityList.getResults());
                    }
                });*/
            }

            @Override
            public void onFailure(@NonNull Call<AlbumsRepository> call, @NonNull Throwable t) {
                view.hideLoading();
                view.toast("Networking error");
            }
        });
    }

    @Override
    public void pushInRV(int position) {
        view.intent(IpTags.ALBUM_ID, String.valueOf(mModelManager.getListAlbum().get(position).getCollectionId()));
    }

    @Override
    public void longPushInRV(int position) {
    }

    @Override
    public void viewIsReady() {
        if (!mModelManager.getListAlbum().isEmpty())
            view.setSearchList(mModelManager.getListAlbum());
        else
            search();
    }

    @Override
    public Class<?> getNextActivity() {
        return InformationAlbumActivity.class;
    }
}
