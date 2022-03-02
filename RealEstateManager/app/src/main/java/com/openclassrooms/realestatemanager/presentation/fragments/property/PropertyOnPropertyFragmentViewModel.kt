package com.openclassrooms.realestatemanager.presentation.fragments.property

data class PropertyOnPropertyFragmentViewModel(
    val id: String,
    val name: String,
    val city: String,
    val price: String,
    val description: String,
    val creator: String,
    val createdDate: String,
    val state: String
)