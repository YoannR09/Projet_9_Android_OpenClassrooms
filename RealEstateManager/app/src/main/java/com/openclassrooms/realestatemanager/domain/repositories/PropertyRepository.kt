package com.openclassrooms.realestatemanager.domain.repositories

import com.openclassrooms.realestatemanager.data.dao.PropertiesDao
import com.openclassrooms.realestatemanager.data.dao.PropertiesFirebaseApi
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.mappers.asModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel

class PropertyRepository(
    private val api: PropertiesFirebaseApi,
    private val dao: PropertiesDao) {

    suspend fun getList(): Result<List<PropertyModel>> {
        return try {
            Result.success(api.list().map { it.asModel() })
        } catch (e: Exception){
            try {
                Result.success(dao.list().map { it.asModel() })
            }catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getPropertyById(id: String): Result<PropertyModel> {
        return try {
            Result.success(api.getPropertyById(id).asModel())
        } catch (e: Exception){
            try {
                Result.success(dao.getPropertyById(id).asModel())
            }catch (e: Exception) {

            }
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun createProperty(propertyEntity: PropertyEntity) {
        try {
            dao.setProperty(propertyEntity)
            api.createProperty(propertyEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateStateProperty(state: String, date: String, propertyId: String) {
        return try {
           api.updateStateProperty(state, date, propertyId)
        } catch (e: Exception){
            try {
                dao.updateStateProperty(state, date, propertyId)
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun updateProperty(propertyEntity: PropertyEntity) {
        return try {
            //dao.updateProperty(propertyEntity)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}