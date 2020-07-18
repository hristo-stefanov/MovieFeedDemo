package hristostefanov.moviefeeddemo.utilities.di

import dagger.Component
import hristostefanov.moviefeeddemo.presentation.MainViewModel

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun getMainViewModel(): MainViewModel
}