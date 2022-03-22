package com.openclassrooms.realestatemanager.domain.mappers

import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.models.PropertyModel

fun PropertyEntity.asModel() =
        PropertyModel(
                id,
                type,
                price,
                meter,
                pieces,
                description,
                picturesList,
                address,
                interestPoints,
                state,
                createDate,
                soldDate,
                agentId
        )

