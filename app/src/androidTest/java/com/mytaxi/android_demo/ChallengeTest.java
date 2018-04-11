package com.mytaxi.android_demo;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class ChallengeTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule =
            new IntentsTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(ACCESS_FINE_LOCATION);

    private MainActivity mActivity = null;

    @Before
    public void setActivity() {
        mActivity = mActivityRule.getActivity();
    }

    @Test
    public void shouldLoginSearchAndCallDriver() {

        onView(withId(R.id.edt_username))
                .check(matches(isDisplayed()))
                .perform(typeText("whiteelephant261"));

        onView(withId(R.id.edt_password))
                .check(matches(isDisplayed()))
                .perform(typeText("video"));

        onView(withId(R.id.btn_login))
                .check(matches(isDisplayed()))
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
                .check(matches(isDisplayed()))
                .perform(click());

        intended(toPackage("com.google.android.dialer"));

    }

    @After
    public void logout() {
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class));
        SystemClock.sleep(2000);
        onView(withContentDescription("Open navigation drawer"))
                .perform(click());
        onView(withId(R.id.nav_view))
                .perform(click());
        onView(withText("Logout"))
                .perform(click());
    }

}
