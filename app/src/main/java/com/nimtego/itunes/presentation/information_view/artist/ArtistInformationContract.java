package com.nimtego.itunes.presentation.information_view.artist;

import com.nimtego.itunes.presentation.base.BaseContract;
import com.nimtego.itunes.presentation.information_view.artist.model.ArtistDetailsModel;
import com.nimtego.itunes.presentation.main.model.AlbumModel;

public interface ArtistInformationContract {
    interface Presenter<V extends View, I extends BaseContract.Interactor>
            extends BaseContract.Presenter<V, I> {
        void viewReady(String albumNameForResponse);

        void albumClicked(AlbumModel album);
    }

    interface View<P extends Presenter> extends BaseContract.View<P> {
        void render(ArtistDetailsModel albumDetailsModel);
    }
}
