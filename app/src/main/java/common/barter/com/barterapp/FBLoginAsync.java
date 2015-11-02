package common.barter.com.barterapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

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

/**
 * Created by vikram on 15/09/15.
 */
public class FBLoginAsync extends AsyncTask<String, String, String> {

/**
 * Before starting background thread Show Progress Dialog
 * */
    private Context context;
    private ProgressDialog pDialog;
    private FragmentManager fragmentManager;
    private LoginResult loginResult;
    private String error;
    private boolean isEmailIdReturned;
    private boolean isEmailPermissionGranted;


        public FBLoginAsync (Context context,FragmentManager fragmentManager,LoginResult loginResult,ProgressDialog pDialog)
        {
            this.context=context;
            this.fragmentManager=fragmentManager;
            this.loginResult=loginResult;
            this.pDialog=pDialog;
            isEmailIdReturned=true;
            isEmailPermissionGranted=true;
        }

@Override
protected void onPreExecute() {
        super.onPreExecute();

    if(pDialog==null){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Receiving Data ..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    else
        pDialog.setMessage("Receiving Data ..");

}

/**
 * Creating product
 * */
protected String doInBackground(String... args) {

        getFacebookInfo();
        return null;

        }

/**
 * After completing background task Dismiss the progress dialog
 * **/
protected void onPostExecute(String file_url) {
        // dismiss the dialog once done

        FBClearData();
        if ((isEmailPermissionGranted) && isEmailIdReturned)
        {
            addUser(1);
        }
        else
        {
            // Email permission not set in FB
            pDialog.dismiss();

            flash(error);

        }
    }

    private void getFacebookInfo( ) {

        //flash("In getFacebookInfo: Information received");
        final AccessToken accessToken = loginResult.getAccessToken();

        if (!(accessToken.getPermissions().contains("email")))
        {
            isEmailPermissionGranted= false;
            error =MessagesString.FB_EMAIL_PERM_ERROR1;
        }
        else {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                    if (graphResponse.getError() != null) {
                        // handle error
                    } else {


                        setFBinfo(user);
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,gender,link,birthday,email");
            request.setParameters(parameters);
            request.executeAndWait();
        }

//        Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
//
//            @Override
//            public void onCompleted(GraphUser user, Response response) {
//                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
//                String userId = (String) user.asMap().get("email");
//                i.putExtra("epuzzle", userId);
//                startActivity(i);
//            }
//        });



        //setLogin_mode(1);
        //addUser(1);

    }


    public void setFBinfo(JSONObject user) {

        LoginDetails.getInstance().resetDetails();
        String email = user.optString("email");
        if(email != null && !"".equalsIgnoreCase(email)) {
            LoginDetails.getInstance().setEmail(user.optString("email"));
//            String safeEmail = user.asMap().get("email").toString();

            LoginDetails.getInstance().setId(user.optString("id"));
            LoginDetails.getInstance().setPersonName(user.optString("name"));
            LoginDetails.getInstance().setGender(user.optString("gender"));
            LoginDetails.getInstance().setBirthday(user.optString("birthday"));
            LoginDetails.getInstance().setPassword(LoginDetails.getInstance().getEmail().concat("123"));
            isEmailIdReturned = true;
            isEmailPermissionGranted=true;
        }
        else
        {
            isEmailIdReturned = false;
            error =MessagesString.FB_EMAIL_PERM_ERROR2;

        }


    }

    public void addUser(int cond)
    {
        new LoginAsync(context,1,fragmentManager,pDialog ).execute();
    }

    public  void flash(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public void FBLogout()
    {

        if(loginResult.getAccessToken() != null){
            LoginManager.getInstance().logOut();
        }
    }

    public void FBClearData() {

         //Solution option1
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        String temp = "/".concat(loginResult.getAccessToken().getUserId()).concat("/permissions");
        final AccessToken accessToken = loginResult.getAccessToken();
        GraphRequest delPermRequest = new GraphRequest(accessToken, temp, null, HttpMethod.DELETE, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                if (graphResponse != null) {
                    FacebookRequestError error = graphResponse.getError();
                    if (error != null) {
                        flash(error.toString() + ": Error while Logging in.");
                    }
                    FBLogout();
                }
            }
        });
        // flash("Executing revoke permissions with graph path");
        delPermRequest.executeAsync();

        }
}

