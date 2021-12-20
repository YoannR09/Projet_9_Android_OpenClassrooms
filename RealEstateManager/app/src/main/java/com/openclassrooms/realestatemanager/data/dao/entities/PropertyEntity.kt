package com.openclassrooms.realestatemanager.data.dao.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openclassrooms.realestatemanager.utils.Picture
import com.openclassrooms.realestatemanager.utils.PropertiesType
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "property")
data class PropertyEntity(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val type: String = PropertiesType.APARTMENT.name,
        val price: Int = 0,
        val size: Int = 0,
        val pieces: Int = 0,
        val description: String = "",
        @Embedded val pictureList: ArrayList<Picture>,
        val address: String = "")


