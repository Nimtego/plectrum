package com.nimtego.itunes.data.repository.datasource;

import android.content.Context;

import com.nimtego.itunes.App;
import com.nimtego.itunes.data.cache.Cache;
import com.nimtego.itunes.data.rest.network.ITunesApi;
import com.nimtego.itunes.data.rest.network.WikiApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.annotations.NonNull;

@Singleton
public class DataStoreFactory {

    private final Context context;
    private final Cache cache;

    @Inject
    public DataStoreFactory(@NonNull Context context, @NonNull Cache cache) {
        this.context = context.getApplicationContext();
        this.cache = cache;
    }

    public DataStore create(int userId) {
        DataStore userDataStore;

        if (!this.cache.isExpired() && this.cache.isCached(userId)) {
            userDataStore = new DiskDataStore(this.cache);
        } else {
            userDataStore = createCloudDataStore();
        }

        return userDataStore;
    }

    public DataStore createCloudDataStore() {

        final ITunesApi iTunesApi = App.getiTunesApi();
        final WikiApi wikiApi = App.getWikiApi();
        return new CloudDataStore(iTunesApi, wikiApi, this.cache);
    }
}
