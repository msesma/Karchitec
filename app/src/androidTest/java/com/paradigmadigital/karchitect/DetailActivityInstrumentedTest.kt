package com.paradigmadigital.karchitect

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.Toolbar
import com.paradigmadigital.karchitect.ui.detail.DetailActivity
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class DetailActivityInstrumentedTest {

    @get:Rule
    public var activityTestRule: ActivityTestRule<DetailActivity> = object : ActivityTestRule<DetailActivity>(DetailActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            val result = Intent(targetContext, DetailActivity::class.java)
            val forecastItem = ForecastItem(
                    time = Date(1341338400000),
                    condition = "Clear",
                    feelslike = 19f,
                    humidity = 65f,
                    rainProbability = 0f,
                    rainQuantity = 0f,
                    snow = 0f,
                    temp = 19f,
                    windSpeed = 8f,
                    iconUrl = "http://icons-ak.wxug.com/i/c/k/clear.gif"
            )
            result.putExtra(targetContext.getString(R.string.item_key), forecastItem)
            return result
        }
    }
    @Test
    fun showTimeOnToolbar() {
        activityTestRule.getActivity();

        val toolbarTitle: CharSequence = "20:00"
        Espresso.onView(isAssignableFrom(Toolbar::class.java))
                .check(matches(withToolbarTitle(`is`(toolbarTitle))))
    }

    @Test
    fun showDetailData() {
        activityTestRule.getActivity();

        Espresso.onView(withId(R.id.condition))
                .check(matches(withText("Clear")))
        Espresso.onView(withId(R.id.temp))
                .check(matches(withText("19,0 ºC")))
        Espresso.onView(withId(R.id.feelslike))
                .check(matches(withText("Feels like 19,0 ºC")))
        Espresso.onView(withId(R.id.rain))
                .check(matches(withText("Rain probability: 0,0%, 0,0mm")))
        Espresso.onView(withId(R.id.humidity))
                .check(matches(withText("Air humidity: 65,0%")))
        Espresso.onView(withId(R.id.snow))
                .check(matches(withText("Snow: 0,0mm")))
        Espresso.onView(withId(R.id.wind))
                .check(matches(withText("Wind: 8,0 Km/h")))
    }

    private fun withToolbarTitle(textMatcher: Matcher<CharSequence>): Matcher<Any> {
        return object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java) {
            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("with toolbar title: ")
                textMatcher.describeTo(description)
            }

            override fun matchesSafely(toolbar: Toolbar): Boolean {
                return textMatcher.matches(toolbar.title)
            }
        }
    }
}
