package com.nimtego.plectrum.presentation.di.modules.presentation

import com.nimtego.plectrum.domain.interactor.general.InformationInteractor
import com.nimtego.plectrum.domain.interactor.general.MoreSectionInteractor
import com.nimtego.plectrum.domain.interactor.general.TrackInformationInteractor
import com.nimtego.plectrum.domain.interactor.popular.PopularBookInteractor
import com.nimtego.plectrum.domain.interactor.popular.PopularMovieInteractor
import com.nimtego.plectrum.domain.interactor.popular.PopularMusicInteractor
import com.nimtego.plectrum.presentation.di.modules.domain.InteractorModule
import com.nimtego.plectrum.presentation.di.modules.navigation.NavigationModule
import com.nimtego.plectrum.presentation.di.modules.navigation.NavigationQualifiers
import com.nimtego.plectrum.presentation.interactor.LaunchUseCase
import com.nimtego.plectrum.presentation.interactor.MusicalSearchUseCase
import com.nimtego.plectrum.presentation.interactor.SchedulersProvider
import com.nimtego.plectrum.presentation.interactor.detail.AlbumDetailUseCase
import com.nimtego.plectrum.presentation.interactor.detail.ArtistDetailUseCase
import com.nimtego.plectrum.presentation.interactor.detail.SongDetailUseCase
import com.nimtego.plectrum.presentation.manger.MainChoiceItemStorage
import com.nimtego.plectrum.presentation.manger.MusicalItemStorageImp
import com.nimtego.plectrum.presentation.manger.UserSearchItemStorage
import com.nimtego.plectrum.presentation.mvp.presenters.detail.AlbumDetailPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.detail.ArtistDetailPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.detail.SongDetailPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.general.InformationPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.general.MoreSectionPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.general.SplashPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.general.TrackInformationPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.navigation.BottomNavigationPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.navigation.SearchNavigationPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.navigation.SearchTabNavPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.navigation.TabNavigationPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.popular.BookTabPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.popular.MovieTabPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.popular.MusicTabPresenter
import com.nimtego.plectrum.presentation.mvp.presenters.search.SearchContentPresenter
import com.nimtego.plectrum.presentation.navigation.NavigationHandler
import com.nimtego.plectrum.presentation.navigation.SearchTabScreenFabric
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Named

@Module(includes = [InteractorModule::class,
                    NavigationModule::class,
                    StorageModule::class])
class PresenterModule {

    @Provides
    fun splashPresenter(
            @Named(NavigationQualifiers.APP_NAVIGATION) appRouter: Router,
            interactor: LaunchUseCase,
            schedulersProvider: SchedulersProvider
    ): SplashPresenter {
        return SplashPresenter(appRouter, interactor, schedulersProvider)
    }

    @Provides
    fun bottomBarPresenter(
            @Named(NavigationQualifiers.BOTTOM_BAR_NAVIGATION) bottomRouter: Router,
            userSearchItemStorage: UserSearchItemStorage
    ): BottomNavigationPresenter {
        return BottomNavigationPresenter(bottomRouter, userSearchItemStorage)
    }

    @Provides
    @Named(NavigationQualifiers.TAB_MUSIC_NAVIGATION)
    fun musicTabNavPresenter(
            @Named(NavigationQualifiers.TAB_MUSIC_NAVIGATION) router: Router,
            userSearchItemStorage: UserSearchItemStorage
    ): TabNavigationPresenter {
        return TabNavigationPresenter(router, userSearchItemStorage)
    }


    @Provides
    @Named(NavigationQualifiers.SEARCH_MUSIC_NAVIGATION)
    fun musicSearchNavPresenter(
            @Named(NavigationQualifiers.TAB_MUSIC_NAVIGATION) router: Router,
            userSearchItemStorage: UserSearchItemStorage
    ): TabNavigationPresenter {
        return TabNavigationPresenter(router, userSearchItemStorage)
    }


    @Provides
    @Named(NavigationQualifiers.TAB_MOVIE_NAVIGATION)
    fun movieTabNavPresenter(
            @Named(NavigationQualifiers.TAB_MOVIE_NAVIGATION) router: Router,
            userSearchItemStorage: UserSearchItemStorage
    ): TabNavigationPresenter {
        return TabNavigationPresenter(router, userSearchItemStorage)
    }

    @Provides
    @Named(NavigationQualifiers.TAB_BOOK_NAVIGATION)
    fun bookTabNavPresenter(
            @Named(NavigationQualifiers.TAB_BOOK_NAVIGATION) router: Router,
            userSearchItemStorage: UserSearchItemStorage
    ): TabNavigationPresenter {
        return TabNavigationPresenter(router, userSearchItemStorage)
    }

    @Provides
    fun musicTabPresenter(
            @Named(NavigationQualifiers.TAB_MUSIC_NAVIGATION)
            router: Router,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            itemStorage: MainChoiceItemStorage,
            interactor: PopularMusicInteractor
    ): MusicTabPresenter {
        return MusicTabPresenter(router, itemStorage, interactor)
    }

    @Provides
    fun movieTabPresenter(
            @Named(NavigationQualifiers.TAB_MOVIE_NAVIGATION)
            router: Router,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            itemStorage: MainChoiceItemStorage,
            interactor: PopularMovieInteractor
    ): MovieTabPresenter {
        return MovieTabPresenter(router, itemStorage, interactor)
    }

