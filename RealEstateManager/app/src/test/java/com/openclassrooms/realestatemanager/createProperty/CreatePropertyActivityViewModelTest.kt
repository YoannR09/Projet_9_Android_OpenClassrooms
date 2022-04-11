package com.openclassrooms.realestatemanager.createProperty

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.presentation.create.CreatePropertyActivityViewModel
import com.openclassrooms.realestatemanager.utils._viewModelScope
import com.openclassrooms.realestatemanager.utils.scope
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreatePropertyActivityViewModelTest {

    private lateinit var model: CreatePropertyActivityViewModel
    @ExperimentalCoroutinesApi
    private val mainThreadSurrogate = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)
        model = CreatePropertyActivityViewModel(null)
        _viewModelScope = TestScope(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        _viewModelScope = null
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkStepTitle() = runTest {
        model.currentStep.value = 0
        assertTrue(model.stepperTitle.value == "Enter your general information")
        model.currentStep.value = 1
        assertTrue(model.stepperTitle.value == "Enter your purchase information")
        model.currentStep.value = 2
        assertTrue(model.stepperTitle.value == "Select your picture")
        model.currentStep.value = 3
        assertTrue(model.stepperTitle.value == "Confirm your property")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkStepperPercent() = runTest {
        model.currentStep.value = 0
        assertTrue(model.stepperPercent.value == 0)
        model.currentStep.value = 1
        assertTrue(model.stepperPercent.value == 33)
        model.currentStep.value = 2
        assertTrue(model.stepperPercent.value == 66)
        model.currentStep.value = 3
        assertTrue(model.stepperPercent.value == 100)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkPreviousStep() = runTest {
        model.currentStep.value = 0
        assertTrue(model.previousStep.value == View.GONE)
        model.currentStep.value = 3
        assertTrue(model.previousStep.value == View.VISIBLE)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkNextStep() = runTest {
        model.currentStep.value = 3
        println("here ${model.nextStepIsVisible.value}")
        assertTrue(!model.nextStepIsVisible.value)
        model.currentStep.value = 2
        assertTrue(model.nextStepIsVisible.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkFormGeeneralInfo() = runTest {
        model.address.value = "address"
        model.type.value = "value"
        model.description.value = "description"
        assert(model.generalInfoCheckForm.value)
        model.address.value = ""
        assertTrue(!model.generalInfoCheckForm.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkFormPurchaseInfo() = runTest {
        model.price.value = 50000
        model.pieces.value = 4
        model.size.value = 102
        assert(model.purchaseInfoCheckForm.value)
        model.pieces.value = 0
        assertTrue(!model.purchaseInfoCheckForm.value)
    }
}