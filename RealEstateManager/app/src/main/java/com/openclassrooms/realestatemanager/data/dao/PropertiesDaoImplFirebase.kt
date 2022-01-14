package com.openclassrooms.realestatemanager.data.dao

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.utils.ErrorState.Companion.errorState
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PropertiesDaoImplFirebase : PropertiesDao {

    private val errorOnFirebase = "Error... Sorry, retry later"
    private val db = Firebase.firestore

    override suspend fun list(): List<PropertyEntity> {
        return suspendCoroutine {
            db.collection("property")
                .get()
                .addOnCompleteListener {
                        task ->
                    if(task.isSuccessful) {
                        it.resume(listOf())
                    } else {
                        it.resume(listOf())
                    }
                }
                .addOnFailureListener {
                    errorState.value = errorOnFirebase
                }
        }
    }

    override suspend fun createProperty(entity: PropertyEntity?) {
        return suspendCoroutine {
            db.collection("property")
                .add(entity!!)
                .addOnCompleteListener {
                        result ->
                    if(result.isSuccessful) {
                        it.resume(Unit)
                    } else {
                        it.resume(Unit)
                    }
                }
                .addOnFailureListener {
                    errorState.value = errorOnFirebase
                }
        }
    }

    /**
     * Shouldn't be impl on this project
     */
    override fun deleteProperty(id: String?) {
        TODO("Not yet implemented")
    }

}