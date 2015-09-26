package common.barter.com.barterapp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by amitpa on 9/19/2015.
 */

/**
 * Created by amitpa on 9/19/2015.
 */

/**
 * Created by amitpa on 9/19/2015.
 */

/**
 * Created by amitpa on 9/19/2015.
 */

public class BaseBackPressedListener implements OnBackPressedListener {
    private final FragmentActivity activity;

    public BaseBackPressedListener(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void doBack() {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}