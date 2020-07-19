package hristostefanov.moviefeeddemo.business.interactors

import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.rxjava2.RxPagingSource
import hristostefanov.moviefeeddemo.BuildConfig
import hristostefanov.moviefeeddemo.business.entities.Movie
import hristostefanov.moviefeeddemo.business.gateways.Result
import hristostefanov.moviefeeddemo.business.gateways.Service
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// TODO
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w300/"

class UpcomingMoviesPagingSource @Inject constructor(private val service: Service) : RxPagingSource<Int, Movie>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val nextPageNumber = params.key ?: 1

        // TODO pass the API_KEY
        return service.getMoveUpcoming(BuildConfig.API_KEY, nextPageNumber)
            .subscribeOn(Schedulers.io())
            .map { response ->
                val movies = response.results.map {
                    Movie(it.id, it.title, IMAGE_BASE_URL + it.posterPath)
                }
                LoadResult.Page(movies, prevKey = null, nextKey = response.page + 1)
                        as LoadResult<Int, Movie> // helps .onErrorReturn type inference
            }
            .onErrorReturn {
                Error(it)
            }
    }
}