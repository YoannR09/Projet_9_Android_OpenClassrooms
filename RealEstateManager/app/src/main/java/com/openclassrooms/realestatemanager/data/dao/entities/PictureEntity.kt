package com.openclassrooms.realestatemanager.data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureEntity(
    @PrimaryKey val id: String,
    val url: String,
    val description: String,
    val name: String
)