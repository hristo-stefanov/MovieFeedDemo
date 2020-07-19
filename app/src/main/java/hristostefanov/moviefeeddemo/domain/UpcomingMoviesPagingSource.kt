package hristostefanov.moviefeeddemo.domain

import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.rxjava2.RxPagingSource
import hristostefanov.moviefeeddemo.domain.api.Service
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class UpcomingMoviesPagingSource @Inject constructor(
    private val service: Service,
    @Named("apiKey") private val apiKey: String,
    @Named("imageBaseURL") private val imageBaseURL: String
) : RxPagingSource<Int, Movie>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val nextPageNumber = params.key ?: 1

        return service.getMoveUpcoming(apiKey, nextPageNumber)
            .subscribeOn(Schedulers.io())
            .map { response ->
                val movies = response.results.map {
                    Movie(
                        it.id,
                        it.title,
                        imageBaseURL + it.posterPath
                    )
                }
                LoadResult.Page(movies, prevKey = null, nextKey = response.page + 1)
                        as LoadResult<Int, Movie> // helps .onErrorReturn type inference
            }
            .onErrorReturn {
                Error(it)
            }
    }
}

data class Movie(
    val id: Int,
    val title: String,
    val imageURL: String?
)