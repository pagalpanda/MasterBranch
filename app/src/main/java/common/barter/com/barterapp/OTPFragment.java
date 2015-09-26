package common.barter.com.barterapp;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by vikram on 20/09/15.
 */
public class OTPFragment extends Fragment {

    Activity context;
    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private final long startTime = 300 * 1000;
    private final long interval = 1 * 1000;
    private IncomingSms incomingSms = new IncomingSms();
    private Boolean result = false;

    private Button btverify;
    private EditText etmobilenum;
    private EditText etotp;
    private EditText etstatus;
    private ProgressBar pbstatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View dialogFragmentView = inflater.inflate(R.layout.otp_popup, container, false);
        receiveWebOTP();
        etotp = (EditText) dialogFragmentView.findViewById(R.id.etotp);
        etmobilenum = (EditText) dialogFragmentView.findViewById(R.id.etmobilenum);
        etstatus = (EditText) dialogFragmentView.findViewById(R.id.etstatus);
        pbstatus = (ProgressBar) dialogFragmentView.findViewById(R.id.pbstatus);
        btverify = (Button) dialogFragmentView.findViewById(R.id.btverify);

        setinputData();

        return dialogFragmentView;

    }


    public class MyCountDownTimer extends CountDownTimer {

        public  MyCountDownTimer(long startTime,long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (!getResult())
            {
                btverify.setText("SEND AGAIN IN " + millisUntilFinished / 1000);
            }
            verifyOTP();
        }

        @Override
        public void onFinish() {
            if (!getResult())
            {
                btverify.setText("VERIFY");
                btverify.setActivated(true);
            }


        }
    }
    public  void flash(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public void registerBroadcastReceiver(View view) {

        context.registerReceiver(incomingSms, new IntentFilter(
                "android.provider.Telephony.SMS_RECEIVED"));
        flash("Registered broadcast receiver");
    }

    /**
     * This method disables the Broadcast receiver
     *
     * @param view
     */
    public void unregisterBroadcastReceiver(View view) {

        context.unregisterReceiver(incomingSms);
        flash("unregistered broadcst receiver");
    }

    public void receiveWebOTP() {

        //test data
        LoginDetails.getInstance().testData();
        LoginDetails.getInstance().setIsverifying(true);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid",LoginDetails.getInstance().getUserid());
        params.put("mobilenum",LoginDetails.getInstance().getMobilenum());
        params.put("instruction", "0");
        AsyncConnection as = new AsyncConnection(context,CommonResources.getURL("UserHandler"),"POST",params,false,null){
            public void receiveData(JSONObject json){
                try {
                    String TAG_SUCCESS = "success";
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 0) {
                        LoginDetails.getInstance().setOtp_received_from_web(json.getString("otp"));
                        LoginDetails.getInstance().setIsverifying(true);
                    }
                    else if (success == 1) {

                        LoginDetails.getInstance().setIsverifying(false);
                    }
                    else {
                        LoginDetails.getInstance().setIsverifying(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();

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

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public void setinputData() {

        etmobilenum.setText(LoginDetails.getInstance().getMobilenum());
        pbstatus.setMax(100);
        if (LoginDetails.getInstance().getIsverifying())
        {
            pbstatus.setProgress(25);
            etstatus.setText("Waiting for OTP.");
            btverify.setActivated(false);
            btverify.setBackgroundColor(getResources().getColor(R.color.gray));

            countDownTimer = new MyCountDownTimer(startTime, interval);
            countDownTimer.start();
        }
        else
        {
            pbstatus.setProgress(0);
            etstatus.setText("Regenerate OTP.");
            btverify.setActivated(true);
            btverify.setText("VERIFY");
            btverify.setBackgroundColor(getResources().getColor(R.color.green));
        }
    }

    public void setProgress(int progress,String status) {
        pbstatus.setProgress(progress);
        etstatus.setText(status);

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
                    setProgress(50, "Verifying OTP");
                    btverify.setActivated(false);
                    btverify.setText("VERIFYING");
                    doOTPVerification();

                }

            }
        }
        else if ( (LoginDetails.getInstance().getMob_verified()!=null) && (LoginDetails.getInstance().getMob_verified().equalsIgnoreCase("1")) )
        {
            setResult(true);
            setProgress(100,"Verified");
            btverify.setActivated(false);
            btverify.setText("VERIFIED");
        }

    }


    public void doOTPVerification()
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid",LoginDetails.getInstance().getUserid());
        params.put("mobilenum",LoginDetails.getInstance().getMobilenum());
        params.put("otp",LoginDetails.getInstance().getOtp_received_from_device());
        params.put("instruction", "1");
        AsyncConnection as = new AsyncConnection(context,CommonResources.getURL("UserHandler"),"POST",params,false,null){
            public void receiveData(JSONObject json){
                try {
                    String TAG_SUCCESS = "success";
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 0) {
                        LoginDetails.getInstance().setMob_verified(json.getString("is_verified"));
                        LoginDetails.getInstance().setIsverifying(false);
                    }
                    else if (success == 1) {

                        LoginDetails.getInstance().setIsverifying(false);
                    }
                    else {
                        LoginDetails.getInstance().setIsverifying(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();

    }

}
