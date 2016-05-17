package common.barter.com.barterapp.forgotpassword;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import common.barter.com.barterapp.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.NetworkCallListener;

/**
 * Created by Panda on 17-05-2016.
 */
public class ForgotPasswordModel {
    NetworkCallListener listener;
    public ForgotPasswordModel(NetworkCallListener listener) {
        this.listener = listener;

    }

    public void sendPassword(Context context, HashMap<String, String> params) {
        AsyncConnection as = new AsyncConnection(context, CommonResources.getURL("ForgotPwd"),"POST",params,true, MessagesString.SEND_EMAIL){
            public void receiveData(JSONObject json){
                try {
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    listener.onNetworkCallSuccess(success);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();
    }
}
