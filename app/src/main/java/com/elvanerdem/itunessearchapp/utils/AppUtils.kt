package com.elvanerdem.itunessearchapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun getFormattedDate(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var convertedDate: Date?
        var formattedDate = ""
        try {
            convertedDate = sdf.parse(date)
            formattedDate = SimpleDateFormat("dd/MM/yyyy").format(convertedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formattedDate
    }

    fun hideSoftKeyboard(activity: Activity) {
        val viewFocused: View? = activity.currentFocus
        if (viewFocused != null) {
            hideSoftKeyboard(viewFocused)
        }
    }

    fun hideSoftKeyboard(view: View) {
        val context: Context = view.getContext()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

}