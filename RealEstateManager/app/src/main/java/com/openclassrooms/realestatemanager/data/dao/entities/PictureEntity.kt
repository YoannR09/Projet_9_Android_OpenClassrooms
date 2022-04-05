package com.openclassrooms.realestatemanager.data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureEntity(
    @PrimaryKey val id: String = "id",
    val url: String = "url",
    val description: String = "descr",
    val name: String = "name"
)