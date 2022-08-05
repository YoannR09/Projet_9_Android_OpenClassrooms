package com.openclassrooms.realestatemanager.presentation.fragments.property

import com.openclassrooms.realestatemanager.domain.models.PictureModel

data class PropertyOnPropertyFragmentViewModel(
    val id: String,
    val city: String,
    val price: String,
    val description: String,
    val creator: String,
    val createdDate: String,
    val state: String,
    val pictureList: List<PictureModel>,
    val piecesCounter: Int,
    val size: String,
    val lat: Double,
    val lng: Double
)