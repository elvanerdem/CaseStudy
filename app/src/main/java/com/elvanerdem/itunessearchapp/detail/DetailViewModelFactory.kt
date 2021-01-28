package com.elvanerdem.itunessearchapp.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elvanerdem.itunessearchapp.network.SearchResultProperty

/**
 * Simple ViewModel factory that provides the SearchResultProperty and context to the ViewModel.
 */
class DetailViewModelFactory(private val searchResultProperty: SearchResultProperty, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(searchResultProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
