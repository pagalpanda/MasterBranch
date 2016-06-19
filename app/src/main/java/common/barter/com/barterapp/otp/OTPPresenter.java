package common.barter.com.barterapp.otp;

import android.content.Context;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.IncomingSms;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;

/**
 * Created by vikram on 05/06/16.
 */
public class OTPPresenter implements ModelCallBackListener<JSONObject>{
    private OTPFragment otpFragment;
    private OTPListener otpListener;
    private OTPModel otpModel;
    private OTPVerificationDialog otpVerificationDialog;
    private CountDownTimer countDownTimer;
    private final long startTime = 25 * 1000;
    private final long interval = 1 * 1000;
    private IncomingSms incomingSms = new IncomingSms();

    public Context getContext() {
        return this.getOtpFragment().getContext();
    }

    public OTPFragment getOtpFragment() {
        return otpFragment;
    }

    public void setOtpFragment(OTPFragment otpFragment) {
        this.otpFragment = otpFragment;
    }

    public OTPListener getOtpListener() {
        if(otpListener==null){
            otpListener= new OTPListener(this);
        }
        return otpListener;
    }

    public OTPModel getOtpModel() {
        if(otpModel==null){
            otpModel= new OTPModel(this,this.getContext());
        }
        return otpModel;
    }

    public OTPVerificationDialog getOtpVerificationDialog() {
        if(otpVerificationDialog==null){
            otpVerificationDialog = new OTPVerificationDialog(this.getContext());
            otpVerificationDialog.setCancelable(false);
        }
        return otpVerificationDialog;
    }

    public void registerBroadcastReceiver(View view) {
        this.getContext().registerReceiver(this.incomingSms, new IntentFilter(
                MessagesString.ANDROID_TELEPHONY_SMS_RECEIVED));
        CommonUtil.flash(this.getContext(), MessagesString.REGISTER_BROADCAST_RECEIVER);
    }

    public void unregisterBroadcastReceiver(View view) {
        this.getContext().unregisterReceiver(this.incomingSms);
        CommonUtil.flash(this.getContext(), MessagesString.UNREGISTER_BROADCAST_RECEIVER);
    }

    public void generateOTP(){
        LoginDetails.getInstance().setUpForNewOtpRequest();
        this.getOtpModel().receiveWebOTP();
        this.startCountdown(startTime, interval);
    }

    public void startCountdown(long startTime, long interval) {
        countDownTimer = new MyCountDownTimer(startTime, interval,this);
        countDownTimer.start();
    }

    public void verifyOTP()
    {
        if (LoginDetails.getInstance().getIsverifying()){
            String otpManual = LoginDetails.getInstance().getOtpReceivedFromDevice();
            if(otpManual!=null && otpManual.length() == MessagesString.OTP_LENGTH) {
                this.getOtpVerificationDialog().show();
                LoginDetails.getInstance().setOtpReceivedFromDevice(otpManual);
                this.getOtpModel().doOTPVerification();
            }
            else if (otpManual!=null) {
                this.getOtpVerificationDialog().dismiss();
                CommonUtil.flash(this.getContext(), MessagesString.INCORRECT_ENTRY);
            }
        } else if ( (LoginDetails.getInstance().getMob_verified()!=null) && (LoginDetails.getInstance().getMob_verified().equals(MessagesString.MOB_VERIFIED_IS_TRUE)) ) {
            this.getOtpFragment().setOtpVerified(true);
            this.getOtpFragment().activateVerifyButton(false);
        }else {

        }
    }
    public void setinputData() {
        this.getOtpFragment().setHeading(MessagesString.OTP_NUMBER_MESSAGE + LoginDetails.getInstance().getMobilenum());
        if (LoginDetails.getInstance().getIsverifying()){
            this.getOtpFragment().setLayoutForInProgress();
        }
        else{
            this.getOtpFragment().setLayoutForNewRequest();
        }
    }

    @Override
    public void onSuccess(JSONObject json) {
        if (json.has(MessagesString.OTP)) {
            processGeneratededOTPResponse(json);
        } else if (json.has(MessagesString.IS_VERIFIED)) {
            processOTPVerificationResponse(json);
        }
    }

    private void processOTPVerificationResponse(JSONObject json) {
        try {
            this.getOtpVerificationDialog().dismiss();
            if (json.getString(MessagesString.IS_VERIFIED).equals(MessagesString.MOB_VERIFIED_IS_FALSE)){
                LoginDetails.getInstance().setMob_verified(MessagesString.MOB_VERIFIED_IS_FALSE);
            }else{
                LoginDetails.getInstance().setMob_verified(MessagesString.MOB_VERIFIED_IS_TRUE);
            }
            this.getOtpModel().updateVerifiedStatusonDevice();
            LoginDetails.getInstance().setUpAfterOtpVerified();
            this.getOtpFragment().navigateToManageUser();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processGeneratededOTPResponse(JSONObject json) {
        try{
            String otp =json.getString(MessagesString.OTP);
            LoginDetails.getInstance().setIsverifying(true);
            LoginDetails.getInstance().setOtpReceivedFromWeb(otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        LoginDetails.getInstance().setIsverifying(false);
        this.getOtpVerificationDialog().dismiss();
        CommonUtil.flash(this.getContext(),errorMessage);
    }

    public void onEachTick(long millisUntilFinished) {
        if (!this.getOtpFragment().getOtpVerified()){
            this.getOtpFragment().setTextOnVerifyButton(MessagesString.SEND_AGAIN_IN + millisUntilFinished / 1000);
        }
        verifyOTP();
    }

    public void onCountDownFinish() {
        if (!this.getOtpFragment().getOtpVerified()){
            this.getOtpFragment().activateVerifyButton(true);
        }
//        if(!(fragment instanceof OTPFragment))
//            setDialogFragment();
    }

    public void onVerifyButtonClicked() {
        LoginDetails.getInstance().setOtpReceivedFromDevice(this.getOtpFragment().getEnteredOTP());
        verifyOTP();
    }

    public void onResendButtonClicked() {
        LoginDetails.getInstance().setUpForNewOtpRequest();
        this.generateOTP();
    }
}
