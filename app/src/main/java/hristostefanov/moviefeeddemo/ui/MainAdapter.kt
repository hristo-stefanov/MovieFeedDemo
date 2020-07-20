package hristostefanov.moviefeeddemo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import hristostefanov.moviefeeddemo.R
import hristostefanov.moviefeeddemo.domain.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

open class MainAdapter(
    diffCallback: DiffUtil.ItemCallback<Movie>
) : PagingDataAdapter<Movie, MainAdapter.VH>(
    diffCallback
) {
    private lateinit var glide: RequestManager

    fun init(glide: RequestManager) {
        this.glide = glide
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: Movie? = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return VH(itemView)
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.textView
        private val imageView = itemView.imageView
        fun bind(item: Movie?) {
            textView.text = item?.title ?: "Loading..."
            val imageURL = item?.imageURL
            if (imageURL == null) {
                glide.clear(imageView)
            } else {
                glide.load(imageURL).into(imageView)
            }
        }
    }
}

