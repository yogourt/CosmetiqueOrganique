package com.blogspot.android_czy_java.beautytips.listView.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.blogspot.android_czy_java.beautytips.R
import org.junit.Rule

import org.junit.Assert.*
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun activityOpens_orderPopularAndOrderNewButtonsAreVisible() {

        //given

        //when

        //then
        onView(withId(R.id.switch_popular)).check(matches(isDisplayed()))
        onView(withId(R.id.switch_new)).check(matches(isDisplayed()))

    }

}