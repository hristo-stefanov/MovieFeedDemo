package hristostefanov.moviefeeddemo

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName

interface Service {
    @GET("movie/upcoming")
    fun getMoveUpcoming(@Query("api_key") apiKey: String, @Query("page") page: Int): Single<Response>
}