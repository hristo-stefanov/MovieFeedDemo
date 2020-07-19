package hristostefanov.moviefeeddemo.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import hristostefanov.moviefeeddemo.App
import hristostefanov.moviefeeddemo.R
import hristostefanov.moviefeeddemo.ResultComparator
import hristostefanov.moviefeeddemo.presentation.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val composite = CompositeDisposable()
    private val mainAdapter = MainAdapter(ResultComparator)

    private val viewModel: MainViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                @Suppress("UNCHECKED_CAST")
                return (application as App).component.getMainViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }

        mainAdapter.addLoadStateListener {
            swipeRefreshLayout.isRefreshing = it.refresh is LoadState.Loading
            // TODO display error message
        }

        // TODO
        // mainAdapter.dataRefreshFlow

        swipeRefreshLayout.setOnRefreshListener {
            mainAdapter.refresh()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.observable.observeOn(AndroidSchedulers.mainThread()).subscribe {
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