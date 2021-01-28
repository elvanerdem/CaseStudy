package com.elvanerdem.itunessearchapp.network

data class SearchResult(
    val resultCount: Int,
    val results: List<SearchResultProperty>
)
