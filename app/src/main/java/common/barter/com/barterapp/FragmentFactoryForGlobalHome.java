package common.barter.com.barterapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Panda on 15-05-2016.
 */
public class FragmentFactoryForGlobalHome {

    FragmentManager fragmentManager;

    public FragmentFactoryForGlobalHome(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    public Fragment getFragment(int position) {
        Fragment fragment = null;
        FragmentTransaction ft  = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                ft.setCustomAnimations(R.anim.enter_from_left, R.anim.abc_fade_out, R.anim.enter_from_left, R.anim.abc_fade_out);
                break;
            case 1:
                fragment = new PostsFragment("myposts", "");
                ft.setCustomAnimations(R.anim.enter_from_left, R.anim.abc_fade_out, R.anim.enter_from_left, R.anim.abc_fade_out);
                break;
            case 2:
                fragment = new ChooseOptionsOffersFragment();
                ft.setCustomAnimations(R.anim.enter_from_left, R.anim.abc_fade_out, R.anim.enter_from_left, R.anim.abc_fade_out);
                break;
            case 3:
                fragment = new WishList();
                ft.setCustomAnimations(R.anim.enter_from_left, R.anim.abc_fade_out, R.anim.enter_from_left, R.anim.abc_fade_out);
                break;
            case 41:
                fragment = new ManageUser();
                ft.setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out, R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
                break;
            case 42:
                fragment = new LoginParentFragment();
                ft.setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out, R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);

        }

        return fragment;
    }
}
