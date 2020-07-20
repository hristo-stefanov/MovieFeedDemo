package hristostefanov.moviefeeddemo.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import hristostefanov.moviefeeddemo.App
import hristostefanov.moviefeeddemo.R
import hristostefanov.moviefeeddemo.presentation.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val composite = CompositeDisposable()
    private lateinit var mainAdapter: MainAdapter

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory {
            (application as App).component.getMainViewModel()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainAdapter = UIUnitTestRegistry.mainAdapter ?: (application as App).component.getMainAdapter()
        mainAdapter.init(Glide.with(this@MainActivity))

        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }

        mainAdapter.addLoadStateListener { loadStates ->
            // NOTE: the state properties #refresh #append and #preprend are combined for #source and #mediator

            // #refresh is Loading when loading the first page, not only for forced refresh
            // with adapter.refresh()
            // We should consider also #preprend and #append states but there is an issue
            // makes #append stick to Loading when reaching the last page
            val isLoading = loadStates.refresh is LoadState.Loading

            val listOfStates = listOf(loadStates.refresh, loadStates.prepend, loadStates.append)

            // the order in the list matter for errors, because an error can arise on one more states
            // usually the first (on #refresh) is the most descriptive
            val errorState = listOfStates.find { it is LoadState.Error } as? LoadState.Error

            swipeRefreshLayout.isRefreshing = isLoading
            errorMessageTextView.visibility = if (errorState != null) View.VISIBLE else View.GONE
            errorMessageTextView.text = errorState?.error?.localizedMessage ?: ""
        }

        swipeRefreshLayout.setOnRefreshListener {
            mainAdapter.refresh()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.movies.observeOn(AndroidSchedulers.mainThread()).subscribe {
            mainAdapter.submitData(lifecycle, it)
        }.also {
            composite.add(it)
        }
    }

    override fun onStop() {
        // not dispose() - will be reused
        composite.clear()
        super.onStop()
    }
}