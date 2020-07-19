package hristostefanov.moviefeeddemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import hristostefanov.moviefeeddemo.PagingSource
import hristostefanov.moviefeeddemo.Result
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(pagingSourceProvider: Provider<PagingSource>) : ViewModel() {
    private val pagerConfig = PagingConfig(
        pageSize = 20, // suggested to the PagingSource via LoadParams
        prefetchDistance = 30 // several times the number of visible items
    )

    private val pager: Pager<Int, Result> = Pager(
        config = pagerConfig,
        pagingSourceFactory = {
            // the factory requires a new instance each time
            pagingSourceProvider.get()
        })

    val observable: Observable<PagingData<Result>> = pager.observable.cachedIn(viewModelScope)
}