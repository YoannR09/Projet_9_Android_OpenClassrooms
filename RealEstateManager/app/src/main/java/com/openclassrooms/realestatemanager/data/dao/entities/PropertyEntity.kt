package com.openclassrooms.realestatemanager.data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.type.LatLng

@Entity
data class PropertyEntity(
    @PrimaryKey
    val id: String = "",
    val type: String = "",
    val price: Int = 0,
    val meter: Int = 0,
    val pieces: Int = 0,
    val description: String = "",
    val picturesList: List<PictureEntity> = listOf(),
    val address: String = "",
    val interestPoints: ArrayList<String> = arrayListOf(),
    val state: String = "",
    val createDate: String= "",
    val soldDate: String= "",
    val agentId: String = "",
    val lng: Double = 0.0,
    val lat: Double = 0.0
)

