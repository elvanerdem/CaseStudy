package com.elvanerdem.itunessearchapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elvanerdem.itunessearchapp.databinding.GridViewItemBinding
import com.elvanerdem.itunessearchapp.network.SearchResultProperty

class PhotoGridAdapter(val onClickListener: OnClickListener) :
    ListAdapter<SearchResultProperty, PhotoGridAdapter.ITunesPropertyViewHolder>(DiffCallback) {
    class ITunesPropertyViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(searchResultProperty: SearchResultProperty) {
            binding.property = searchResultProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SearchResultProperty>() {
        override fun areItemsTheSame(
            oldItem: SearchResultProperty,
            newItem: SearchResultProperty
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SearchResultProperty,
            newItem: SearchResultProperty
        ): Boolean {
            return oldItem.trackId == newItem.trackId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ITunesPropertyViewHolder {
        return ITunesPropertyViewHolder(
            GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ITunesPropertyViewHolder, position: Int) {
        val searchProperty = getItem(position)
        holder.bind(searchProperty)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(searchProperty)
        }
    }

    class OnClickListener(val clickListener: (searchResultProperty: SearchResultProperty) -> Unit) {
        fun onClick(searchResultProperty: SearchResultProperty) =
            clickListener(searchResultProperty)
    }
}
