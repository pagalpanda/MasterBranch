package common.barter.com.barterapp;

import android.support.v4.app.Fragment;

import common.barter.com.barterapp.globalhome.GlobalHome;

/**
 * Created by Panda on 19-05-2016.
 */
public abstract class AbstractFragment extends Fragment{

    public void setActionBarTitle(String headerTest) {
        ((GlobalHome)getActivity()).setActionBarTitle(headerTest);
    }

    public void setHamburgerIndicator(boolean enable) {
        ((GlobalHome)getActivity()).getmDrawerToggle().setDrawerIndicatorEnabled(enable);
    }

    public void hideKeybaord() {
        CommonResources.hideKeyboard(getActivity());
    }

    public abstract void navigate();
}
