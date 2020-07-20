package hristostefanov.moviefeeddemo.utilities.di

import dagger.Component
import hristostefanov.moviefeeddemo.presentation.MainViewModel
import hristostefanov.moviefeeddemo.ui.MainAdapter

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun getMainViewModel(): MainViewModel
    fun getMainAdapter(): MainAdapter
}