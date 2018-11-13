package com.nimtego.itunes.presentation.main.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nimtego.itunes.data.entity.Song;
import com.nimtego.itunes.presentation.main.SongContract;
import com.nimtego.itunes.presentation.main.SongPresenter;
import com.nimtego.itunes.presentation.main.adapter.SongAdapter;
import com.nimtego.itunes.presentation.main.model.MainDataModel;
import com.nimtego.itunes.presentation.main.model.SongModel;

import java.util.ArrayList;
import java.util.Collection;

public class SongTabsFragment
        extends MainTabsFragment<SongContract.Presenter>
        implements SongContract.View<SongContract.Presenter>  {

    @Override
    protected RecyclerView.LayoutManager rvLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Override
    public void render(Collection<SongModel> songModels) {
        RecyclerView.Adapter adapter = new SongAdapter(new ArrayList<>(songModels),
                this.getActivity());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void search(String response) {
        mPresenter.search(response);
    }

    @Override
    public SongContract.Presenter supplyPresenter() {
        return new SongPresenter();
    }
}
