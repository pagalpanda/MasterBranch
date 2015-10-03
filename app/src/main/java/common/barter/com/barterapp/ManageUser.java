package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ManageUser extends Fragment {

    Activity context;
    private EditText etemail;
    private TextView btchangePwd;
    private EditText etphone;
    private ImageButton btverify;
    private EditText etname;
    private RadioButton rbmale;
    private RadioButton rbfemale;
    private EditText etOTP;
    private Button btSave;
    private ImageView ivVerified;


    private OTPVerificationDialog otpVerificationDialog;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null)
            menu.findItem(R.id.action_search).setVisible(false);
        menu.clear();
        inflater.inflate(R.menu.menu_test_db, menu);
//        if (menu.findItem(R.id.action_sign_up) != null)
//            menu.findItem(R.id.action_sign_up).setVisible(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            //logout user
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    new CommonResources(context).logoutUserFromAllAccounts();
                }
            });
            t.start();
            navigateToLogin();

        }
        return false;
    }

    private void navigateToLogin() {
        Fragment fragment = new LoginParentFragment();
        FragmentManager fragmentManager = ((GlobalHome)getActivity()).getSupportFragmentManager();
        FragmentTransaction ft  = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_left,R.anim.abc_fade_out,R.anim.enter_from_left,R.anim.abc_fade_out);
        ft.replace(R.id.frame_container, fragment).commit();
    }

    public ManageUser( ){


    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.manage_user, container, false);
//        MenuItem item = menu.findItem(R.id.my_item);
//        item.setVisible(false);

        setHasOptionsMenu(true);

        etemail = (EditText)rootView.findViewById(R.id.etEmailIdlogin);
        etphone = (EditText)rootView.findViewById(R.id.etPhone);
        etname = (EditText)rootView.findViewById(R.id.etName);
        btchangePwd = (TextView)rootView.findViewById(R.id.btChangePwd);
        btverify = (ImageButton)rootView.findViewById(R.id.btVerify);
        rbmale = (RadioButton)rootView.findViewById(R.id.rbMale);
        rbfemale = (RadioButton)rootView.findViewById(R.id.rbFemale);
        btSave = (Button)rootView.findViewById(R.id.btSaveManageUser);
        ivVerified= (ImageView)rootView.findViewById(R.id.ivVerified);
        otpVerificationDialog = new OTPVerificationDialog(getContext());
        otpVerificationDialog.setCancelable(false);
        getDetails();

        btverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flash("Button clicked");
                LoginDetails.getInstance().setIsverifying(true);
                receiveWebOTP();
//                createProgressBarProperties();
//                progress.setMessage("Sending OTP");
//                progress.show();




            }
        });

//        btverify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //etOTP.setVisibility(View.VISIBLE);
//                flash("Button clicked");
//                LoginDetails.getInstance().setIsverifying(true);
//                //setPopUp(inflater);
//                setDialogFragment();
//
//            }
//        });

        btchangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }

    public boolean validateInput() {

        return true;
    }
    public void getDetails() {


//        etemail.setText("asdasd@ghgg.com");
//        etname.setText("vvbvvbvb");
//        etphone.setText("7032910032"); // Test
//        btverify.setVisibility(View.VISIBLE);
//        rbmale.setChecked(true);
//
//        Commented to test
        String email = LoginDetails.getInstance().getEmail();
        etemail.setText(email);
        String name = LoginDetails.getInstance().getPersonName();
        etname.setText(name);
        String phoneNum = LoginDetails.getInstance().getMobilenum();
        if(phoneNum!=null)
            etphone.setText(phoneNum); // Test
        if((LoginDetails.getInstance().getGender())!=null)
        {
            if (LoginDetails.getInstance().getGender().equalsIgnoreCase("M"))
            {
                rbmale.setChecked(true);
            }

            if (LoginDetails.getInstance().getGender().equalsIgnoreCase("F"))
            {
                rbfemale.setChecked(true);
            }
        }
        if (LoginDetails.getInstance().getMobilenum() !=null)
        {
            if (LoginDetails.getInstance().getMob_verified().equals("0"))
            {
                btverify.setVisibility(View.VISIBLE);
                ivVerified.setVisibility(View.GONE);
            }
            else
            {
                ivVerified.setVisibility(View.VISIBLE);
                btverify.setVisibility(View.GONE);
            }

        }

    }




    public  void flash(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public void updateUser()
    {


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid",LoginDetails.getInstance().getUserid());

        params.put("personname",LoginDetails.getInstance().getPersonName());
        params.put("gender",LoginDetails.getInstance().getGender());
        params.put("birthdate",LoginDetails.getInstance().getBirthday());
        params.put("mobilenum",LoginDetails.getInstance().getMobilenum());

        params.put("instruction", "2");
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
                        otpVerificationDialog.dismiss();
                    }
                    else {
                        LoginDetails.getInstance().setIsverifying(false);
                        otpVerificationDialog.dismiss();

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

        if (fragment != null) {

//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_container, fragment).addToBackStack("Manage User").commit();
            getFragmentManager().beginTransaction()
                    .add(R.id.frame_container, fragment).commit();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating Manage user fragment");
        }


    }


    public void receiveWebOTP() {
        //createProgressBarProperties();
        otpVerificationDialog.show();
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
                        CountDownTimer countDownTimer = new MyCountDownTimer(startTime, interval);
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
    private Boolean result = false;

    public Boolean getResult() {
        return result;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }

    int i = 1;
    public class MyCountDownTimer extends CountDownTimer {

        public  MyCountDownTimer(long startTime,long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if (!getResult())
            {

            }
//            progress.setMessage("Waiting for OTP \nResend OTP in "+( (startTime - i*interval)/1000)+" s");
            i++;
            verifyOTP();
        }

        @Override
        public void onFinish() {
            if (!getResult())
            {
                //progress.setMessage("Couldn't Read OTP");
                //progress.dismiss();
                setDialogFragment();
                otpVerificationDialog.dismiss();
//                btverify.setText("VERIFY");
//                btverify.setActivated(true);
            }


        }
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
                    //setProgress(50, "Verifying OTP");
                    //btverify.setActivated(false);
                    //btverify.setText("VERIFYING");
                    doOTPVerification();

                }

            }
        }
        else if ( (LoginDetails.getInstance().getMob_verified()!=null) && (LoginDetails.getInstance().getMob_verified().equalsIgnoreCase("1")) )
        {
            otpVerificationDialog.show();
            setResult(true);
            //setProgress(100,"Verified");
            //btverify.setActivated(false);
            //btverify.setText("VERIFIED");
        }

    }
    private final long startTime = 10 * 1000;
    private final long interval = 1 * 1000;
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
                        otpVerificationDialog.dismiss();
                        getFragmentManager().popBackStack();
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

}