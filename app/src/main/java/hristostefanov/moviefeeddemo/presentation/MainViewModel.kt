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

class MainViewModel @Inject constructor(pagingSource: PagingSource): ViewModel() {
    private val pager: Pager<Int, Result> = Pager<Int, Result>(PagingConfig(pageSize = 20)) {
        pagingSource
    }

    val observable: Observable<PagingData<Result>> = pager.observable.cachedIn(viewModelScope)

}