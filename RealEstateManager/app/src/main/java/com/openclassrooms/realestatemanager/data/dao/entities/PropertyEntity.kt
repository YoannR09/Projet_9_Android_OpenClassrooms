package com.openclassrooms.realestatemanager.data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val price: Int = 0,
    val meter: Int = 0,
    val pieces: Int = 0,
    val description: String = "",
    val picturesList: ArrayList<PictureEntity> = arrayListOf(),
    val address: String = "",
    val interestPoints: ArrayList<String> = arrayListOf(),
    val state: String = "",
    val createDate: String= "",
    val soldDate: String= "",
    val agentId: String = ""
) {
    constructor() : this(0)
}
