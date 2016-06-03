package common.barter.com.barterapp.Home;

import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.splash.SplashModel;
import common.barter.com.barterapp.splash.SplashView;

/**
 * Created by vikram on 25/05/16.
 */
public class HomePresenter implements ModelCallBackListener {
    private HomeFragment homeFragment;
    private HomeModel homeModel;

    public HomePresenter() {

    }

    public void setHomeFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public void initializeAndGetHomeModel() {
        homeModel = new HomeModel();
    }

    public void displaySubCategories(String selectedCategory) {
        homeFragment.displaySubCategories(selectedCategory);
    }

    public void navigateToPostAd() {
        homeFragment.navigateToPostAd();
    }

    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }
}
