package com.openclassrooms.realestatemanager.createProperty

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.getOrAwaitValue
import com.openclassrooms.realestatemanager.presentation.create.CreatePropertyActivityViewModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CreatePropertyActivityViewModelTest {

    private lateinit var model: CreatePropertyActivityViewModel

    private val mainThreadSurrogate = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        model = CreatePropertyActivityViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.cleanupTestCoroutines()
    }

    @Test
    fun checkStepTitle() = runBlockingTest {
        model.currentStep.value = 0
        assertTrue(model.stepperTitle.value == "")
        model.currentStep.value = 1
        assertTrue(model.stepperTitle.value == "Enter your pictures list")
        model.currentStep.value = 2
        assertTrue(model.stepperTitle.value == "Confirm your property")
        model.currentStep.value = 99
        assertTrue(model.stepperTitle.value == "")
    }

    @Test
    fun checkStepperPercent() = runBlockingTest {
        model.currentStep.value = 0
        assertTrue(model.stepperPercent.value == 0)
        model.currentStep.value = 1
        assertTrue(model.stepperPercent.value == 33)
        model.currentStep.value = 2
        assertTrue(model.stepperPercent.value == 66)
        model.currentStep.value = 3
        assertTrue(model.stepperPercent.value == 100)
    }

    @Test
    fun checkPreviousStep() = runBlockingTest {
        model.currentStep.value = 0
        assertTrue(model.previousStep.value == View.GONE)
        model.currentStep.value = 3
        assertTrue(model.previousStep.value == View.VISIBLE)
    }

    @Test
    fun checkNextStep() {
        model.currentStep.value = 4
        println("here ${model.nextStep.value}")
        assertTrue(model.nextStep.value == View.GONE)
        model.currentStep.value = 2
        assertTrue(model.nextStep.value == View.VISIBLE)
    }

    @Test
    fun checkFormGeeneralInfo() {
        model.address.value = "address"
        model.type.value = "value"
        model.description.value = "description"
        assert(model.generalInfoCheckForm.value)
        model.address.value = ""
        assertTrue(!model.generalInfoCheckForm.value)
    }

    @Test
    fun checkFormPurchaseInfo() {
        model.price.value = 50000
        model.pieces.value = 4
        model.size.value = 102
        assert(model.purchaseInfoCheckForm.value)
        model.pieces.value = 0
        assertTrue(!model.purchaseInfoCheckForm.value)
    }
}