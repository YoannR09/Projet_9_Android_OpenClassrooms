package com.openclassrooms.realestatemanager.presentation.fragments.property

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.models.PropertyModel
import com.openclassrooms.realestatemanager.presentation.create.CreatePropertyActivity

class PropertyDisplayActivity : AppCompatActivity() {

    val property by lazy {
        intent.getSerializableExtra("property") as? PropertyModel
    }

    companion object {
        fun start(context: Context, property: PropertyModel? = null) {
            val intent = Intent(context, PropertyDisplayActivity::class.java)
            intent.putExtra("property", property)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_display)
    }
}