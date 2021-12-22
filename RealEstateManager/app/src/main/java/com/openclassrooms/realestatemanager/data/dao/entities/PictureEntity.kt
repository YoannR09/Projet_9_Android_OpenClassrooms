package com.openclassrooms.realestatemanager.data.dao.entities

import androidx.room.Entity

@Entity(tableName = "picture")
data class PictureEntity(
        val url: String,
        val description: String,
        val name: String
)