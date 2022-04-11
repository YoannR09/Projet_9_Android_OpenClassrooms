package com.openclassrooms.realestatemanager.domain.models

import java.io.Serializable

class PictureModel(
    val id: String = "id",
    val url: String = "url",
    val description: String = "descr",
    val name: String = "name"
): Serializable