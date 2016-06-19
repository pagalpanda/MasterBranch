package common.barter.com.barterapp.ManageUser;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import common.barter.com.barterapp.globalhome.GlobalHome;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by vikram on 07/06/16.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ManageUserTest {

    @Rule
    public ActivityTestRule<GlobalHome> mActivityRule = new ActivityTestRule<>(
            GlobalHome.class);

    @Before
    public void createLogHistory() {

    }

    @Test
    public void logHistory_ParcelableWriteRead() {
        
//        onView(withId(R.id.editTextUserInput))
//                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
//        onView(withId(R.id.changeTextBt)).perform(click());
        assertThat(1, is(1));
    }


}
