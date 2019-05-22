package com.nimtego.plectrum.presentation.migrate_kotlin.di

import com.nimtego.plectrum.presentation.migrate_kotlin.ui.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [BaseNavigationModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

//    fun inject(fragment: SampleFragment)
//
//    fun inject(activity: BottomNavigationActivity)
//
//    fun inject(fragment: TabContainerFragment)
//
//    fun inject(fragment: ProfileFragment)
//
//    fun inject(fragment: SelectPhotoFragment)
//
//    fun inject(activity: ProfileActivity)
}