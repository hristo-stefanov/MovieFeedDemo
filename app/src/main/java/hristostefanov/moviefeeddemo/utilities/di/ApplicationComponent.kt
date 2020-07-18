package hristostefanov.moviefeeddemo.utilities.di

import dagger.Component
import hristostefanov.moviefeeddemo.presentation.MainViewModel

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun getMainViewModel(): MainViewModel
}