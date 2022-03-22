package com.openclassrooms.realestatemanager.presentation.mappers

import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.presentation.fragments.property.PropertyOnPropertyFragmentViewModel
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.PropertyOnPropertyListFragmentViewModel

fun PropertyModel.asPropertyListViewModel() =
    PropertyOnPropertyListFragmentViewModel(
        id = id,
        name = type,
        city = address,
        price = price.toString(),
        mainPictureUrl = propertyPicturesEmpty(picturesList),
        size = meter
    )

fun PropertyModel.asPropertyViewModel() =
    PropertyOnPropertyFragmentViewModel(
        id = id,
        city = address,
        price = price.toString(),
        description = description,
        creator = agentId,
        createdDate = ", on $createDate",
        state = state,
        pictureList = picturesList
    )

fun propertyPicturesEmpty(picturesList: List<PictureEntity>): String {
    return if(picturesList.isEmpty()) {
        "none"
    } else {
        picturesList[0].url
    }
}