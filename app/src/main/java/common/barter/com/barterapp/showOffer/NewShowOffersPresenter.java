package common.barter.com.barterapp.showOffer;

import android.content.Context;

import org.json.JSONObject;

import common.barter.com.barterapp.HttpConnectionParameters;
import common.barter.com.barterapp.ModelCallBackListener;

/**
 * Created by vikram on 23/06/16.
 */
public class NewShowOffersPresenter implements ModelCallBackListener<JSONObject> {
    private NewShowOfferFragment showOffersFragment;
    private ShowOffersModel showOffersModel;
    private ShowOfferListener showOfferListener;
    private NewShowOfferHolderAdapter showOfferAdapter;

    private Context getContext() {
        return this.getShowOffersFragment().getContext();
    }
    public NewShowOfferHolderAdapter getShowOfferAdapter() {
        if (showOfferAdapter==null){
            showOfferAdapter = new NewShowOfferHolderAdapter(getContext(),this.getShowOfferListener());
        }
        return showOfferAdapter;
    }

    public ShowOfferListener getShowOfferListener() {
        if (showOfferListener==null){
            showOfferListener = new ShowOfferListener(this);
        }
        return showOfferListener;
    }

    public ShowOffersModel getShowOffersModel() {
        if (showOffersModel == null) {
            showOffersModel = new ShowOffersModel(this,getContext());
        }
        return showOffersModel;
    }

    public NewShowOfferFragment getShowOffersFragment() {
        return showOffersFragment;
    }

    public ShowOfferLoaderManager getShowOfferLoaderManager() {
        return new ShowOfferLoaderManager(getContext(),getConnectionParametersForAllOffers(),this.getShowOfferAdapter());
    }

    private HttpConnectionParameters getConnectionParametersForAllOffers() {
        return this.getShowOffersModel().getConnectionParametersForAllOffers();
    }

    public void setShowOffersFragment(NewShowOfferFragment showOffersFragment) {
        this.showOffersFragment = showOffersFragment;
    }

    public void notifyDataSetChanged() {
        this.getShowOfferAdapter().notifyDataSetChanged();
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    public void onAdapterClicked(int position) {

    }


    public void onPrimaryOfferImageClicked(String postId) {

    }
}

