package com.paradigmadigital.paraguas

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.ui.SimpleIdlingResource
import com.paradigmadigital.karchitect.ui.detail.DetailActivity
import com.paradigmadigital.karchitect.ui.detail.DetailViewModel
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailActivityInstrumentedTest {

    @get:Rule
    public var activityTestRule: ActivityTestRule<DetailActivity> = object : ActivityTestRule<DetailActivity>(DetailActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            val result = Intent(targetContext, DetailActivity::class.java)
            val channelKey = "http://feed.test.com"
            result.putExtra(DetailActivity.CHANNEL_KEY, channelKey)
            return result
        }
    }

    private val idlingRes = SimpleIdlingResource()
    private lateinit var viewModel: DetailViewModel


    @Before
    fun idlingResourceSetup() {
        Espresso.registerIdlingResources(idlingRes)
        idlingRes.setIdleNow(false)

        viewModel = ViewModelProviders.of(activityTestRule.getActivity()).get(DetailViewModel::class.java)

        viewModel.items?.observeForever(object : Observer<List<Item>> {
            override fun onChanged(items: List<Item>?) {
                if (items != null) {
                    idlingRes.setIdleNow(true)
                }
            }
        })
    }

    @Test
    fun showDetailData() {
        activityTestRule.getActivity();

        onView(withId(R.id.detail_row))
                .check(matches(isDisplayed()))

        onView(allOf<View>(withId(R.id.title),
                withText("Item title")))
                .check(matches(isDisplayed()))

        onView(allOf<View>(withId(R.id.desc),
                withText("Item description")))
                .check(matches(isDisplayed()))
    }
}
