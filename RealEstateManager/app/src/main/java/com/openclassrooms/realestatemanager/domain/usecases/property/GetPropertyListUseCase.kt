package com.openclassrooms.realestatemanager.domain.usecases.property

import com.openclassrooms.realestatemanager.domain.repositories.Repository
import com.openclassrooms.realestatemanager.presentation.fragments.propertyList.PropertyOnPropertyListFragmentViewModel
import com.openclassrooms.realestatemanager.presentation.mappers.asPropertyListViewModel

class GetPropertyListUseCase {
    suspend fun getList(): Result<List<PropertyOnPropertyListFragmentViewModel>> {
        val listViewModel = ArrayList<PropertyOnPropertyListFragmentViewModel>()
        Repository.getPropertyRepository()!!.getList().map {
            for(property in it) {
                listViewModel.add(property.asPropertyListViewModel())
            }
        }
        return Result.success(listViewModel)
    }
}