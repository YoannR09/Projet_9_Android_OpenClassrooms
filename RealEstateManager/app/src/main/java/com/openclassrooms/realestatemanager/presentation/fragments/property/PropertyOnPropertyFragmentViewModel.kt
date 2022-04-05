package com.openclassrooms.realestatemanager.presentation.fragments.property

import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity

data class PropertyOnPropertyFragmentViewModel(
    val id: String,
    val city: String,
    val price: String,
    val description: String,
    val creator: String,
    val createdDate: String,
    val state: String,
    val pictureList: List<PictureEntity>,
    val piecesCounter: Int,
    val size: String
)