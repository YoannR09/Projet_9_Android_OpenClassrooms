package com.openclassrooms.realestatemanager.presentation.fragments.propertyList

import java.io.Serializable


data class PropertyOnPropertyListFragmentViewModel(
    val id: String,
    val name: String,
    val city: String,
    val price: String,
    val mainPictureUrl: String,
    val size: Int,
    val isSelected: Boolean,
    val state: Boolean): Serializable