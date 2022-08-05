package com.openclassrooms.realestatemanager.domain.mappers

import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.presentation.create.uiModels.PropertyLocationTypeUiModel
import com.openclassrooms.realestatemanager.presentation.create.uiModels.toModel

fun PropertyEntity.asModel() =
        PropertyModel(
                id = id,
                type = type,
                price = price,
                meter = meter,
                pieces = pieces,
                description = description,
                picturesList = picturesList.map { it.asModel() },
                address = address,
                interestPoints = interestPoints.map { string -> PropertyLocationTypeUiModel.values().first { it.title == string }.toModel() },
                state = state,
                createDate = createDate,
                soldDate = soldDate,
                agentId = agentId,
                lat = lat,
                lng = lng
        )

