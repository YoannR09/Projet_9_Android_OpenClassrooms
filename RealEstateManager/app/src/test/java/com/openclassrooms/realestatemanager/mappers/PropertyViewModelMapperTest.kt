package com.openclassrooms.realestatemanager.mappers

import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyListViewModel
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PropertyViewModelMapperTest {

    private val before = PropertyModel(
        id="id",
        address= "ad",
        agentId="agent",
        createDate="0",
        description="desc",
        interestPoints = arrayListOf(),
        meter= 4,
        picturesList= arrayListOf(),
        pieces=3,
        soldDate="sold",
        state="state",
        type="type",
        price = 44,
        lat = 11.0,
        lng = 12.2
    )

    @Test
    fun asPropertyListViewModel() {
        val mapped = before.asPropertyListViewModel()
        assertEquals(mapped.id, "id")
        assertEquals(mapped.city, "ad")
        assertEquals(mapped.mainPictureUrl, "none")
        assertEquals(mapped.name, "type")
        assertEquals(mapped.price, "44")
        assertEquals(mapped.size, 4)
    }

    @Test
    fun asPropertyViewModel() {
        val mapped = before.asPropertyViewModel()
        assertEquals(mapped.id, "id")
        assertEquals(mapped.city, "ad")
        assertEquals(mapped.creator, "agent")
        assertEquals(mapped.description, "desc")
        assertEquals(mapped.price, "44")
        assertEquals(mapped.state, "state")
        assertEquals(mapped.createdDate, ", on 01/01/1970")
    }
}