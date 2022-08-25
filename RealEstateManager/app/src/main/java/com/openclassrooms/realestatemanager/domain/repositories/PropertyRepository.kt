package com.openclassrooms.realestatemanager.domain.repositories

import com.openclassrooms.realestatemanager.RealStateManagerApplication
import com.openclassrooms.realestatemanager.data.dao.PropertiesDao
import com.openclassrooms.realestatemanager.data.dao.PropertiesFirebaseApi
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.mappers.asModel
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.utils.Utils.isNetworkAvailable

class PropertyRepository(
    private val api: PropertiesFirebaseApi,
    private val dao: PropertiesDao) {

    suspend fun getList(): Result<List<PropertyModel>> {
        return try {
            if(isNetworkAvailable(RealStateManagerApplication.context)) {
                Result.success(api.list().map {
                    it.asModel()
                })
            } else {
                throw Exception()
            }
        } catch (e: Exception){
            try {
                Result.success(dao.list().map {
                    it.asModel()
                })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getPropertyById(id: String): Result<PropertyModel> {
        return try {
            if(isNetworkAvailable(RealStateManagerApplication.context)) {
                Result.success(api.getPropertyById(id).asModel())
            } else {
                throw Exception()
            }
        } catch (e: Exception){
            try {
                Result.success(dao.getPropertyById(id).asModel())
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }

    suspend fun createProperty(propertyEntity: PropertyEntity) {
        try {
            if(isNetworkAvailable(RealStateManagerApplication.context)) {
                api.createProperty(propertyEntity)
            }
            dao.setProperty(propertyEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateStateProperty(state: String, date: String, propertyId: String) {
        return try {
            if(isNetworkAvailable(RealStateManagerApplication.context)) {
                api.updateStateProperty(state, date, propertyId)
            }
            dao.updateStateProperty(state, date, propertyId)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun updateProperty(propertyEntity: PropertyEntity) {
        return try {
            if(isNetworkAvailable(RealStateManagerApplication.context)) {
                api.updateProperty(propertyEntity)
            }
            dao.setProperty(propertyEntity)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}