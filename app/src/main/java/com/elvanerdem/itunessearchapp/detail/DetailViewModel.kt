package com.elvanerdem.itunessearchapp.detail

import android.app.Application
import androidx.lifecycle.*
import com.elvanerdem.itunessearchapp.network.SearchResultProperty

/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(@Suppress("UNUSED_PARAMETER")searchResultProperty: SearchResultProperty, app: Application) : AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<SearchResultProperty>()

    val selectedProperty: LiveData<SearchResultProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = searchResultProperty
    }

    val displayCollectionName = Transformations.map(selectedProperty) {
        app.applicationContext.getString(com.elvanerdem.itunessearchapp.R.string.collection_name, it.collectionName)
    }

    val displayTrackName = Transformations.map(selectedProperty) {
        app.applicationContext.getString(com.elvanerdem.itunessearchapp.R.string.track_name, it.trackName)
    }

    val displayArtistName = Transformations.map(selectedProperty) {
        app.applicationContext.getString(com.elvanerdem.itunessearchapp.R.string.artist_name, it.artistName)
    }

    val displayKind = Transformations.map(selectedProperty) {
        app.applicationContext.getString(com.elvanerdem.itunessearchapp.R.string.kind_name, it.kind)
    }

    val displayPrice = Transformations.map(selectedProperty) {
        app.applicationContext.getString(com.elvanerdem.itunessearchapp.R.string.price, it.price)
    }

}
