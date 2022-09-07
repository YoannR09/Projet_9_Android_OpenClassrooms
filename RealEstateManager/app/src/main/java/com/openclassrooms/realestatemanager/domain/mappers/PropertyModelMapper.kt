package com.openclassrooms.realestatemanager.domain.mappers

import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.presentation.mappers.toModelArray

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
                interestPoints = toModelArray(interestPoints),
                state = state,
                createDate = createDate,
                soldDate = soldDate,
                agentId = agentId,
                lat = lat,
                lng = lng
        )

