

package common.barter.com.barterapp;

        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.app.FragmentStatePagerAdapter;



public class LoginFragmentAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public LoginFragmentAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LoginFragment fragment1 = new LoginFragment(1);
                return fragment1;
            case 1:
                LoginFragment fragment2 = new LoginFragment(2);
                return fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
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