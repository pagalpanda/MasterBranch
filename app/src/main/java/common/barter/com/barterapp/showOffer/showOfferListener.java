package common.barter.com.barterapp.showOffer;

import common.barter.com.barterapp.AdapterOnClickListener;

/**
 * Created by vikram on 04/06/16.
 */
public class ShowOfferListener {
    private ShowOffersPresenter showOffersPresenter;

    public ShowOfferListener(ShowOffersPresenter showOffersPresenter) {
        this.showOffersPresenter = showOffersPresenter;
    }

    public AdapterOnClickListener getAdapterOnClickListener(){
        return new AdapterOnClickListener(){

            @Override
            public void onClick(int position) {
                showOffersPresenter.onAdapterClicked(position);
            }
        };
    }


}
