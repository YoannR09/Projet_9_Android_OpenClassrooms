package com.openclassrooms.realestatemanager.presentation.mappers

import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.domain.models.PictureModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.presentation.fragments.property.PropertyOnPropertyFragmentViewModel
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.PropertyOnPropertyListFragmentViewModel
import com.openclassrooms.realestatemanager.utils.Utils.dateToFormat
import java.util.*

fun PropertyModel.asPropertyListViewModel(
    isSelected: Boolean = false
) =
    PropertyOnPropertyListFragmentViewModel(
        id = id,
        name = type,
        city = address,
        price = price.toString(),
        mainPictureUrl = propertyPicturesEmpty(picturesList),
        size = meter,
        isSelected = isSelected,
        state = state == "AVAILABLE"
    )

fun PropertyModel.asPropertyViewModel() =
    PropertyOnPropertyFragmentViewModel(
        id = id,
        city = address,
        price = price.toString(),
        description = description,
        creator = agentId,
        createdDate = ", on ${dateToFormat(Date(createDate.toLong()))}",
        state = state,
        pictureList = picturesList,
        piecesCounter = pieces,
        size = "$meter m²"
    )

fun propertyPicturesEmpty(picturesList: List<PictureModel>): String {
    return if(picturesList.isEmpty()) {
        "none"
    } else {
        picturesList[0].url
    }
}