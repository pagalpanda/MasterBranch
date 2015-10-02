package common.barter.com.barterapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vikram on 15/09/15.
 */
public class FBLoginAsync extends AsyncTask<String, String, String> {

/**
 * Before starting background thread Show Progress Dialog
 * */
        Context context;
        ProgressDialog pDialog;
        FragmentManager fragmentManager;
        LoginResult loginResult;

        public FBLoginAsync (Context context,FragmentManager fragmentManager,LoginResult loginResult)
        {
            this.context=context;
            this.fragmentManager=fragmentManager;
            this.loginResult=loginResult;
        }

@Override
protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Connecting to Facebook ..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
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
        pDialog.dismiss();
    if(isEmailIdReturned)
        addUser(1);
    else// Email permission not set in FB
        Toast.makeText(context, "Email Permission not set in Facebook",Toast.LENGTH_SHORT).show();
        }

    private void getFacebookInfo( ) {

        //flash("In getFacebookInfo: Information received");
        final AccessToken accessToken = loginResult.getAccessToken();
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
        parameters.putString("fields", "id,name,email,gender, birthday,location");
        request.setParameters(parameters);
        request.executeAndWait();
        //setLogin_mode(1);
        //addUser(1);

    }
    boolean isEmailIdReturned;

    public void setFBinfo(JSONObject user) {

        LoginDetails.getInstance().resetDetails();
        String email = user.optString("email");
        if(email != null && !"".equalsIgnoreCase(email)) {
            LoginDetails.getInstance().setEmail(user.optString("email"));


            LoginDetails.getInstance().setId(user.optString("id"));
            LoginDetails.getInstance().setPersonName(user.optString("name"));
            LoginDetails.getInstance().setGender(user.optString("gender"));
            LoginDetails.getInstance().setBirthday(user.optString("birthday"));
            LoginDetails.getInstance().setPassword(LoginDetails.getInstance().getEmail().concat("123"));
            isEmailIdReturned = true;
        }
        else
            isEmailIdReturned = false;


    }

    public void addUser(int cond)
    {
        new LoginAsync(context,1,fragmentManager ).execute();
    }

    public  void flash(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

