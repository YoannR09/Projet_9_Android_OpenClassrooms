package com.openclassrooms.realestatemanager.presentation.mappers

import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.presentation.fragments.property.PropertyOnPropertyFragmentViewModel
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.PropertyOnPropertyListFragmentViewModel

fun PropertyModel.asPropertyListViewModel() =
    PropertyOnPropertyListFragmentViewModel(
        id = id,
        name = name,
        city = address,
        price = "50000",
        mainPictureUrl = "url"
    )

fun PropertyModel.asPropertyViewModel() =
    PropertyOnPropertyFragmentViewModel(
        id = id,
        name = name,
        city = address,
        price = "50000"
    )