package hristostefanov.moviefeeddemo

import android.app.Application
import hristostefanov.moviefeeddemo.utilities.di.DaggerApplicationComponent

class App: Application() {
    val component = DaggerApplicationComponent.create()
}