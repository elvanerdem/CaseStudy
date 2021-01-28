package com.elvanerdem.itunessearchapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://itunes.apple.com/"

enum class SearchFilter(val value: String) {
    MOVIES("movie"),
    MUSIC("music"),
    APPS("software"),
    BOOKS("ebook")
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ITuneSearchService {
    @GET("search")
    fun getResultList(
        @Query("term") term: String,
        @Query("media") media: String,
        @Query("limit") limit: Int
    ): Deferred<SearchResult>
}

object ITuneSearchApi {
    val RETROFIT_SERVICE: ITuneSearchService by lazy {
        retrofit.create(ITuneSearchService::class.java)
    }
}
