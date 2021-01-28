package com.elvanerdem.itunessearchapp.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elvanerdem.itunessearchapp.network.ITuneSearchApi
import com.elvanerdem.itunessearchapp.network.SearchFilter
import com.elvanerdem.itunessearchapp.network.SearchResultProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [SearchFragment].
 */

enum class ITunesApiStatus { LOADING, EMPTY, ERROR, DONE }

class SearchViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<ITunesApiStatus>()
    val status: LiveData<ITunesApiStatus>
        get() = _status


    val properties = MutableLiveData<List<SearchResultProperty>>()

    private val _navigateToSelectedProperty = MutableLiveData<SearchResultProperty>()

    val navigateToSelectedProperty: LiveData<SearchResultProperty>
        get() = _navigateToSelectedProperty


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call getResultList() on init so we can display status immediately.
     */
    init {
        getResultList("", SearchFilter.MOVIES, 1)
    }

    /**
     * Sets the value of the status LiveData to the ITunes API status.
     */
    private fun getResultList(term: String, filter: SearchFilter, count: Int) {

        Log.d("HTTP ", count.toString() + " " + term + " " + filter)

        coroutineScope.launch {
            var getPropertiesDeferred =
                ITuneSearchApi.RETROFIT_SERVICE.getResultList(term, filter.value, 20 * count)

            try {
                _status.value = ITunesApiStatus.LOADING
                var result = getPropertiesDeferred.await()
                var listResult = result.results
                _status.value = ITunesApiStatus.DONE
                if (listResult.isNotEmpty()) {
                    properties.value = listResult
                } else {
                    _status.value = ITunesApiStatus.EMPTY
                    properties.value = ArrayList()
                }
            } catch (t: Throwable) {
                _status.value = ITunesApiStatus.ERROR
                properties.value = ArrayList()
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayPropertyDetails(searchResultProperty: SearchResultProperty) {
        _navigateToSelectedProperty.value = searchResultProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    fun updateFilter(term: String, filter: SearchFilter, count: Int) {
        if (count <= 10) {
            getResultList(term, filter, count)
        }
    }
}
