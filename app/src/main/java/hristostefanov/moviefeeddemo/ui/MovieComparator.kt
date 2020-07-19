package hristostefanov.moviefeeddemo.ui

import androidx.recyclerview.widget.DiffUtil
import hristostefanov.moviefeeddemo.business.entities.Movie
import hristostefanov.moviefeeddemo.business.gateways.Result

object MovieComparator: DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}