package common.barter.com.barterapp.showOffer;

import android.support.annotation.NonNull;
import android.view.View;

import common.barter.com.barterapp.AdapterOnClickListener;
import common.barter.com.barterapp.posts.PostDetailsDialogOffer;

/**
 * Created by vikram on 04/06/16.
 */
public class showOfferListener {
    private NewShowOffersPresenter showOffersPresenter;

    public showOfferListener(NewShowOffersPresenter showOffersPresenter) {
        this.showOffersPresenter = showOffersPresenter;
    }
    public View.OnClickListener getPrimaryOfferImageOnClickListener(final String postId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOffersPresenter.onPrimaryOfferImageClicked(postId);
            }
        };
    }

    public AdapterOnClickListener getAdapterOnClickListener() {
        return new AdapterOnClickListener() {

            @Override
            public void onClick(int position) {
                showOffersPresenter.onAdapterClicked(position);
            }
        };
    }
}
