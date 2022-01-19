package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

data class PropertyOnPropertyListFragmentViewModel(
    val id: Int,
    val name: String,
    val city: String,
    val price: String,
    val mainPictureUrl: String)