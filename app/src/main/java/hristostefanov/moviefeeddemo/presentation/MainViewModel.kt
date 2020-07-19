package hristostefanov.moviefeeddemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import hristostefanov.moviefeeddemo.domain.Movie
import hristostefanov.moviefeeddemo.domain.UpcomingMoviesPagingSource
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(upcomingMoviesPagingSourceProvider: Provider<UpcomingMoviesPagingSource>) : ViewModel() {
    private val pagerConfig = PagingConfig(
        pageSize = 20, // suggested to the PagingSource via LoadParams
        prefetchDistance = 30 // several times the number of visible items
    )

    private val pager: Pager<Int, Movie> = Pager(
        config = pagerConfig,
        pagingSourceFactory = {
            // the factory requires a new instance each time
            upcomingMoviesPagingSourceProvider.get()
        })

    val movies: Observable<PagingData<Movie>> = pager.observable.cachedIn(viewModelScope)
}