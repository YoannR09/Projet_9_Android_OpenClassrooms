package com.openclassrooms.realestatemanager


import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.realestatemanager.presentation.home.HomeActivity
import com.openclassrooms.realestatemanager.utils.Utils
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test


class HomeActivityTest {

    var activity: HomeActivity? = null


    @get:Rule
    var rule: ActivityTestRule<HomeActivity> = ActivityTestRule(
        HomeActivity::class.java,
        true, true
    )

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun initMainActivity() {
        activity = rule.activity
        assertNotNull(activity)
        assertTrue(FirebaseAuth.getInstance().currentUser?.email == "roche_yoann@outlook.fr")
    }

    @Test
    fun checkViewFragmentState() {
        onView(withId(R.id.classic_list_view)).check(matches(isDisplayed()))
    }

    @Test
    fun switchToMapFragment() {
        onView(withId(R.id.map_button)).perform(click());
        onView(withId(R.id.classic_list_view)).check(matches(not(isDisplayed())))
    }

    @Test
    fun switchToMapFragmentThenReturnToList() {
        onView(withId(R.id.map_button)).perform(click())
        onView(withId(R.id.classic_list_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.list_button)).perform(click())
        onView(withId(R.id.classic_list_view)).check(matches(isDisplayed()))
    }

    @Test
    fun filterModal() {
        onView(withId(R.id.filter_button)).perform(click())
        onView(withId(R.id.filter_top_bar)).check(matches(isDisplayed()))
    }

    init {
        rule.launchActivity(Intent())
        FirebaseAuth.getInstance().signInWithEmailAndPassword("roche_yoann@outlook.fr", "azerty")
    }
}