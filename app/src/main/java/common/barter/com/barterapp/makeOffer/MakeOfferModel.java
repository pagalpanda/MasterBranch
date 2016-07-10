package common.barter.com.barterapp.makeOffer;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import common.barter.com.barterapp.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.data.domain.NewPost;

/**
 * Created by vikram on 06/07/16.
 */
public class MakeOfferModel {
    private Context context;
    private ModelCallBackListener<JSONObject> modelCallBackListener;

    public MakeOfferModel(Context context, ModelCallBackListener<JSONObject> modelCallBackListener) {
        this.context = context;
        this.modelCallBackListener = modelCallBackListener;
    }


    public void getAllPostsOFSenderAndeReceiver(Long senderId,Long receiverId) {
        this.sendHttpRequestToGetAllPostsOFSenderAndeReceiver(this.getParamsForGetAllPostsOFSenderAndeReceiver(senderId,receiverId));
    }

    public void sendHttpRequestToGetAllPostsOFSenderAndeReceiver(HashMap<String, String> params) {
        AsyncConnection asyncConnection = this.getAllPostsOFSenderAndeReceiverAsnycConnection(params, context);
        asyncConnection.execute();
    }

    public HashMap<String, String> getParamsForGetAllPostsOFSenderAndeReceiver(Long senderId,Long receiverId) {
        HashMap<String,String> params = new HashMap <String,String>();
        params.put(MessagesString.SENDER_ID, String.valueOf(senderId));
        params.put(MessagesString.RECEIVER_ID, String.valueOf(receiverId));
        return params;
    }

    public AsyncConnection getAllPostsOFSenderAndeReceiverAsnycConnection(HashMap<String, String> params, Context context) {
        return new AsyncConnection(context, CommonResources.getURL(MessagesString.GET_MAKE_OFFER_POSTS),"POST",params,true,MessagesString.FETCHING_POSTS) {
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
}
