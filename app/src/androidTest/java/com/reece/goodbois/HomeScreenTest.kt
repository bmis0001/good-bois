package com.reece.goodbois

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.reece.goodbois.adpater.SearchAdapter
import com.reece.goodbois.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_isSearchFragmentInView() {
        onView(withId(R.id.search_card)).check(matches(isDisplayed()))
        onView(withId(R.id.search_result_recycler)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectItem_DetailFragmentVisible() {
        Thread.sleep(3000)
        onView(withId(R.id.search_result_recycler))
            .perform(actionOnItemAtPosition<SearchAdapter.BreedViewHolder>(5, click()))

        onView(withId(R.id.breed_name)).check(matches(isDisplayed()))
    }
}