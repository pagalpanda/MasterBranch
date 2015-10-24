package common.barter.com.barterapp;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by vikram on 17/10/15.
 */
public class OTPVerificationAdapter {

    private Activity activity;
    private Context context;
    private OTPVerificationDialog otpVerificationDialog;
    int i = 1;
    private Boolean result = false;
    CountDownTimer countDownTimer;

    public OTPVerificationAdapter(Activity activity,Context context)
    {
        this.activity=activity;
        this.context=context;
        otpVerificationDialog = new OTPVerificationDialog(this.context);
        otpVerificationDialog.setCancelable(false);

    }

    public void startTimer()
    {

    }

    public void generateOTP()
    {
        receiveWebOTP();
    }

    public Boolean getResult() {
        return result;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }

    public void receiveWebOTP() {
        //createProgressBarProperties();
        otpVerificationDialog.show();
        //test data
        //LoginDetails.getInstance().testData();
        LoginDetails.getInstance().setIsverifying(true);
        LoginDetails.getInstance().setOtp_received_from_web(null);
        LoginDetails.getInstance().setOtp_received_from_device(null);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid",LoginDetails.getInstance().getUserid());
        params.put("mobilenum",LoginDetails.getInstance().getMobilenum());
        params.put("instruction", "0");
        as = new AsyncConnection(context,CommonResources.getURL("UserHandler"),"POST",params,false,null){
            public void receiveData(JSONObject json){
                try {
                    String TAG_SUCCESS = "success";
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 0) {
                        LoginDetails.getInstance().setOtp_received_from_web(json.getString("otp"));
                        LoginDetails.getInstance().setIsverifying(true);
                        countDownTimer = new MyCountDownTimer(startTime, interval);
                        countDownTimer.start();

                        //Add timer to progress bar

                    }
                    else if (success == 1) {
                        otpVerificationDialog.dismiss();
                        LoginDetails.getInstance().setIsverifying(false);
                        //Disable Progress bar
                    }
                    else {
                        otpVerificationDialog.dismiss();
                        LoginDetails.getInstance().setIsverifying(false);
                        //Disable Progress bar
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();

    }

    boolean isExecuted;
    public void verifyOTP()
    {
        if (LoginDetails.getInstance().getIsverifying())
        {
            if( (LoginDetails.getInstance().getOtp_received_from_device()!=null) && (LoginDetails.getInstance().getOtp_received_from_web()!=null))
            {

                if(!isExecuted){
                    isExecuted = true;
                    setResult(true);
                    doOTPVerification();

                }

            }
        }
        else if ( (LoginDetails.getInstance().getMob_verified()!=null) && (LoginDetails.getInstance().getMob_verified().equalsIgnoreCase("1")) )
        {
            setResult(true);
        }

    }
    private final long startTime = 25 * 1000;
    private final long interval = 1 * 1000;
    AsyncConnection as;

    public void doOTPVerification()
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid",LoginDetails.getInstance().getUserid());
        params.put("mobilenum",LoginDetails.getInstance().getMobilenum());
        params.put("otp",LoginDetails.getInstance().getOtp_received_from_device());
        params.put("instruction", "1");
        as = new AsyncConnection(context,CommonResources.getURL("UserHandler"),"POST",params,false,null){
            public void receiveData(JSONObject json){
                try {
                    String TAG_SUCCESS = "success";
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 0) {
                        LoginDetails.getInstance().setMob_verified(json.getString("is_verified"));
                        new CommonResources(context).saveToSharedPrefs(MessagesString.SHARED_PREFS_IS_MOBILE_VERIFIED, LoginDetails.getInstance().getMob_verified());
                        LoginDetails.getInstance().setIsverifying(false);
                        otpVerificationDialog.dismiss();
                       // btverify.setImageResource(R.drawable.home);
                        //getFragmentManager().popBackStack();
                    }
                    else if (success == 1) {
                        otpVerificationDialog.dismiss();
                        LoginDetails.getInstance().setIsverifying(false);// OTP Ver failed : Try Again Later
                    }
                    else {
                        otpVerificationDialog.dismiss();
                        LoginDetails.getInstance().setIsverifying(false);// OTP Ver failed : Try Again Later

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();

    }

    public void setDialogFragment()
    {
        Fragment fragment = new OTPFragment();
        FragmentManager fragmentManager = ((GlobalHome)activity).getSupportFragmentManager();
        FragmentTransaction ft  = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out,R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
        ft.replace(R.id.frame_container, fragment).commit();
    }

    public class MyCountDownTimer extends CountDownTimer {

        public  MyCountDownTimer(long startTime,long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            verifyOTP();
            i++;

        }

        @Override
        public void onFinish() {
            otpVerificationDialog.dismiss();
            if (!getResult())
            {
                //progress.setMessage("Couldn't Read OTP");
                //progress.dismiss();
                if(as != null){
                    as.cancel(true);
                }
                setDialogFragment();

//                btverify.setText("VERIFY");
//                btverify.setActivated(true);
            }


        }
    }


}
