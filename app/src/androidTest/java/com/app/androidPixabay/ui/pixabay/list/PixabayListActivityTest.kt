package com.app.androidPixabay.ui.pixabay.list

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.app.androidPixabay.R
import com.app.androidPixabay.utils.ConstantTest.Companion.TEST_TAG_VALUE
import com.app.androidPixabay.utils.ConstantTest.Companion.TEST_USER_VALUE
import com.app.androidPixabay.utils.EspressoIdlingResourceRule
import com.app.androidPixabay.utils.RecyclerViewMatcher
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class PixabayListActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<PixabayListActivity>
            = ActivityTestRule(PixabayListActivity::class.java,true,true)

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

   /* @Test
    fun a_test_isRcylvAndProgressBarVisible_onAppLaunch() {
        onView(withId(R.id.recylv_pixabay))
            .check(matches(isDisplayed()))

        onView(withId(R.id.progress_bar))
            .check(matches(CoreMatchers.not(isDisplayed())))
    }*/

    @Test
    fun checkDataInRecyclerView() {

        onView(RecyclerViewMatcher(R.id.recylv_pixabay).atPositionOnView(0, R.id.user_tv))
            .check(matches(withText(TEST_USER_VALUE)))


        onView(RecyclerViewMatcher(R.id.recylv_pixabay).atPositionOnView(0, R.id.tag_tv))
            .check(matches(withText(TEST_TAG_VALUE)))
    }

    @Test
    fun isAlertDialogVisible() {

        // Open Alert Dialog on click of list item
        onView(RecyclerViewMatcher(R.id.recylv_pixabay)
            .atPositionOnView(2, R.id.user_tv))
            .perform(click())

        onView(withId(android.R.id.button2)).perform((click()))
    }

    @Test
    fun selectRcylvItem_OpenDetailActivity() {

        // Click list item #LIST_ITEM_IN_TEST
        onView(RecyclerViewMatcher(R.id.recylv_pixabay)
            .atPositionOnView(0, R.id.user_tv))
            .perform(click())

        onView(withId(android.R.id.button1)).perform((click()))
    }
}