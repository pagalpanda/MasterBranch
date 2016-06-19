package common.barter.com.barterapp.ManageUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.manageuser.ManageUserFragment;
import common.barter.com.barterapp.manageuser.ManageUserPresenter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by vikram on 07/06/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ManageUserMockTest {
    @Mock
    GlobalHome mActivity;
    @Mock
    ManageUserFragment mFragment;
    @Mock
    LoginDetails mLoginDetails;
    ManageUserPresenter mPresenter;

    @Before
    public void setUp() {
        mActivity = mock(GlobalHome.class);
        mLoginDetails = mock(LoginDetails.class);
        mLoginDetails.setTestUserDetails();
        mFragment= mock(ManageUserFragment.class);
        mPresenter = new ManageUserPresenter();
        mPresenter.setManageUserfragment(mFragment);
    }

    @Test
    public void fragmentLoadingTests() {
        mPresenter.readAndSetUserDetails();
        assertThat(mFragment.getName(), is(LoginDetails.getInstance().getName()));
        assertThat(mFragment.getMobileNum(), is(LoginDetails.getInstance().getMobilenum()));
    }

    @Test
    public void testonMobileNumTextChanged() {
        mPresenter.onMobileNumTextChanged("7032910032");
        assertThat(mFragment.getBtVerifyVisibility(), is(0));
        mPresenter.onMobileNumTextChanged(null);
        assertThat(mFragment.getBtVerifyVisibility(), is(0));
        mPresenter.onMobileNumTextChanged("");
        assertThat(mFragment.getBtVerifyVisibility(), is(0));
        mPresenter.onMobileNumTextChanged("7032910033");
        assertThat(mFragment.getBtVerifyVisibility(), is(0));
        mPresenter.onMobileNumTextChanged("abc");
        assertThat(mFragment.getBtVerifyVisibility(), is(0));
    }

}
