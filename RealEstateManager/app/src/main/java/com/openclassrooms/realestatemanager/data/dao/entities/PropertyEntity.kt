package com.openclassrooms.realestatemanager.data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseUser

@Entity
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val type: String,
    val price: Int,
    val meter: Int,
    val pieces: Int,
    val description: String,
    val picturesList: ArrayList<PictureEntity>,
    val address: String,
    val interestPoints: ArrayList<String>,
    val state: String,
    val createDate: String,
    val soldDate: String,
    val agent: FirebaseUser
)


