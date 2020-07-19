package hristostefanov.moviefeeddemo.domain

import androidx.paging.PagingSource
import hristostefanov.moviefeeddemo.any
import hristostefanov.moviefeeddemo.domain.api.Response
import hristostefanov.moviefeeddemo.domain.api.Service
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.*
import org.mockito.Mockito.mock

class UpcomingMoviesPagingSourceTest {
    // test data
    private val imageBaseURL = "http://base"
    private val apiKey = "apiKey"
    private val params = PagingSource.LoadParams.Refresh(1, 10, false, 10)

    private val service = mock(Service::class.java)
    private val unit = UpcomingMoviesPagingSource(service, apiKey, imageBaseURL)
    private val responseSingle = Single.just(Response(1, listOf(), 1,1))

    @Test
    fun `should query service as requested`() = runBlocking {
        given(service.getMoveUpcoming(any(), ArgumentMatchers.anyInt())).willReturn(responseSingle)

        unit.load(params)

        then(service).should().getMoveUpcoming(apiKey, 1)
        Unit
    }
}