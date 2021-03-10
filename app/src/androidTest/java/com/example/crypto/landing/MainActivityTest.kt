package com.example.crypto.landing

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.crypto.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun endToEndAppJourneyTest() {
        Thread.sleep(3000)
        onView(allOf(withId(R.id.textViewSort), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.textViewReset), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.textViewFilter), isDisplayed())).check(matches(isDisplayed()))

        val recyclerView = onView(
                allOf(withId(R.id.currencyRv),
                        childAtPosition(
                                withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        onView(allOf(withId(R.id.ivIcon), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.tvDetail), isDisplayed())).check(matches(isDisplayed()))

        val appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                2),
                        isDisplayed()))
        appCompatImageButton.perform(click())

        onView(allOf(withId(R.id.textViewSort), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.textViewReset), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.textViewFilter), isDisplayed())).perform(click())

    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
