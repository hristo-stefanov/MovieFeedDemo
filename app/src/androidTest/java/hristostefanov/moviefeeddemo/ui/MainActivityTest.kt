package hristostefanov.moviefeeddemo.ui

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import hristostefanov.moviefeeddemo.R
import hristostefanov.moviefeeddemo.domain.Movie
import hristostefanov.moviefeeddemo.presentation.MainViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.util.concurrent.atomic.AtomicInteger

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val viewModel = mock(MainViewModel::class.java)

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, false, false)

    var subscriptionCount = AtomicInteger()

    @Before
    fun beforeEach() {
        // make the activity use the mock view model
        UIUnitTestRegistry.viewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return viewModel as T
            }
        }

        val pagingData = PagingData.empty<Movie>()
        // Note: Mockito cannot spy on Observable on Android, hence tapping into the stream
        val observable = Observable.just(pagingData).doOnSubscribe {
            subscriptionCount.incrementAndGet()
        }
        given(viewModel.movies).willReturn(observable)
    }

    @Test
    fun shouldSubscribeToViewModelObservable() {
        activityRule.launchActivity(Intent())

        assertThat(subscriptionCount.get(), equalTo(1))
    }

    @Test
    fun testTODO() {
        // TODO
        activityRule.launchActivity(Intent())
        onView(withId(R.id.swipeRefreshLayout)).perform(swipeDown())
        fail()
    }

}
