package common.barter.com.barterapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import common.barter.com.barterapp.PostsOfferFragment;

public class ChooseOffersAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ArrayList<Offer> listOfMyOffers;
    ArrayList<Offer> listOfHisOffers;

    public ChooseOffersAdapter(FragmentManager fm, int NumOfTabs, ArrayList listOfMyOffers, ArrayList listOfHisOffers) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.listOfMyOffers = listOfMyOffers;
        this.listOfHisOffers = listOfHisOffers;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ShowOffersFragment fragment = new ShowOffersFragment("myoffers", listOfMyOffers);
                CommonResources.flowForOffers = "myoffers";
                return fragment;
            case 1:
                ShowOffersFragment fragment2 = new ShowOffersFragment("hisoffers",listOfHisOffers);
                CommonResources.flowForOffers = "hisoffers";
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

        return position == 0? "By Me":"To Me";
    }

}