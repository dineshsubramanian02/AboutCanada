package com.aboutcanada.view.activity;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Dinesh on 25/06/18.
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, false);
    private MockWebServer server;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        server.url("/").toString();
    }

    @Test
    public void testListIsShown() throws Exception {
        String fileName = "success_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RetrofitServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withText("Beavers")).check(matches(isDisplayed()));
    }

    @Test
    public void testRetryButtonShowsWhenError() throws Exception {
        String fileName = "error_response.json";

        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(RetrofitServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withText("We can't find the page you're looking for")).check(matches(isDisplayed()));
    }

    @Test
    public void testRefreshButtonIsShown() throws Exception {
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.refresh)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void testListViewIsShown() throws Exception {
        String fileName = "success_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RetrofitServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.news_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void testListViewItemClickRow() throws Exception {
        String fileName = "success_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RetrofitServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));
        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.news_view)).check(matches(hasDescendant(withText("Beavers"))));
        onView(withId(R.id.news_view)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText("Beavers")), click()));
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}
