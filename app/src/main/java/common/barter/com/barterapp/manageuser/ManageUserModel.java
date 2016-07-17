package common.barter.com.barterapp.manageuser;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import common.barter.com.barterapp.asyncConnections.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;

/**
 * Created by vikram on 04/06/16.
 */
public class ManageUserModel {

    private ModelCallBackListener modelCallBackListener;
    private Context context;

    public ManageUserModel(ModelCallBackListener modelCallBackListener, Context context) {
        this.modelCallBackListener = modelCallBackListener;
        this.context = context;
    }

    public void saveData(HashMap<String, String> params) {
        sendHttpRequest(params);
    }

    public void sendHttpRequest(HashMap<String, String> params) {
        AsyncConnection asyncConnection = this.getAsnycConnectionObject(params,context);
        asyncConnection.execute();
    }

    public AsyncConnection getAsnycConnectionObject(HashMap<String, String> params, Context context) {
        return new AsyncConnection(context, CommonResources.getURL(MessagesString.PHP_USERHANDLER), MessagesString.POST, params, false, null) {
            @Override
            public void receiveData(JSONObject json) {
                try {
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    if (success == 0) {
                        modelCallBackListener.onSuccess(json);
                    }
                    else{
                        modelCallBackListener.onFailure(MessagesString.ERROR_IN_UPDATING_USER_DETAILS);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void saveMobileNumOnDevice() {
        new CommonResources(context).saveToSharedPrefs(MessagesString.SHARED_PREFS_MOBILE, LoginDetails.getInstance().getMobilenum());
    }
}
