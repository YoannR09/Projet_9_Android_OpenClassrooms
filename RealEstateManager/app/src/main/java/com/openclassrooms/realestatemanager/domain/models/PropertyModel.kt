package com.openclassrooms.realestatemanager.domain.models

import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity

data class PropertyModel (
        val id: Int,
        val name: String,
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
        val agentId: String
)
