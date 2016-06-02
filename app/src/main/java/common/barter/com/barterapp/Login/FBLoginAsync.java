package common.barter.com.barterapp.Login;

import android.os.AsyncTask;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.MessagesString;

/**
 * Created by vikram on 15/09/15.
 */
public class FBLoginAsync extends AsyncTask<String, String, String> {

    private LoginPresenter loginPresenter;
    private LoginResult loginResult;

    public FBLoginAsync(LoginResult loginResult, LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        this.loginResult = loginResult;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loginPresenter.getpDialog().createProcessDialog(MessagesString.RECEIVING_DATA);
    }

    protected String doInBackground(String... args) {
        connectAndReceiveFbDetails();
        return null;

    }

    protected void onPostExecute(String file_url) {
        FBClearData();
        loginPresenter.getpDialog().dismissProcessDialog();
    }

    private void connectAndReceiveFbDetails() {
        final AccessToken accessToken = loginResult.getAccessToken();
        if (!(accessToken.getPermissions().contains(MessagesString.EMAIL))) {
            CommonUtil.flash(loginPresenter.getContext(), MessagesString.FB_GRANT_EMAIL_PERMISSION);
        } else {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                    if (graphResponse.getError() != null) {
                        // handle error
                    } else {
                        validateAndSaveFbDetails(user);
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,gender,link,birthday,email");
            request.setParameters(parameters);
            request.executeAndWait();
        }
    }

    public void validateAndSaveFbDetails(JSONObject user) {
        String email = user.optString("email");
        if (email != null && !"".equalsIgnoreCase(email)) {
            loginPresenter.doFacebookLogin(loginPresenter.getSignUpObject(user));
        } else {
            CommonUtil.flash(loginPresenter.getContext(), MessagesString.FB_EMAIL_IS_MANDATORY);

        }
    }

    public void FBLogout() {
        if (loginResult.getAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }
    }

    public void FBClearData() {
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        String temp = "/".concat(loginResult.getAccessToken().getUserId()).concat("/permissions");
        final AccessToken accessToken = loginResult.getAccessToken();
        GraphRequest delPermRequest = new GraphRequest(accessToken, temp, null, HttpMethod.DELETE, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if (graphResponse != null) {
                    FacebookRequestError error = graphResponse.getError();
                    if (error != null) {
                        CommonUtil.flash(loginPresenter.getContext(), error.toString() +" : "+ MessagesString.FB_CLEAR_DATA_ERROR);
                    }
                    FBLogout();
                }
            }
        });
        delPermRequest.executeAsync();

    }
}

