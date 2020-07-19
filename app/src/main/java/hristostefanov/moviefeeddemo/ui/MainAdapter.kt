package hristostefanov.moviefeeddemo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import hristostefanov.moviefeeddemo.R
import hristostefanov.moviefeeddemo.Result
import kotlinx.android.synthetic.main.movie_item.view.*

// TODO
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w300/"

class MainAdapter(
    private val glide: RequestManager,
    diffCallback: DiffUtil.ItemCallback<Result>
) : PagingDataAdapter<Result, MainAdapter.VH>(
    diffCallback

) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: Result? = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return VH(itemView)
    }

    inner class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.textView
        private val imageView = itemView.imageView
        fun bind(item: Result?) {
            textView.text = item?.title ?: "Loading..."
            val posterPath = item?.posterPath
            if (posterPath == null) {
                glide.clear(imageView)
            } else {
                glide.load(IMAGE_BASE_URL + posterPath).into(imageView)
            }
        }
    }
}

