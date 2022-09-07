package com.openclassrooms.realestatemanager.data.dao

import android.content.res.Resources
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import java.lang.Error
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PropertiesFirebaseApi {

    private val db = Firebase.firestore

    suspend fun list(): List<PropertyEntity> {
        return suspendCoroutine { coroutine ->
            db.collection("property")
                .get()
                .addOnSuccessListener {
                    coroutine.resume(it?.documents?.mapNotNull {
                        it.toObject(PropertyEntity::class.java)
                    }?: listOf())
                }.addOnFailureListener { err ->
                    err.printStackTrace()
                    throw err
                }
        }
    }

    suspend fun createProperty(entity: PropertyEntity?) {
        return suspendCoroutine {
            db.collection("property")
                .add(entity!!)
                .addOnCompleteListener { result ->
                    if(result.isSuccessful) {
                        it.resume(Unit)
                    } else {
                        it.resume(Unit)
                    }
                }
                .addOnFailureListener { err ->
                    err.printStackTrace()
                }
        }
    }

    suspend fun getPropertyById(id: String): PropertyEntity {
        return suspendCoroutine { coroutine ->
            db.collection("property")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        coroutine.resume(task.result!!.documents.first().toObject(PropertyEntity::class.java)!!)
                    }else{
                        throw Resources.NotFoundException()
                    }
                }
                .addOnFailureListener { err ->
                    err.printStackTrace()
                    throw Resources.NotFoundException()
                }
        }
    }

    suspend fun updateStateProperty(state: String, date: String, propertyId: String) {
        return suspendCoroutine {
                coroutine ->
            db.collection("property").document(propertyId).update(
                "state", state,
                "soldDate", date)
                .addOnSuccessListener {
                    coroutine.resume(Unit)
                }
                .addOnFailureListener {
                        err ->
                    err.printStackTrace()
                    throw Resources.NotFoundException()
                }
        }
    }

    suspend fun updateProperty(propertyEntity: PropertyEntity) {
        return suspendCoroutine {
                coroutine ->
            val rootRef: FirebaseFirestore = FirebaseFirestore.getInstance()
            val eventsRef: CollectionReference = rootRef.collection("property")
            val docIdQuery: Query = eventsRef.whereEqualTo("id", propertyEntity.id)
            docIdQuery.get().addOnSuccessListener { task ->
                for (document in task.documents) {
                    document.reference.update("description", propertyEntity.description,
                        "interestPoints", propertyEntity.interestPoints,
                        "meter", propertyEntity.meter,
                        "picturesList", propertyEntity.picturesList,
                        "pieces", propertyEntity.pieces,
                        "price", propertyEntity.price,
                        "type", propertyEntity.type,
                        "soldDate", propertyEntity.soldDate,
                        "state", propertyEntity.state,
                        "address", propertyEntity.address)
                        .addOnSuccessListener {
                            coroutine.resume(Unit)
                        }.addOnFailureListener {
                            coroutine.resumeWithException(it)
                            it.printStackTrace()
                        }
                }
            }.addOnFailureListener {
                coroutine.resumeWithException(it)
                it.printStackTrace()
            }
        }
    }


    /**
     * Shouldn't be impl on this project
     */
    fun deleteProperty(id: String) {
        throw Error("Shouldn't be impl on this project")
    }

}