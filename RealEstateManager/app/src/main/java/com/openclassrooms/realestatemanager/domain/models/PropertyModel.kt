package com.openclassrooms.realestatemanager.domain.models

import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import java.io.Serializable

data class PropertyModel (
        val id: String,
        val type: String,
        val price: Int,
        val meter: Int,
        val pieces: Int,
        val description: String,
        val picturesList: List<PictureModel>,
        val address: String,
        val interestPoints: ArrayList<String>,
        val state: String,
        val createDate: String,
        val soldDate: String,
        val agentId: String
) : Serializable