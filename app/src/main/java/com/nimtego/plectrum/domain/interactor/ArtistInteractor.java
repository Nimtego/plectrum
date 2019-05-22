package com.nimtego.plectrum.domain.interactor;

import com.nimtego.plectrum.presentation.main.model.ArtistModelK;

import java.util.List;

import dagger.internal.Preconditions;
import io.reactivex.Observable;

public class ArtistInteractor extends BaseInteractor<List<ArtistModelK>, ArtistInteractor.Params> {

    public ArtistInteractor() {
    }

    @Override
    protected Observable<List<ArtistModelK>> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return repository.artists(params.request);
    }

    public static final class Params {

        private final String request;

        private Params(String request) {
            this.request = request;
        }

        public static Params forRequest(String request) {
            return new Params(request);
        }
    }

}