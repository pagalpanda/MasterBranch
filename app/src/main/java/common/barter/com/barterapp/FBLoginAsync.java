package common.barter.com.barterapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
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
        Context context;
        ProgressDialog pDialog;
        FragmentManager fragmentManager;
        LoginResult loginResult;
        String error;

        public FBLoginAsync (Context context,FragmentManager fragmentManager,LoginResult loginResult,ProgressDialog pDialog)
        {
            this.context=context;
            this.fragmentManager=fragmentManager;
            this.loginResult=loginResult;
            this.pDialog=pDialog;
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
        FBLogout();
        if(isEmailIdReturned)
        {
            addUser(1);
        }
        else {
            // Email permission not set in FB
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            pDialog.dismiss();
        }
    }

    private void getFacebookInfo( ) {

        //flash("In getFacebookInfo: Information received");
        final AccessToken accessToken = loginResult.getAccessToken();

        if (!(accessToken.getPermissions().contains("email")))
        {
            isEmailIdReturned = false;
            error ="Please grant email permission";
        }

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
    boolean isEmailIdReturned;

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
        }
        else
        {
            isEmailIdReturned = false;
            error ="Please grant email permission";

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

        // Solution option1
//        GraphRequest delPermRequest = new GraphRequest(AccessToken.getCurrentAccessToken(), "/{user-id}/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
//            @Override
//            public void onCompleted(GraphResponse graphResponse) {
//                if (graphResponse != null) {
//                    FacebookRequestError error = graphResponse.getError();
//                    if (error != null) {
//                        flash(error.toString());
//                    }
//                    FBLogout();
//                }
//            }
//        });
//        flash("Executing revoke permissions with graph path");
//        delPermRequest.executeAndWait();
//
//        // Solution option2
//        GraphRequest delPermRequest1 = GraphRequest.newDeleteObjectRequest(AccessToken.getCurrentAccessToken(), "/{user-id}/permissions/", new GraphRequest.Callback() {
//            @Override
//            public void onCompleted(GraphResponse graphResponse) {
//                if (graphResponse != null) {
//                    FacebookRequestError error = graphResponse.getError();
//                    if (error != null) {
//                        flash(error.toString());
//                    }
//                    FBLogout();
//                }
//            }
//        });
//        delPermRequest1.executeAndWait();
//        flash("Executing revoke permissions with graph path");

        }
}

