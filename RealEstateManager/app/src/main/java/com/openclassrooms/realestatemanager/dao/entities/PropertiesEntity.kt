package com.openclassrooms.realestatemanager.dao.entities

import com.openclassrooms.realestatemanager.utils.InterestPoint
import com.openclassrooms.realestatemanager.utils.Picture
import com.openclassrooms.realestatemanager.utils.PropertiesType
import java.util.*

class PropertiesEntity(
        type: PropertiesType,
        price: Int,
        size: Int,
        pieces: Int,
        description: String,
        pictureList: Array<Picture>,
        address: String,
        interestPoints: Array<InterestPoint>,
        available: Boolean,
        createDate: Date?,
        soldDate: Date?,
        creator: AgentEntity?) {

    private val type: PropertiesType = PropertiesType.APARTMENT
    private val price: Int = 0
    private val size: Int = 0
    private val pieces: Int = 0
    private val description: String = ""
    private val pictureList: Array<Picture> = emptyArray()
    private val address: String = ""
    private val interestPoints: Array<InterestPoint> = emptyArray()
    private val available: Boolean = true
    private val createDate: Date? = null
    private val soldDate: Date? = null
    private val creator: AgentEntity? = null
}