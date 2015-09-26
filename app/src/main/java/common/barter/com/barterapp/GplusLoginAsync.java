package common.barter.com.barterapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


/**
 * Created by vikram on 16/09/15.
 */
public class GplusLoginAsync extends AsyncTask<String, String, String> implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    /**
     * Before starting background thread Show Progress Dialog
     * */
    private Activity activity;
    private Context context;
    private ProgressDialog pDialog;
    private FragmentManager fragmentManager;
    private GoogleApiClient mGoogleApiClient;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mGoogleAPIConnected = false;
    //Google sign in
    private static final int RC_SIGN_IN = 0;

    public GplusLoginAsync (Activity activity,Context context,FragmentManager fragmentManager)
    {
        this.activity=activity;
        this.context=context;
        this.fragmentManager=fragmentManager;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Creating Product..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

    }

    /**
     * Creating product
     * */
    protected String doInBackground(String... args) {
        buildGoogleApiClient();
        if (mGoogleApiClient !=null)
        {
            mGoogleApiClient.connect();

// To allow user to ask for new account
//            if (mGoogleApiClient.isConnected()) {
//                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//                mGoogleApiClient.disconnect();
//                mGoogleApiClient.connect();
//            }
        }
        else
        {
            flash("Please check Connectivity");
        }

        return null;

    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog once done
        pDialog.dismiss();
        if (mGoogleAPIConnected)
            addUser(1);
    }

    public void addUser(int cond)
    {
        new LoginAsync(context,1,fragmentManager ).execute();
    }

    public  void flash(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.PLUS_LOGIN))
                .addScope(new Scope(Scopes.PLUS_ME))
                .build();
    }
    @Override
    public void onConnected(Bundle bundle) {
// onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        flash("onConnected:" + bundle);
        mGoogleAPIConnected=true;
        getGoogleInfo();

        // Show the signed-in UI
    }

    private void getGoogleInfo() {

        if (mGoogleApiClient.isConnected()) {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                flash("In getGoogleInfo: Information received");

                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                LoginDetails.getInstance().resetDetails();
                LoginDetails.getInstance().setPersonName(currentPerson.getDisplayName());
                LoginDetails.getInstance().setPersonPhoto(currentPerson.getImage().getUrl());
                LoginDetails.getInstance().setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
                LoginDetails.getInstance().setBirthday(currentPerson.getBirthday());
                LoginDetails.getInstance().setLoginLocation(currentPerson.getCurrentLocation());
                LoginDetails.getInstance().setGender(currentPerson.getGender());
                LoginDetails.getInstance().setId(currentPerson.getId());
                LoginDetails.getInstance().setPlacesLived(currentPerson.getPlacesLived());
                LoginDetails.getInstance().setPassword(LoginDetails.getInstance().getEmail().concat("123"));
                addUser(2);
            } else {
                flash("In getGoogleInfo: Information not received");
            }
        } else
        {
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        flash("onConnectionFailed:" + connectionResult);

        if (!mIsResolving) {

            if (connectionResult.hasResolution()) {
                try {

                    connectionResult.startResolutionForResult(activity, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    flash("Could not resolve ConnectionResult:" + e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                //showErrorDialog(connectionResult);
                flash(new Integer(connectionResult.getErrorCode()).toString());
            }
        } else {
            // Show the signed-out UI
            showSignedOutUI();
        }
    }

    public void showSignedOutUI() {
        flash("showSignedOutUI");

    }

}

