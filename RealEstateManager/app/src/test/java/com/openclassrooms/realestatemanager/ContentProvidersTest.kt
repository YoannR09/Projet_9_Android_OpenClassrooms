package com.openclassrooms.realestatemanager

import android.content.ClipData
import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase_Impl
import com.openclassrooms.realestatemanager.domain.providers.PropertyContentProvider
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class ContentProvidersTest {

    private val AUTHORITY= "com.openclassrooms.realestatemanager.domain.providers"

    private val TABLE_NAME = ClipData.Item::class.java.simpleName

    val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

    private var mContentResolver: ContentResolver? = null

    // DATA SET FOR TEST

    // DATA SET FOR TEST
    private val USER_ID: Long = 1

    @Before
    fun setUp() {
        Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            RealEstateManagerDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        mContentResolver = InstrumentationRegistry.getInstrumentation().context
            .contentResolver
    }

    @Test
    fun getItemsWhenNoItemInserted() {
        val cursor: Cursor? = mContentResolver!!.query(
            ContentUris.withAppendedId(
                URI_ITEM,
                0
            ), null, null, null, null
        )
        assertThat(cursor, nullValue())
        cursor?.close()
    }

}