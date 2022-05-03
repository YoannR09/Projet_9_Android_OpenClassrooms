package com.openclassrooms.realestatemanager.domain.models

data class FilterModel (
    val type: String? = null,
    val picturesLength: String? = null,
    val size: List<Int>? = listOf(),
    val price: List<Int>? = listOf(),
    val createdDate: String? = null,
    val soldDate: String? = null,
    val interests: List<String>? = null
)

