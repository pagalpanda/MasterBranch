package common.barter.com.barterapp.otp;

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
 * Created by vikram on 05/06/16.
 */
public class OTPModel {
    private ModelCallBackListener modelCallBackListener;
    private Context context;

    public OTPModel(ModelCallBackListener modelCallBackListener, Context context) {
        this.modelCallBackListener = modelCallBackListener;
        this.context = context;
    }

    public void receiveWebOTP() {
        this.sendHttpRequestToGenerateOTP(this.getParamsForGenerateOTP());
    }

    public void doOTPVerification()
    {
        this.sendHttpRequestToVerifyOTP(this.getParamsForVerifyOTP());
    }

    public void sendHttpRequestToGenerateOTP(HashMap<String, String> params) {
        LoginDetails.getInstance().setIsverifying(true);
        AsyncConnection asyncConnection = this.getGenerateOTPAsnycConnection(params, context);
        asyncConnection.execute();
    }

    public HashMap<String, String> getParamsForGenerateOTP() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(MessagesString.USER_ID,LoginDetails.getInstance().getUserid());
        params.put(MessagesString.MOBILENUM, LoginDetails.getInstance().getMobilenum());
        params.put(MessagesString.INSTRUCTION, "0");
        return params;
    }

    public AsyncConnection getGenerateOTPAsnycConnection(HashMap<String, String> params, Context context) {
        return new AsyncConnection(context, CommonResources.getURL(MessagesString.PHP_USERHANDLER),"POST",params,false,null) {
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

    public void sendHttpRequestToVerifyOTP(HashMap<String, String> params) {
        AsyncConnection asyncConnection = this.getVerifyOTPAsnycConnection(params, context);
        asyncConnection.execute();
    }

    public HashMap<String, String> getParamsForVerifyOTP() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid",LoginDetails.getInstance().getUserid());
        params.put("mobilenum",LoginDetails.getInstance().getMobilenum());
        params.put("otp",LoginDetails.getInstance().getOtpReceivedFromDevice());
        params.put("instruction", "1");
        return params;
    }

    public AsyncConnection getVerifyOTPAsnycConnection(HashMap<String, String> params, Context context) {
        return new AsyncConnection(context, CommonResources.getURL(MessagesString.PHP_USERHANDLER), MessagesString.POST, params, false, null) {
            @Override
            public void receiveData(JSONObject json) {
                try {
                    if (json==null){
                        modelCallBackListener.onFailure(MessagesString.OTP_NOT_VERIFIED);
                        return;
                    }
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    if (success == 0) {
                        modelCallBackListener.onSuccess(json);
                    }
                    else{
                        modelCallBackListener.onFailure(MessagesString.OTP_NOT_VERIFIED);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void updateVerifiedStatusonDevice() {
        new CommonResources(context).saveToSharedPrefs(MessagesString.SHARED_PREFS_IS_MOBILE_VERIFIED, LoginDetails.getInstance().getMob_verified());
    }
}
