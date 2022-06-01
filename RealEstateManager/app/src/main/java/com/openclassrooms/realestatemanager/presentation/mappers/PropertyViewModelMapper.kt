package com.openclassrooms.realestatemanager.presentation.mappers

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.domain.models.PictureModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.presentation.fragments.property.PropertyOnPropertyFragmentViewModel
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.PropertyOnMapFragmentViewModel
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

fun PropertyModel.asPropertyOnMapViewModel(context: Context): PropertyOnMapFragmentViewModel {
    val geoCoder = Geocoder(context)
    var address: List<Address> = try {
        geoCoder.getFromLocationName(address, 1)
    }catch (e: Exception) {
        listOf()
    }
    var latitude = 48.858235
    var longitude = 2.294571
    if(address.isNotEmpty()) {
        latitude = address[0].latitude;
        longitude = address[0].longitude;
    }
    return PropertyOnMapFragmentViewModel(
        marker = R.drawable.house_icon,
        id = id,
        lat = latitude,
        lng = longitude
    )
}



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