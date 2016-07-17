package common.barter.com.barterapp.showOffer;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import common.barter.com.barterapp.asyncConnections.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.HttpConnectionParameters;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.data.domain.NewOffer;

/**
 * Created by vikram on 04/06/16.
 */
public class ShowOffersModel {
    private ModelCallBackListener<JSONObject> modelCallBackListener;
    private Context context;

    public ShowOffersModel(ModelCallBackListener<JSONObject> modelCallBackListener, Context context) {
        this.modelCallBackListener = modelCallBackListener;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    public void getAllOffers(){
        this.sendHttpRequestForAllOffers(this.getParamsForAllOfers());
    }

    private void sendHttpRequestForAllOffers(HashMap<String, String> params) {
        AsyncConnection asyncConnection = this.getAllOffersAsnycConnection(params, getContext());
        asyncConnection.execute();
    }

    private HashMap<String, String> getParamsForAllOfers() {
        HashMap<String,String> params = new HashMap <>();
        params.put(MessagesString.USER_ID, LoginDetails.getInstance().getUserid());
        params.put(MessagesString.INSTRUCTION, MessagesString.ALL_OFFERS_INSTRUCTION);
        return params;
    }

    private AsyncConnection getAllOffersAsnycConnection(HashMap<String, String> params, Context context) {
        return new AsyncConnection(context, CommonResources.getURL(MessagesString.GET_OFFER),"POST",params,true,MessagesString.FETCHING_OFFERS) {
            @Override
            public void receiveData(JSONObject json) {
                try{
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    if (success == 0) {
                        modelCallBackListener.onSuccess(json);
                    }
                    else{
                        modelCallBackListener.onFailure(MessagesString.OTP_NOT_GENERATED);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void getUserPost(NewOffer offer){
        this.sendHttpRequestForUserPost(this.getParamsForUserPost(offer));
    }

    private void sendHttpRequestForUserPost(HashMap<String, String> params) {
        AsyncConnection asyncConnection = this.getUserPostAsnycConnection(params, getContext());
        asyncConnection.execute();
    }
    private HashMap<String, String> getParamsForUserPost(NewOffer offer) {
        HashMap<String,String> params = new HashMap <String,String>();
        params.put(MessagesString.SENDER_ID, String.valueOf(offer.getSenderId()));
        params.put(MessagesString.RECEIVER_ID, String.valueOf(offer.getReceiverId()));
        return params;
    }

    private AsyncConnection getUserPostAsnycConnection(HashMap<String, String> params, Context context) {
        return new AsyncConnection(context, CommonResources.getURL(MessagesString.GET_USER_POSTS_FOR_OFFERS),"POST",params,true,MessagesString.FETCHING_POSTS) {
            @Override
            public void receiveData(JSONObject json) {
                try{
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    if (success == 0) {
                        modelCallBackListener.onSuccess(json);
                    }
                    else{
                        modelCallBackListener.onFailure(MessagesString.OTP_NOT_GENERATED);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public HttpConnectionParameters getConnectionParametersForAllOffers() {
        return new HttpConnectionParameters(CommonResources.getURL(MessagesString.GET_OFFER),"POST",this.getParamsForAllOfers());
    }
}
