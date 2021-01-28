package com.elvanerdem.itunessearchapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.elvanerdem.itunessearchapp.R
import com.elvanerdem.itunessearchapp.network.SearchResultProperty
import com.elvanerdem.itunessearchapp.search.ITunesApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions().placeholder(R.drawable.loading_animation).error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<SearchResultProperty>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiImgStatus")
fun bindStatus(statusImageView: ImageView, status: ITunesApiStatus?) {
    when(status){
        ITunesApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ITunesApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_empty_list)
        }
        ITunesApiStatus.EMPTY -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_empty_list)
        }
        ITunesApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("apiDescStatus")
fun bindStatus(statusTextView: TextView, status: ITunesApiStatus?) {
    when(status){
        ITunesApiStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
        }
        ITunesApiStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
        }
        ITunesApiStatus.EMPTY ->
            statusTextView.visibility = View.VISIBLE
        ITunesApiStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}

@BindingAdapter("apiVisibility")
fun bindStatus(statusProgressBar: ProgressBar, status: ITunesApiStatus?) {
    when(status){
        ITunesApiStatus.LOADING -> {
            statusProgressBar.visibility = View.VISIBLE
        }
        ITunesApiStatus.ERROR, ITunesApiStatus.EMPTY, ITunesApiStatus.DONE  -> {
            statusProgressBar.visibility = View.GONE
        }
    }
}

