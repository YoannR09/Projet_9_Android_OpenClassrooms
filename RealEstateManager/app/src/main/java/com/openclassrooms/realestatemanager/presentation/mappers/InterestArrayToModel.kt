package com.openclassrooms.realestatemanager.presentation.mappers

import com.openclassrooms.realestatemanager.utils.InterestPoint

fun toModelArray(list: ArrayList<String>): List<InterestPoint> {
    return list.map {
        stringToModel ->
        InterestPoint.valueOf(stringToModel)
    }
}