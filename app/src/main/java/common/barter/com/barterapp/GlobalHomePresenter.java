package common.barter.com.barterapp;

import android.view.View;

/**
 * Created by Panda on 15-05-2016.
 */
public class GlobalHomePresenter {

    GlobalHome globalHomeView;
    public GlobalHomePresenter(GlobalHome view) {
        this.globalHomeView = view;
    }

    public void onCreateView() {
        globalHomeView.initializeToolbar();
        globalHomeView.createLeftNavigationBar();
    }

    public void onLeftNavClicked(View view, int position) {
        if (position != 0) {
            globalHomeView.displayView(position - 1);
            globalHomeView.setActionBarTitleForNavBar(view);
        }else{
            globalHomeView.showLocationDialog();
        }
    }
}
