package com.openclassrooms.realestatemanager.presentation.create.uiModels

import com.openclassrooms.realestatemanager.utils.InterestPoint

enum class PropertyLocationTypeUiModel(val title: String) {
    SCHOOL("School"),
    PARK("Park"),
    SHOP("Shop"),
    TRANSPORT("Transport")
}

fun InterestPoint.toUIModel() =
    when(this){
        InterestPoint.SCHOOL -> PropertyLocationTypeUiModel.SCHOOL
        InterestPoint.PARK -> PropertyLocationTypeUiModel.PARK
        InterestPoint.SHOP -> PropertyLocationTypeUiModel.SHOP
        InterestPoint.TRANSPORT -> PropertyLocationTypeUiModel.TRANSPORT
    }

fun PropertyLocationTypeUiModel.toModel() =
    when(this){
        PropertyLocationTypeUiModel.SCHOOL -> InterestPoint.SCHOOL
        PropertyLocationTypeUiModel.PARK -> InterestPoint.PARK
        PropertyLocationTypeUiModel.SHOP -> InterestPoint.SHOP
        PropertyLocationTypeUiModel.TRANSPORT -> InterestPoint.TRANSPORT
    }