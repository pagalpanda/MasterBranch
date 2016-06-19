package common.barter.com.barterapp.otp;

import android.view.View;

import org.json.JSONObject;

import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.ModelCallBackListener;

/**
 * Created by vikram on 05/06/16.
 */
public class OTPListener{
    private OTPPresenter otpPresenter;

    public OTPListener(OTPPresenter otpPresenter) {
        this.otpPresenter = otpPresenter;
    }

    public View.OnClickListener getVerifyButtonOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpPresenter.onVerifyButtonClicked();
            }
        };
    }

    public View.OnClickListener getResendButtonOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otpPresenter.onResendButtonClicked();
            }
        };
    }

}
