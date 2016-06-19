package common.barter.com.barterapp.OTPFragment;

import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.manageuser.ManageUserFragment;
import common.barter.com.barterapp.otp.OTPFragment;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by vikram on 15/06/16.
 */
@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class TestOTPFragment {
    @Mock
    GlobalHome mActivity;
    @Mock
    OTPFragment mFragment;
    @Mock
    LoginDetails mLoginDetails;

    @Before
    public void setUp() {
        mActivity = mock(GlobalHome.class);
        mLoginDetails = mock(LoginDetails.class);
        mLoginDetails.setTestUserDetails();
        mFragment= mock(OTPFragment.class);
    }

    @Test
    public void fragmentLoadingTests() {
//        assertThat(mFragment.getName(), is(LoginDetails.getInstance().getName()));
//        assertThat(mFragment.getMobileNum(), is(LoginDetails.getInstance().getMobilenum()));
    }
}