    @Provides
    fun bookTabPresenter(
            @Named(NavigationQualifiers.TAB_BOOK_NAVIGATION)
            router: Router,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            userChoiceItemStorage: MainChoiceItemStorage,
            interactor: PopularBookInteractor
    ): BookTabPresenter {
        return BookTabPresenter(router, userChoiceItemStorage, interactor)
    }

    @Provides
    fun sectionMorePresenter(
            @Named(NavigationQualifiers.BOTTOM_NAVIGATION_ROUTER_HANDLER)
            navigationHandler: NavigationHandler,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            userChoiceItemStorage: MainChoiceItemStorage,
            interactor: MoreSectionInteractor
    ): MoreSectionPresenter {
        return MoreSectionPresenter(navigationHandler, interactor, userChoiceItemStorage)
    }

    @Provides
    @Named(NavigationQualifiers.BOTTOM_BAR_NAVIGATION)
    fun songDetailPresenter(
            @Named(NavigationQualifiers.BOTTOM_NAVIGATION_ROUTER_HANDLER)
            navigationHandler: NavigationHandler,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            userChoiceItemStorage: MainChoiceItemStorage,
            songDetailUseCase: SongDetailUseCase,
            schedulersProvider: SchedulersProvider
    ): SongDetailPresenter {
        return SongDetailPresenter(navigationHandler,
                songDetailUseCase,
                userChoiceItemStorage,
                schedulersProvider)
    }

    @Provides
    @Named(NavigationQualifiers.BOTTOM_BAR_NAVIGATION)
    fun albumDetailPresenter(
            @Named(NavigationQualifiers.BOTTOM_NAVIGATION_ROUTER_HANDLER)
            navigationHandler: NavigationHandler,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            userChoiceItemStorage: MainChoiceItemStorage,
            albumDetailUseCase: AlbumDetailUseCase,
            schedulersProvider: SchedulersProvider
    ): AlbumDetailPresenter {
        return AlbumDetailPresenter(navigationHandler,
                albumDetailUseCase,
                userChoiceItemStorage,
                schedulersProvider)
    }

    @Provides
    @Named(NavigationQualifiers.BOTTOM_BAR_NAVIGATION)
    fun artistDetailPresenter(
            @Named(NavigationQualifiers.BOTTOM_NAVIGATION_ROUTER_HANDLER)
            navigationHandler: NavigationHandler,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            userChoiceItemStorage: MainChoiceItemStorage,
            artistDetailUseCase: ArtistDetailUseCase,
            schedulersProvider: SchedulersProvider
    ): ArtistDetailPresenter {
        return ArtistDetailPresenter(navigationHandler,
                artistDetailUseCase,
                userChoiceItemStorage,
                schedulersProvider)
    }

    //todo
    @Provides
    @Named(NavigationQualifiers.BOTTOM_BAR_NAVIGATION)
    fun informationPresenter(
            @Named(NavigationQualifiers.BOTTOM_NAVIGATION_ROUTER_HANDLER)
            navigationHandler: NavigationHandler,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            userChoiceItemStorage: MainChoiceItemStorage,
            interactor: InformationInteractor
    ): InformationPresenter {
        return InformationPresenter(navigationHandler, interactor, userChoiceItemStorage)
    }

    @Provides
    @Named(NavigationQualifiers.SEARCH_NAVIGATION)
    fun informationPresenterForSearchState(
            @Named(NavigationQualifiers.LOCAL_NAVIGATION_ROUTER_HANDLER)
            navigationHandler: NavigationHandler,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            userChoiceItemStorage: MainChoiceItemStorage,
            interactor: InformationInteractor
    ): InformationPresenter {
        return InformationPresenter(navigationHandler, interactor, userChoiceItemStorage)
    }

    @Provides
    fun trackInformationPresenter(
            @Named(NavigationQualifiers.TAB_MUSIC_NAVIGATION)
            router: Router,
            itemStorage: MusicalItemStorageImp,
            interactor: TrackInformationInteractor
    ): TrackInformationPresenter {
        return TrackInformationPresenter(router, itemStorage, interactor)
    }

    @Provides
    @Named(NavigationQualifiers.SEARCH_NAVIGATION)
    fun searchNavigationPresenter(
            @Named(NavigationQualifiers.SEARCH_NAVIGATION_ROUTER_HANDLER)
            searchRouterHandler: NavigationHandler,
            itemStorage: UserSearchItemStorage,
            searchTabScreenFabric: SearchTabScreenFabric
    ): SearchNavigationPresenter {
        return SearchNavigationPresenter(searchRouterHandler,
                itemStorage,
                searchTabScreenFabric)
    }

    @Provides
    fun searchPresenter(
            @Named(NavigationQualifiers.LOCAL_NAVIGATION_ROUTER_HANDLER)
            navigationHandler: NavigationHandler,
            interactor: MusicalSearchUseCase,
            searchItemStorage: UserSearchItemStorage,
            @Named(StorageQualifiers.MAIN_ITEM_STORAGE_MANAGER)
            userChoiceItemStorage: MainChoiceItemStorage,
            schedulersProvider: SchedulersProvider
    ): SearchContentPresenter {
        return SearchContentPresenter(navigationHandler = navigationHandler,
                musicalSearchUseCase = interactor,
                searchItemStorage = searchItemStorage,
                userChoiceItemStorage = userChoiceItemStorage,
                schedulersProvider = schedulersProvider)
    }

    @Provides
    fun searchTabNavPresenter(
            @Named(NavigationQualifiers.LOCAL_NAVIGATION_ROUTER_HANDLER)
            navigationHandler: NavigationHandler
    ): SearchTabNavPresenter {
        return SearchTabNavPresenter(navigationHandler)
    }
}
