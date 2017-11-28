package com.cleganeBowl2k18.trebuchet.presenter.home

import android.content.Intent
import android.test.ActivityInstrumentationTestCase2
import com.cleganeBowl2k18.trebuchet.presentation.view.activity.HomeActivity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

class HomeActivityTest : ActivityInstrumentationTestCase2<HomeActivity>(HomeActivity::class.java) {

    private lateinit var mHomeActivity: HomeActivity

    override fun setUp() {
        super.setUp()
        setActivityIntent(createTargetIntent())
        mHomeActivity = activity
    }

    @Throws(Exception::class)
    override fun tearDown() {
        super.tearDown()
    }

    fun testContainsProperTitle() {
        val actualTitle = mHomeActivity.title.toString().trim()

        assertThat(actualTitle, `is`("Pet Store"))
    }

    private fun createTargetIntent(): Intent {
        return HomeActivity.getIntent(instrumentation.targetContext)
    }
}
