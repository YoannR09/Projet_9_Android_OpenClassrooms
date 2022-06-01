package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import androidx.annotation.DrawableRes

data class PropertyOnMapFragmentViewModel(
    @DrawableRes val marker: Int,
    val id: String,
    val lat: Double,
    val lng: Double
)