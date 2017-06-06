package com.paradigmadigital.karchitect.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import com.paradigmadigital.karchitect.ui.SimpleIdlingResource
import com.paradigmadigital.karchitect.ui.detail.DetailActivity
import org.hamcrest.Matchers.allOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private val idlingRes = SimpleIdlingResource()

    @Before
    fun idlingResourceSetup() {
        Espresso.registerIdlingResources(idlingRes)
        idlingRes.setIdleNow(false)

        val viewModel = ViewModelProviders.of(activityTestRule.getActivity()).get(MainViewModel::class.java)

        viewModel.channels.observeForever(object : Observer<List<ChannelUiModel>> {
            override fun onChanged(channels: List<ChannelUiModel>?) {
                if (channels != null) {
                    idlingRes.setIdleNow(true)
                }
            }
        })
    }

    @Test
    @Throws(Exception::class)
    fun useAppContext() {

        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("com.paradigmadigital.karchitect", appContext.packageName)
    }

    @Test
    fun startDetailActivityOnChannelClick() {
        activityTestRule.activity
        Intents.init()

        onView(withId(R.id.main_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Intents.intended(hasComponent(DetailActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun fabDialogOpensAndCloses() {
        val floatingActionButton = onView(withId(R.id.fab))

        floatingActionButton.check(matches(isDisplayed()))
        floatingActionButton.perform(click())

        onView(withId(R.id.customPanel)).check(matches(isDisplayed()))

        val appCompatButton = onView(allOf<View>(withId(android.R.id.button2), withText("CANCEL")))
        appCompatButton.perform(scrollTo(), click())

        onView(withId(R.id.customPanel)).check(doesNotExist())
    }
}
