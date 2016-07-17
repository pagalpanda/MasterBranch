package common.barter.com.barterapp.reviewOffer;

import android.content.Context;

import org.json.JSONObject;

import common.barter.com.barterapp.ModelCallBackListener;

/**
 * Created by vikramb on 13/07/16.
 */
public class ReviewOfferModel {
    private Context context;
    private ModelCallBackListener<JSONObject> modelCallBackListener;

    public ReviewOfferModel(Context context, ModelCallBackListener<JSONObject> modelCallBackListener) {
        this.context = context;
        this.modelCallBackListener = modelCallBackListener;
    }

}
