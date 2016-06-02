package common.barter.com.barterapp.Login;

import android.app.Activity;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.MessagesString;

/**
 * Created by vikram on 28/05/16.
 */
public class GPlusConnectionListener implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,GoogleplusListener {


    private Activity activity;
    private LoginPresenter loginPresenter;

    private boolean gPlusShouldResolve = false;
    private GoogleApiClient gPlusApiClient;
    private boolean gPlusIsResolving = false;
    private boolean gPlusAPIConnected = false;

    static final int gPlusSigninId = 14;


    public GPlusConnectionListener(Activity activity, LoginPresenter loginPresenter) {
        this.activity = activity;
        this.loginPresenter = loginPresenter;
    }
    public boolean isConnected() {
        return gPlusApiClient.isConnected();
    }

    public boolean isgPlusAPIConnected() {
        return gPlusAPIConnected;
    }

    public void setgPlusAPIConnected(boolean gPlusAPIConnected) {
        this.gPlusAPIConnected = gPlusAPIConnected;
    }

    public boolean isgPlusIsResolving() {
        return gPlusIsResolving;
    }

    public void setgPlusIsResolving(boolean gPlusIsResolving) {
        this.gPlusIsResolving = gPlusIsResolving;
    }

    public GoogleApiClient getgPlusApiClient() {
        return gPlusApiClient;
    }

    public void setgPlusApiClient(GoogleApiClient gPlusApiClient) {
        this.gPlusApiClient = gPlusApiClient;
    }

    public boolean isgPlusShouldResolve() {
        return gPlusShouldResolve;
    }

    public void setgPlusShouldResolve(boolean gPlusShouldResolve) {
        this.gPlusShouldResolve = gPlusShouldResolve;
    }

    @Override
    public void onConnected(Bundle bundle) {
        gPlusAPIConnected =true;
        if (gPlusApiClient.isConnected()) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(gPlusApiClient);
            if (currentPerson != null) {
                loginPresenter.getpDialog().createProcessDialog(MessagesString.GPLUS_CONNECT1);
                loginPresenter.doGPlusLogin(loginPresenter.getSignUpObject(currentPerson));
                this.deleteGPlusAccountData();

            } else {
                CommonUtil.flash(loginPresenter.getContext(), MessagesString.GPLUS_ERROR2);
            }
        } else
        {
            buildGoogleApiClient();
            gPlusApiClient.connect();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        gPlusApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!gPlusIsResolving) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(activity, gPlusSigninId);
                    gPlusIsResolving = true;
                    // gPlusApiClient.connect();
                } catch (IntentSender.SendIntentException e) {
                    //flash("Could not resolve ConnectionResult:" + e);
                    gPlusIsResolving = false;
                    gPlusApiClient.connect();
                }
            } else {
                loginPresenter.getpDialog().dismissProcessDialog();
            }
        } else {
            deleteGPlusAccountData();
        }
    }
    protected synchronized GoogleApiClient buildGoogleApiClient() {
        gPlusApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.PLUS_LOGIN))
                .addScope(new Scope(Scopes.PLUS_ME))
                .build();
        return gPlusApiClient;
    }

    public void deleteGPlusAccountData() {
        if (gPlusApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(gPlusApiClient);
            gPlusApiClient.disconnect();
        }

    }

    public void grantPermissionAndReconnect(int resultCode){
        if (resultCode != Activity.RESULT_OK) {
            gPlusShouldResolve = false;
        }
        else {
            gPlusShouldResolve = true;
            loginPresenter.getpDialog().createProcessDialog(MessagesString.GPLUS_CONNECTING);
        }
        gPlusIsResolving= false;
        if(gPlusShouldResolve) {
            if (gPlusApiClient == null) {
                buildGoogleApiClient();
                gPlusApiClient.connect();
            } else {
                gPlusApiClient.connect();
            }
        }
    }

    @Override
    public void onClick(View view) {
        this.setgPlusIsResolving(false);
        this.loginPresenter.getpDialog().createProcessDialog(MessagesString.GPLUS_CONNECTING);
        this.buildGoogleApiClient();
        if (gPlusApiClient!=null)
        {
            gPlusApiClient.connect();
        }else {
            CommonUtil.flash(loginPresenter.getContext(), MessagesString.CHECK_NETWORK_CONNECTIVITY);
            this.loginPresenter.getpDialog().dismissProcessDialog();
        }

    }
}
