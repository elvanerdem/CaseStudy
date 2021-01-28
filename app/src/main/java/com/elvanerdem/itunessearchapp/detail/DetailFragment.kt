package com.elvanerdem.itunessearchapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elvanerdem.itunessearchapp.databinding.FragmentDetailBinding

/**
 * This [Fragment] will show the detailed information about a selected list item.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val searchResultProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = DetailViewModelFactory(searchResultProperty, application)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        return binding.root
    }

}
