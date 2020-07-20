package hristostefanov.moviefeeddemo.presentation

import hristostefanov.moviefeeddemo.THREAD_SWITCH_TIMEOUT_MS
import hristostefanov.moviefeeddemo.domain.UpcomingMoviesPagingSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.mock
import org.mockito.Mockito.timeout
import javax.inject.Provider

class MainViewModelTest : BaseViewModelTest() {
    @Suppress("UNCHECKED_CAST")
    private val provider = mock(Provider::class.java) as Provider<UpcomingMoviesPagingSource>

    private val unit by lazy {
        MainViewModel(provider)
    }

    @Before
    fun beforeEach() = runBlocking {
        val source = mock(UpcomingMoviesPagingSource::class.java)
        given(provider.get()).willReturn(source)
        Unit
    }

    @Test
    fun `should request paging source`() = runBlocking {
        unit.movies.subscribe()

        then(provider).should(timeout(THREAD_SWITCH_TIMEOUT_MS)).get()
        Unit
    }

    @Test
    fun `should emit paging data`() {
        val testObserver = unit.movies.test()

        testObserver.awaitCount(1)
            .assertValueCount(1)
    }
}
