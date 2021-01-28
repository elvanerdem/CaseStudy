package com.elvanerdem.itunessearchapp.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elvanerdem.itunessearchapp.R
import com.elvanerdem.itunessearchapp.adapter.PhotoGridAdapter
import com.elvanerdem.itunessearchapp.databinding.FragmentSearchBinding
import com.elvanerdem.itunessearchapp.network.SearchFilter
import com.elvanerdem.itunessearchapp.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_segmented_button.view.*


/**
 * This fragment shows the the status of the ITunes search web services transaction.
 */
class SearchFragment : Fragment() {


    private lateinit var binding: FragmentSearchBinding
    private var searchQuery = ""
    private lateinit var selectedSearchType: SearchFilter
    private lateinit var searchView: SearchView
    private lateinit var photoGridAdapter: PhotoGridAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var counter = 1

    /**
     * Lazily initialize our [SearchViewModel].
     */
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the SearchFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        selectedSearchType = SearchFilter.MOVIES

        setRecyclerViewAdapter()
        setUpSearchView()
        setUpSegmentedButton()

        navigateToDetail()

        return binding.root
    }

    private fun setRecyclerViewAdapter() {
        photoGridAdapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener { viewModel.displayPropertyDetails(it) })
        binding.rvResult.adapter = photoGridAdapter
        gridLayoutManager = binding.rvResult.layoutManager as GridLayoutManager
        viewModel.properties.observe(this, Observer { photoGridAdapter.submitList(it) })

        viewModel.status.observe(this, Observer { state ->
            progress_bar.visibility = if (state == ITunesApiStatus.LOADING) View.VISIBLE else View.GONE
        })

        binding.rvResult.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var lastPosition: Int = gridLayoutManager.findLastVisibleItemPosition()
                var listSize: Int? = viewModel.properties.value?.size

                if (listSize == (lastPosition + 1)) {
                    viewModel.updateFilter(searchQuery, selectedSearchType, counter++)
                }
            }
        })
    }

    private fun setUpSearchView() {
        searchView = binding.searchView
        val backgroundView: View = searchView.findViewById(R.id.search_plate)
        backgroundView.background = null

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 2) {
                    searchQuery = newText
                    counter = 1
                    viewModel.updateFilter(searchQuery, selectedSearchType, counter)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length > 2) {
                    searchQuery = query
                    counter = 1
                    viewModel.updateFilter(searchQuery, selectedSearchType, counter)
                }
                return false
            }

        })

        val clearButton: ImageView = searchView.findViewById(R.id.search_close_btn)
        clearButton.setOnClickListener {
            counter = 1
            if (searchView.query.isEmpty()) {
                searchView.isIconified = true
            } else {
                searchQuery = ""
                searchView.setQuery(searchQuery, false)
                viewModel.updateFilter(searchQuery, selectedSearchType, counter)
            }
        }


        binding.rlSearch.setOnClickListener(View.OnClickListener {
           AppUtils.hideSoftKeyboard(it)
        })
    }

    private fun setUpSegmentedButton() {
        binding.segmentedButton.radioGroup.setOnCheckedChangeListener { radioGroup, radioButtonID ->
            val selectedRadioButton = radioGroup.findViewById<RadioButton>(radioButtonID)
            selectedSearchType = when (selectedRadioButton.text) {
                "Movies" -> SearchFilter.MOVIES
                "Music" -> SearchFilter.MUSIC
                "Apps" -> SearchFilter.APPS
                else -> SearchFilter.BOOKS
            }
            if (searchQuery.isNotEmpty()) {
                counter = 1
                viewModel.updateFilter(searchQuery, selectedSearchType, counter)
            }
            activity?.let { it1 -> AppUtils.hideSoftKeyboard(it1) }
        }
    }

    private fun navigateToDetail() {
        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if (null != it) {
                this.findNavController().navigate(SearchFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })
    }
}

