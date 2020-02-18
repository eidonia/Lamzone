package com.bast.lamzone;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;
import com.bast.lamzone.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

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
    public void createReunion() {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.spinSalle)).perform(click());
        onData(anything()).inRoot(RootMatchers.isPlatformPopup()).atPosition(2).perform(click());
        onView(withId(R.id.btnCreate)).perform(click());
        onView(withId(R.id.rvList)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void filterReunion() {
        ApiServiceReu apiService = Di.getApiServiceReu();
        List<Reunion> reu = apiService.getReunion();
        ArrayList<String> listParti = new ArrayList<>();
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        Reunion reuFil = new Reunion(1, 14, 15, 15, 15, "Lundi", 15, "février", 2020, "Bast", listParti);
        Reunion reuSal = new Reunion(2, 14, 15, 15, 15, "Lundi", 17, "février", 2020, "Bast", listParti);
        Reunion reuSal2 = new Reunion(2, 14, 15, 15, 15, "Lundi", 15, "février", 2020, "Bast", listParti);
        reu.add(reuFil);
        reu.add(reuSal);
        reu.add(reuSal2);
        onView(withId(R.id.action_filter)).perform(click());
        onView(withId(R.id.radioRoom)).perform(click());
        onView(withId(R.id.rvList)).check(matches(hasMinimumChildCount(0)));
        onView(withId(R.id.salle1)).perform(click());
        onView(withId(R.id.rvList)).check(matches(hasMinimumChildCount(1)));
        onView(withId(R.id.salle1)).perform(click());
        onView(withId(R.id.salle2)).perform(click());
        onView(withId(R.id.rvList)).check(matches(hasMinimumChildCount(2)));
    }

    @Test
    public void pageReunion() {
        createReunion();
        onView(withId(R.id.rvList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

}
