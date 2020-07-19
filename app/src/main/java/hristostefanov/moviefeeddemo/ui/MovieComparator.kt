package hristostefanov.moviefeeddemo.ui

import androidx.recyclerview.widget.DiffUtil
import hristostefanov.moviefeeddemo.domain.Movie

object MovieComparator: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}