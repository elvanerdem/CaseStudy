package com.elvanerdem.itunesearchapp

import com.elvanerdem.itunessearchapp.utils.AppUtils
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.runners.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `Should return formatted date when called from app utils`() {
        assertEquals("28/01/2021", AppUtils.getFormattedDate("2021-01-28T19:00:00Z"))
    }
}