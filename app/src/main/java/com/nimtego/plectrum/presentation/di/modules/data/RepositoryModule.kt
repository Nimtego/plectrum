package com.nimtego.plectrum.presentation.di.modules.data

import android.content.Context
import com.nimtego.plectrum.data.cache.Cache
import com.nimtego.plectrum.data.cache.DashBoardEntityCache
import com.nimtego.plectrum.data.cache.FileManager
import com.nimtego.plectrum.data.cache.Serializer
import com.nimtego.plectrum.data.entity.mapper.EntityDataMapper
import com.nimtego.plectrum.data.executor.BaseExecutor
import com.nimtego.plectrum.data.model.rss_itunes.PopularResponse
import com.nimtego.plectrum.data.repository.datasource.DataStoreFactory
import com.nimtego.plectrum.data.repository.repository.DashBoardRepository
import com.nimtego.plectrum.data.repository.repository.TabContentRepository
import com.nimtego.plectrum.domain.executor.ThreadExecutor
import com.nimtego.plectrum.presentation.di.modules.ContextModule
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideDashBoardRepository(mapper: EntityDataMapper,
                                            dataStoreFactory: DataStoreFactory<PopularResponse>) =
            DashBoardRepository(dataStoreFactory, mapper)

    @Provides
    @Singleton
    internal fun provideTabContentRepository(mapper: EntityDataMapper,
                                             dataStoreFactory: DataStoreFactory<PopularResponse>) =
            TabContentRepository(dataStoreFactory, mapper)

    @Provides
    internal fun compositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    internal fun entityDataMapper(): EntityDataMapper {
        return EntityDataMapper()
    }

    @Provides
    internal fun serializer(): Serializer {
        return Serializer()
    }

    @Provides
    @Singleton
    internal fun fileManager(): FileManager {
        return FileManager()
    }

    @Provides
    internal fun cache(appContext: Context,
                       serializer: Serializer,
                       fileManager: FileManager,
                       threadExecutor: ThreadExecutor): Cache<PopularResponse> {
        return DashBoardEntityCache(appContext, serializer, fileManager, threadExecutor)
    }

    @Provides
    internal fun storeFactory(appContext: Context, cache: Cache<PopularResponse>): DataStoreFactory<PopularResponse> {
        return DataStoreFactory(appContext, cache)
    }

    @Provides
    internal fun executor(): ThreadExecutor {
        return BaseExecutor()
    }

}