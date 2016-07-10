package common.barter.com.barterapp.makeOffer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import common.barter.com.barterapp.posts.PostsOfferFragment;

public class MakeOfferAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MakeOfferAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PostsOfferFragment fragment1 = new PostsOfferFragment("1");
                return fragment1;
            case 1:
                PostsOfferFragment fragment2 = new PostsOfferFragment("2");
                return fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return position == 0? "My Posts":"His Posts";
    }

}