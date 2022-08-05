package com.openclassrooms.realestatemanager.home

import com.openclassrooms.realestatemanager.presentation.create.CreatePropertyActivityViewModel
import com.openclassrooms.realestatemanager.presentation.home.HomeActivityViewModel
import com.openclassrooms.realestatemanager.utils._viewModelScope
import junit.framework.Assert
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HomeActivityViewModelTest {

    private lateinit var model: HomeActivityViewModel
    @ExperimentalCoroutinesApi
    private val mainThreadSurrogate = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)
        model = HomeActivityViewModel()
        _viewModelScope = TestScope(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        _viewModelScope = null
    }

    @ExperimentalCoroutinesApi
    @Test
    fun checkIdSelected() = runTest {
        assertTrue(model.idSelected.value === null)
        model.changeSelectId("newId")
        assertTrue(model.idSelected.value === "newId")
    }
}