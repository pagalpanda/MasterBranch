package common.barter.com.barterapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vikram on 29/09/15.
 */
public class ForgotPassword extends Fragment {

    Activity context;
    private Button btSendPwd;
    private EditText etEmailOrMob;
    GlobalHome activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ForgotPasswordView = inflater.inflate(R.layout.forgot_password, container, false);
        activity = (GlobalHome) getActivity();
        activity.getmDrawerToggle().setDrawerIndicatorEnabled(false);
        activity.setActionBarTitle(MessagesString.HEADER_FORGOT_PASSWORD);
        etEmailOrMob = (EditText) ForgotPasswordView.findViewById(R.id.etCurrentPwd);
        btSendPwd = (Button) ForgotPasswordView.findViewById(R.id.btSendPwd);

        btSendPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonResources.hideKeyboard(getActivity());
                if (isValidInput()) {
                    sendPwd();
                }else {
                    flash(MessagesString.INVALID_EMAIL);
                }

            }
        });

        return ForgotPasswordView;

    }


    public  void flash(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public Boolean isValidInput() {

        if ( (!CommonResources.isValidEmail(etEmailOrMob.getText().toString())) )
        {
            return false;
        }
        return true;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }
    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void sendPwd()
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sendTo",etEmailOrMob.getText().toString());
        params.put("instruction", "0");

        AsyncConnection as = new AsyncConnection(context,CommonResources.getURL("ForgotPwd"),"POST",params,true,MessagesString.SEND_EMAIL){
            public void receiveData(JSONObject json){
                try {
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    if (success == 0) {
                        flash(MessagesString.FGT_PWD1.concat(etEmailOrMob.getText().toString()));

                        navigateToLogin();
                    }
                    else if (success == 1) {
                        flash(MessagesString.FGT_PWD2);

                    }
                    else if (success == 2) {
                        flash(MessagesString.FGT_PWD3);
                    }
                    else {
                        flash(MessagesString.FGT_PWD2);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();

    }

    private void navigateToLogin() {
        ((GlobalHome)getActivity()).getSupportFragmentManager().popBackStack();
        activity.getmDrawerToggle().setDrawerIndicatorEnabled(true);
        activity.setActionBarTitle(MessagesString.HEADER_MY_ACCOUNT);

    }


}


