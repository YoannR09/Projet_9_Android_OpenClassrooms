package com.openclassrooms.realestatemanager.domain.providers

import android.content.ClipData.Item
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase


class PropertyContentProvider: ContentProvider() {

    private val AUTHORITY= "com.openclassrooms.realestatemanager.domain.providers"

    private val TABLE_NAME = Item::class.java.simpleName

    val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor {
        if (context != null) {
            val cursor: Cursor = RealEstateManagerDatabase.instance.propertiesDaoRoom()!!.listCursor()
            cursor.setNotificationUri(context!!.contentResolver, p0)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri $p0")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
}