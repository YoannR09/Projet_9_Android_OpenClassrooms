package com.openclassrooms.realestatemanager.domain.usecases.property

import androidx.lifecycle.LiveData
import android.content.Context
import com.openclassrooms.realestatemanager.data.dao.entities.PropertyEntity
import com.openclassrooms.realestatemanager.domain.repositories.Repository
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.PropertyOnPropertyListFragmentViewModel
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyListViewModel

class GetPropertyListUseCase {
    suspend fun getList(context: Context): List<PropertyOnPropertyListFragmentViewModel> {
        val listViewModel = ArrayList<PropertyOnPropertyListFragmentViewModel>()
        for(property in Repository.getPropertyRepository(context)!!.getList()) {
            listViewModel.add(property.asPropertyListViewModel())
        }
        return listViewModel
    }
}