package common.barter.com.barterapp.showOffer;

import org.json.JSONObject;

import common.barter.com.barterapp.AdapterOnClickListener;
import common.barter.com.barterapp.ModelCallBackListener;

/**
 * Created by vikram on 04/06/16.
 */
public class ShowOffersPresenter implements ModelCallBackListener<JSONObject>{
    private ShowOffersFragment showOffersFragment;
    private ShowOffersModel showOffersModel;
    private ShowOfferListener showOfferListener;
    private ShowOffersAdapter showOffersAdapter;

    public ShowOffersPresenter() {
    }

    public ShowOffersFragment getShowOffersFragment() {
        return showOffersFragment;
    }

    public ShowOffersModel getShowOffersModel() {
        if (showOffersModel==null){
            showOffersModel = new ShowOffersModel(this,this.getShowOffersFragment().getContext());
        }
        return showOffersModel;
    }

    public ShowOfferListener getShowOfferListener() {
        if (showOfferListener==null) {
            showOfferListener = new ShowOfferListener(this);
        }
        return showOfferListener;
    }

    public ShowOffersAdapter getShowOffersAdapter() {
        return showOffersAdapter;
    }

    public void setShowOffersAdapter(ShowOffersAdapter showOffersAdapter) {
        this.showOffersAdapter = showOffersAdapter;
    }

    public void setShowOffersFragment(ShowOffersFragment showOffersFragment) {
        this.showOffersFragment = showOffersFragment;
    }

    public AdapterOnClickListener getAdapterOnClickListener(){
        return this.getShowOfferListener().getAdapterOnClickListener() ;
    }






    @Override
    public void onSuccess(JSONObject jsonObject) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    public void onAdapterClicked(int position) {
        showOffersFragment.getUserPosts(position);
    }
}
