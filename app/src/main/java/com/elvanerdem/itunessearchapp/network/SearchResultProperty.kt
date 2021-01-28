package com.elvanerdem.itunessearchapp.network

import android.os.Parcelable
import com.elvanerdem.itunessearchapp.utils.AppUtils
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@Parcelize
data class SearchResultProperty(
    val trackId: String,
    @Json(name = "artworkUrl100")
    val imgSrcUrl: String,
    val collectionName: String = "",
    val collectionPrice: Double = 0.0,
    val releaseDate: String = "",
    val currency: String = "",
    val wrapperType: String = "",
    val artistName: String = "",
    val trackName: String = "",
    val kind: String = "",
    val description: String = "",
    val longDescription: String = ""


) : Parcelable {
    val price get() = if (collectionPrice == 0.0)  "Free" else  collectionPrice.toString().plus(" $currency")
    val formattedReleaseDate get() = AppUtils.getFormattedDate(releaseDate)

}
