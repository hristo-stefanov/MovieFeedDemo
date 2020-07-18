package hristostefanov.moviefeeddemo

import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.rxjava2.RxPagingSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PagingSource @Inject constructor(val service: Service): RxPagingSource<Int, Result>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Result>> {
        val nextPageNumber = params.key ?: 1

        // TODO pass the API_KEY
        return service.getMoveUpcoming(BuildConfig.API_KEY, nextPageNumber)
            .subscribeOn(Schedulers.io())
                // TODO for debugging
            .doOnError {
                it.printStackTrace()
            }
            .map {response ->
                LoadResult.Page(response.results, prevKey = null, nextKey = response.page + 1)
            }
        // TODO

//            .onErrorReturn {
//                LoadResult.Error(it)
//            }
    }
}