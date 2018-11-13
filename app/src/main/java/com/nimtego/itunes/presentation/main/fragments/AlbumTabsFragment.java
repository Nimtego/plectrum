package com.nimtego.itunes.presentation.main.fragments;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nimtego.itunes.presentation.main.AlbumContract;
import com.nimtego.itunes.presentation.main.AlbumPresenter;
import com.nimtego.itunes.presentation.main.adapter.AlbumAdapter;
import com.nimtego.itunes.presentation.main.model.AlbumModel;
import com.nimtego.itunes.presentation.main.model.MainDataModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AlbumTabsFragment
        extends MainTabsFragment<AlbumContract.Presenter>
        implements AlbumContract.View<AlbumContract.Presenter> {

    @Override
    public void render(Collection<AlbumModel> albumModel) {
        RecyclerView.Adapter adapter = new AlbumAdapter(new ArrayList<>(albumModel),
                this.getActivity());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected RecyclerView.LayoutManager rvLayoutManager(Context context) {
        return new GridLayoutManager(context, 2);
    }


    @Override
    public void search(String response) {
        mPresenter.search(response);
    }

    @Override
    public AlbumContract.Presenter supplyPresenter() {
        return new AlbumPresenter();
    }


/*    @Override
    protected RecyclerView.LayoutManager rvLayoutManager(Context context) {
        return new GridLayoutManager(context, 2);
    }

    @Override
    public void setSearchList(MainDataModel dataModel) {
        RecyclerView.Adapter adapter = new AlbumAdapter(dataModel.getAlbumModels(),
                this.getActivity());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public AlbumContract.Presenter supplyPresenter() {
        return new AlbumPresenter();
    }


    @Override
    public void render(Collection albumModel) {

    }

    @Override
    public void search(String response) {

    }*/
}
