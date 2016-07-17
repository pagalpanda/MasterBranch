package common.barter.com.barterapp.posts;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.makeOffer.MakeOfferFragment;
import common.barter.com.barterapp.reviewOffer.ReviewOfferFragment;

/**
 * Created by vikramb on 15/07/16.
 */
public class PostDetailsListener {
    private PostDetailsPresenter postDetailsPresenter;

    public PostDetailsListener(PostDetailsPresenter postDetailsPresenter) {
        this.postDetailsPresenter = postDetailsPresenter;
    }

    @NonNull
    public View.OnClickListener getOnWishListClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDetailsPresenter.onWishListClicked();
            }
        };
    }

    @NonNull
    public View.OnClickListener getOnFloatingButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDetailsPresenter.onFloatingButtonClicked();
            }
        };
    }
}
