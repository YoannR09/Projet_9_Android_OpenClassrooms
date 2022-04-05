package com.openclassrooms.realestatemanager.mappers

import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.mappers.asModel
import com.openclassrooms.realestatemanager.utils.InterestPoint
import junit.framework.Assert.assertEquals
import org.junit.Test

class PropertyModelMapperTest {

    private val before = PropertyEntity(
        id="id",
        address= "ad",
        agentId="agent",
        createDate="created",
        description="desc",
        interestPoints = arrayListOf(),
        meter= 4,
        picturesList= arrayListOf(),
        pieces=3,
        soldDate="sold",
        state="state",
        type="type",
        price = 44
    )

    @Test
    fun asModel() {
        val mapped = before.asModel()
        assertEquals(mapped.address, "ad")
        assertEquals(mapped.agentId, "agent")
        assertEquals(mapped.id, "id")
        assertEquals(mapped.meter, 4)
        assertEquals(mapped.price, 44)
        assertEquals(mapped.description, "desc")
        assertEquals(mapped.state, "state")
        assertEquals(mapped.interestPoints, arrayListOf<InterestPoint>())
        assertEquals(mapped.type, "type")
    }
}