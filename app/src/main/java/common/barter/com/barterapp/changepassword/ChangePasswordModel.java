package common.barter.com.barterapp.changepassword;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import common.barter.com.barterapp.asyncConnections.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.NetworkCallListener;

/**
 * Created by Panda on 19-05-2016.
 */
public class ChangePasswordModel {
    NetworkCallListener listener;
    public ChangePasswordModel(NetworkCallListener listener) {
        this.listener = listener;

    }

    public void savePassword(Context context, HashMap<String, String> params) {
        AsyncConnection as = new AsyncConnection(context, CommonResources.getURL("UserHandler"),"POST",params,false,null){
            public void receiveData(JSONObject json){
                try {
                    String TAG_SUCCESS = "success";
                    int success = json.getInt(TAG_SUCCESS);
                    if(success == 1) {
                        if ("1".equals(json.getInt("userpresent")))
                        {
                            success = 11;
                        }
                        else
                        {
                            success = 12;
                        }

                    }
                    listener.onNetworkCallSuccess(success);



                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onNetworkCallFailure();
                }
            }
        };
        as.execute();

    }
}
