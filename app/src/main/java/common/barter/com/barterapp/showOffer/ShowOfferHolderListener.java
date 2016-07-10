package common.barter.com.barterapp.showOffer;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by vikram on 20/06/16.
 */
public class ShowOfferHolderListener {
    private ShowOffersHolderPresenter showOffersHolderPresenter;

    public ShowOfferHolderListener(ShowOffersHolderPresenter showOffersHolderPresenter) {
        this.showOffersHolderPresenter = showOffersHolderPresenter;
    }

    public View.OnClickListener getPrimaryOfferImageOnClickListener(final String postId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOffersHolderPresenter.onPrimaryOfferImageClicked(postId);
            }
        };
    }
    @NonNull
    public View.OnClickListener getHoldercardViewOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOffersHolderPresenter.onHoldercardViewOnClickListener(position);
            }
        };
    }
}
