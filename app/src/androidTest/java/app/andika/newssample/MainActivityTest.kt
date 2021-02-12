package app.andika.newssample

import android.app.Activity
import android.view.Gravity
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import app.andika.newssample.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkFirstLoadingView() {
        onView(withText("Please wait")).check(matches(isDisplayed()));
    }

    @Test fun checkHomeIcon_OpenAndCloseNavigation() {
        Thread.sleep(5000)  //Wait news loading dialog gone
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(Gravity.START)))

        clickOnHomeIconToOpenNavigationDrawer()
        checkDrawerIsOpen()
    }

    @Test fun checkHomeIcon_checkHomePage() {
        Thread.sleep(5000)  //Wait news loading dialog gone
        clickOnHomeIconToOpenNavigationDrawer()
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_home))
        
        onView(withId(R.id.rvNews)).check(matches(isDisplayed()))
    }

    fun getToolbarNavigationContentDescription(activity: Activity, toolbarId: Int) =
        activity.findViewById<Toolbar>(toolbarId).navigationContentDescription as String

    private fun clickOnHomeIconToOpenNavigationDrawer() {
        onView(withContentDescription(getToolbarNavigationContentDescription(activityTestRule.activity, R.id.toolbar))).perform(click())
    }

    private fun checkDrawerIsOpen() {
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen(Gravity.START)))
    }

}