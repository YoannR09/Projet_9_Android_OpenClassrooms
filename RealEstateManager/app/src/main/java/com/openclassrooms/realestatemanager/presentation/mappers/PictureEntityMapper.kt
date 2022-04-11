package com.openclassrooms.realestatemanager.presentation.mappers

import com.openclassrooms.realestatemanager.data.dao.entities.PictureEntity
import com.openclassrooms.realestatemanager.domain.models.PictureModel

fun PictureModel.asEntity() =
    PictureEntity(
        id,
        url,
        description,
        name
    )