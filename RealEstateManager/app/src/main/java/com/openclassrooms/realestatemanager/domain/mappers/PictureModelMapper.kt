package com.openclassrooms.realestatemanager.domain.mappers

import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.domain.models.PictureModel

fun PictureEntity.asModel() =
    PictureModel(
        id = id,
        url = url,
        description = description,
        name = name
    )