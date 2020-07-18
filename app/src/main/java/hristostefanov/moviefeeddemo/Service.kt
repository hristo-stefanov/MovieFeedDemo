package hristostefanov.moviefeeddemo

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName
import rx.Single

interface Service {
    @GET("/movie/upcoming")
    fun getMoveUpcoming(@Query("api_key") apiKey: String): Single<Response>
}