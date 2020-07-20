package hristostefanov.moviefeeddemo.utilities.di

import dagger.Module
import dagger.Provides
import hristostefanov.moviefeeddemo.BuildConfig
import hristostefanov.moviefeeddemo.domain.api.Service
import hristostefanov.moviefeeddemo.ui.MainAdapter
import hristostefanov.moviefeeddemo.ui.MovieComparator
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class ApplicationModule {
    @ApplicationScope
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideService(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }

    @Provides @Named("apiKey")
    fun provideApiKey() = BuildConfig.API_KEY

    @Provides @Named("imageBaseURL")
    fun provideImageBaseURL() = "https://image.tmdb.org/t/p/w300/"

    @Provides
    fun provideMainAdapter(): MainAdapter {
        return MainAdapter(MovieComparator)
    }
}