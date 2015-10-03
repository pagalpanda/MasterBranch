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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ForgotPasswordView = inflater.inflate(R.layout.forgot_password, container, false);
        etEmailOrMob = (EditText) ForgotPasswordView.findViewById(R.id.etCurrentPwd);
        btSendPwd = (Button) ForgotPasswordView.findViewById(R.id.btSendPwd);

        btSendPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidInput()) {
                    sendPwd();
                }

            }
        });

        return ForgotPasswordView;

    }


    public  void flash(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public Boolean isValidInput() {

        if ( (!isValidEmail(etEmailOrMob.getText().toString())) && (!isValidMobile(etEmailOrMob.getText().toString())) )
        {
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidMobile(String mobilenum) {
        String MOBILE_PATTERN = "^[1-9][0-9]{9}$";
        Pattern pattern = Pattern.compile(MOBILE_PATTERN);
        Matcher matcher = pattern.matcher(mobilenum);
        return matcher.matches();
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
        params.put("instruction", isValidEmail(etEmailOrMob.getText().toString()) ? "0" : "1");

        AsyncConnection as = new AsyncConnection(context,CommonResources.getURL("ForgotPwd"),"POST",params,true,"Sending Password"){
            public void receiveData(JSONObject json){
                try {
                    String TAG_SUCCESS = "success";
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 0) {
                        flash("Your new Password has been sent to".concat(etEmailOrMob.getText().toString()));
                        ((GlobalHome)getActivity()).getSupportFragmentManager().popBackStack();
                    }
                    else if (success == 1) {
                        flash("We are facing some issues. Please try again later.");

                    }
                    else if (success == 2) {
                        etEmailOrMob.setError("Email id or Mobile not present.");
                        flash("Email id or Mobile not present.");
                    }
                    else {
                        flash("We are facing some issues. Please try again later.");

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();

    }

}


