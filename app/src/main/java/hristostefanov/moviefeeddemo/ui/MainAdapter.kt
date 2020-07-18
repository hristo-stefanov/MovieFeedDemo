package hristostefanov.moviefeeddemo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hristostefanov.moviefeeddemo.Result

class MainAdapter(val diffCallback: DiffUtil.ItemCallback<Result>) : PagingDataAdapter<Result, MainAdapter.VH>(
    diffCallback

) {

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: Result? = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return VH(itemView)
    }

    class VH(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)
        fun bind(item: Result?) {
            textView.text = item?.title ?: "Loading..."
        }
    }
}

