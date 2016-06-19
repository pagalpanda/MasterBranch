package common.barter.com.barterapp.showOffer;

import android.content.Context;

import org.json.JSONObject;

import common.barter.com.barterapp.ModelCallBackListener;

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


}
