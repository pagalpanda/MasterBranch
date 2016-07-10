package common.barter.com.barterapp.Login;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import common.barter.com.barterapp.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.data.constants.LoginMode;
import common.barter.com.barterapp.data.domain.LoginUser;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.data.domain.SignUp;

/**
 * Created by vikram on 28/05/16.
 */

public class LoginModel {

    private ModelCallBackListener<JSONObject> modelCallBackListener;
    private Context context;

    public LoginModel(ModelCallBackListener modelCallBackListener,Context context) {
        this.modelCallBackListener = modelCallBackListener;
        this.context=context;
    }

    public void manualSignUp(SignUp signUp) {
        this.signUpUser(signUp, LoginMode.MANUALSIGNUP);
    }

    public void manualLogin(LoginUser login) {
        this.loginUser(login, LoginMode.MANUALLOGIN);
    }

    public void gPlusLogin(SignUp signUp) {
        this.signUpUser(signUp, LoginMode.GPLUS);
    }

    public void doFacebookLogin(SignUp signUp) {
        this.signUpUser(signUp, LoginMode.FACEBOOK);
    }

    public void signUpUser(SignUp signUp, LoginMode loginMode) {
        HashMap<String, String> params = this.getRequestParam(signUp);
        params.put(MessagesString.LOGINMODE, loginMode.toString());
        sendHttpRequest(params);
    }

    public void loginUser(LoginUser login, LoginMode loginMode) {
        HashMap<String, String> params = this.getRequestParam(login);
        params.put(MessagesString.LOGINMODE, loginMode.toString());
        sendHttpRequest(params);
    }

    public HashMap<String, String> getRequestParam(SignUp signUp) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(MessagesString.USERNAME, signUp.getEmailId());
        params.put(MessagesString.PASSWORD, signUp.getPassword());
        params.put(MessagesString.NAME, signUp.getName());
        String mobileNum = signUp.getMobileNum();
        if (mobileNum != null && mobileNum.trim().length() != 0) {
            params.put(MessagesString.MOBILENUM, mobileNum);
        }
        String gender = signUp.getGender();
        if (gender != null && gender.length() != 0) {
            params.put(MessagesString.GENDER, gender);
        }
        return params;
    }

    public HashMap<String, String> getRequestParam(LoginUser login) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(MessagesString.USERNAME, login.getEmailId());
        params.put(MessagesString.PASSWORD, login.getPassword());
        return params;
    }

    public void sendHttpRequest(HashMap<String, String> params) {
        AsyncConnection asyncConnection = this.getAsnycConnectionObject(params,context);
        asyncConnection.execute();
    }

    public AsyncConnection getAsnycConnectionObject(HashMap<String, String> params, Context context) {
        return new AsyncConnection(context, CommonResources.getURL("login"), "POST", params, false, null) {
            @Override
            public void receiveData(JSONObject json) {
                try {

                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    if (success == 0) {
                        modelCallBackListener.onSuccess(json);
                    } else if (success == 1) {
                        modelCallBackListener.onSuccess(json);
                    } else if (success == 2) {
                        modelCallBackListener.onFailure(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };
    }
}

