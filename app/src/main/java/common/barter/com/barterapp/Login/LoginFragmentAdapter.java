package common.barter.com.barterapp.Login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



public class LoginFragmentAdapter extends FragmentStatePagerAdapter {

    public LoginFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoginFragment fragment1 = new LoginFragment(LoginFragment.tabs.LOGIN.name());
                return fragment1;
            case 1:
                LoginFragment fragment2 = new LoginFragment(LoginFragment.tabs.SIGNUP.name());
                return fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return position == 0? "LOG IN":"SIGN UP";
    }
}