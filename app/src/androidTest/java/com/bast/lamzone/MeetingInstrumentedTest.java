package com.bast.lamzone;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.bast.lamzone.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule(MainActivity.class);
    private MainActivity mActivity;

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());

    }

    @Test
    public void creationReunion() {
        createReunion(2, "Bastien", "bastien@bast.com");
        onView(withId(R.id.rvList)).check(matches(hasChildCount(1)));
    }


    @Test
    public void filterReunion() {
        createReunion(1, "Bastien", "bastien@bast.com");
        createReunion(2, "Bastien", "bastien@bast.com");
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.radioRoom)).perform(click());
        onView(withId(R.id.rvList)).check(matches(hasChildCount(0)));
        onView(withId(R.id.salle1)).perform(click());
        onView(withId(R.id.rvList)).check(matches(hasChildCount(1)));
        onView(withId(R.id.salle1)).perform(click());
        onView(withId(R.id.salle2)).perform(click());
        onView(withId(R.id.rvList)).check(matches(hasChildCount(1)));
        onView(withId(R.id.salle2)).perform(click());
        onView(withId(R.id.radioDate)).perform(click());
        onView(withId(R.id.rvList)).check(matches(hasChildCount(2)));
        onView(withId(R.id.rvList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.gridNom)).check(matches(withText("Salle 1")));
    }

    @Test
    public void pageReunion() {
        createReunion(2, "basti", "bas@bas.com");
        onView(withId(R.id.rvList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void suppressionReunion() {
        createReunion(2, "basti", "bas@bas.com");
        onView(withId(R.id.rvList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteReunion()));
        onView(withId(R.id.rvList)).check(matches(hasChildCount(0)));
        createReunion(2, "basti", "bas@bas.com");
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.radioRoom)).perform(click());
        onView(withId(R.id.salle2)).perform(click());
        onView(withId(R.id.rvList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteReunion()));
        onView(withId(R.id.rvList)).check(matches(hasChildCount(0)));
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.rvList)).check(matches(hasChildCount(0)));
    }

    public void createReunion(int room, String host, String parti) {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.spinSalle)).perform(click());
        onData(anything()).inRoot(RootMatchers.isPlatformPopup()).atPosition(room).perform(click());
        onView(withId(R.id.editHost)).perform(clearText(), typeText(host), closeSoftKeyboard());
        onView(withId(R.id.editParti)).perform(clearText(), typeText(parti), closeSoftKeyboard());
        onView(withId(R.id.btnAddParti)).perform(click());
        onView(withId(R.id.btnCreate)).perform(click());
    }

}
