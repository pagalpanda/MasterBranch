package common.barter.com.barterapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import common.barter.com.barterapp.PostsOfferFragment;

public class MakeOfferAdapter extends FragmentPagerAdapter {
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



}