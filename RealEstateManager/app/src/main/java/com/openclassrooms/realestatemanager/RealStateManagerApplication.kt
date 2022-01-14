package com.openclassrooms.realestatemanager

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.openclassrooms.realestatemanager.data.database.RealEstateManagerDatabase

class RealStateManagerApplication : Application() {

    companion object {
        lateinit var context: Application
        val contextApp: Context get() = context
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        RealEstateManagerDatabase.createInstance(this)
        context = this
    }


}