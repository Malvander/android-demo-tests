package com.mytaxi.android_demo;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ChallengeTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setActivity() {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void testClickActionBarItem() {

        onView(withId(R.id.edt_username))
                .perform(typeText("whiteelephant261"));

        onView(withId(R.id.edt_password))
                .perform(typeText("video"), closeSoftKeyboard());

        onView(withId(R.id.btn_login))
                .perform(click());

        //Almost like idling resource !.. Its for animation
        SystemClock.sleep(1000);

        onView(withId(R.id.textSearch))
                .check(matches(isDisplayed()))
                .perform(typeText("sa"));

        onView(withText("Sarah Friedrich"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.fab))
                .perform(click());

        SystemClock.sleep(2000);

    }

    @After
    public void logout() {
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class));

        onView(withContentDescription("Open navigation drawer"))
                .perform(click());
        onView(withId(R.id.nav_view))
                .perform(click());
        onView(withText("Logout"))
                .perform(click());
    }

}
