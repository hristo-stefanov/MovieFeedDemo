package hristostefanov.moviefeeddemo.business.gateways

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("movie/upcoming")
    fun getMoveUpcoming(@Query("api_key") apiKey: String, @Query("page") page: Int): Single<Response>
}