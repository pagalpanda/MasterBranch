package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    private String newName;
    private String newMobileNum;
    private String newGender;

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewMobileNum() {
        return newMobileNum;
    }

    public void setNewMobileNum(String newMobileNum) {
        this.newMobileNum = newMobileNum;
    }

    public String getNewGender() {
        return newGender;
    }

    public void setNewGender(String newGender) {
        this.newGender = newGender;
    }


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
        ft.setCustomAnimations(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out,R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
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
        btverify = (ImageButton)rootView.findViewById(R.id.btVerifyManageUser);
        rbmale = (RadioButton)rootView.findViewById(R.id.rbMale);
        rbfemale = (RadioButton)rootView.findViewById(R.id.rbFemale);
        btSave = (Button)rootView.findViewById(R.id.btSaveManageUser);
        ivVerified= (ImageView)rootView.findViewById(R.id.ivVerified);
        // TEST for OTPAdapter
//        otpVerificationDialog = new OTPVerificationDialog(getContext());
//        otpVerificationDialog.setCancelable(false);

        getDetails();
        etphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String currentNum = s == null ? null : s.toString();
                if (currentNum != null) {
                    if (CommonResources.isValidMobile(currentNum) && !currentNum.equalsIgnoreCase(LoginDetails.getInstance().getMobilenum())) {
                        btverify.setVisibility(View.VISIBLE);
                        btverify.setImageResource(R.drawable.search);
                    } else if (CommonResources.isValidMobile(currentNum) && currentNum.equalsIgnoreCase(LoginDetails.getInstance().getMobilenum())) {
                        if ("0".equalsIgnoreCase(LoginDetails.getInstance().getMob_verified())) {
                            btverify.setVisibility(View.VISIBLE);
                            btverify.setImageResource(R.drawable.search);
                        } else {
                            btverify.setVisibility(View.VISIBLE);
                            btverify.setImageResource(R.drawable.home);
                        }
                    } else {
                        btverify.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPhoneEntered = etphone.getText().toString();
                if(newPhoneEntered !=null && CommonResources.isValidMobile(newPhoneEntered) && !(newPhoneEntered.equalsIgnoreCase(LoginDetails.getInstance().getMobilenum()) && "1".equalsIgnoreCase(LoginDetails.getInstance().getMob_verified()) )) {
                    LoginDetails.getInstance().setIsverifying(true);
                    LoginDetails.getInstance().setMob_verified("0");
                    LoginDetails.getInstance().setMobilenum(newPhoneEntered);
                    new CommonResources(context).saveToSharedPrefs(MessagesString.SHARED_PREFS_MOBILE, LoginDetails.getInstance().getMobilenum());

                    OTPVerificationAdapter otpVerificationAdapter = new OTPVerificationAdapter(getActivity(),ManageUser.this);
                    otpVerificationAdapter.generateOTP();
                }else {
                    etphone.setText(LoginDetails.getInstance().getMobilenum());
                }
            }
        });

        if("2".equalsIgnoreCase(LoginDetails.getInstance().getLoginMethod())) //  change pass is visible only in case of manual login/signup
            btchangePwd.setVisibility(View.VISIBLE);
        else
            btchangePwd.setVisibility(View.GONE);

        btchangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ChangePassword();

                FragmentManager fragmentManager = ((GlobalHome)getActivity()).getSupportFragmentManager();
                FragmentTransaction ft  = fragmentManager.beginTransaction();
                ft.setCustomAnimations(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out,R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
                ft.add(R.id.frame_container, fragment).addToBackStack("change_pwd").commit();
                // ChangePassword fragment
            }
        });


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput())
                {
                    if(hasDetailsChanged()){
                        //save the data to db
                        saveData();
                    }else{
                        Toast.makeText(getContext(),"Data upto-date",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(),"Wrong data",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return rootView;
    }

    private boolean hasDetailsChanged() {
         if( hasGenderChanged()||hasMobileNumChanged()||hasNameChanged())
             return true;
        else
            return false;

    }

    public void setVerifyImage(boolean isVerified){
        if(isVerified)
            btverify.setImageResource(R.drawable.home);
        else
            btverify.setImageResource(R.drawable.search);
    }
    private boolean hasNameChanged() {
        String newName = etname.getText().toString();
        if( !newName.equals(LoginDetails.getInstance().getPersonName()))
            return true;
        else
            return false;

    }
    private boolean hasMobileNumChanged() {
        String newPhoneEntered = etphone.getText().toString();
        if(!newPhoneEntered.equalsIgnoreCase(LoginDetails.getInstance().getMobilenum())) {
            return true;
        }else
        return false;

    }

    private boolean hasGenderChanged() {
        String newGender = rbmale.isChecked()?"M":"F";
        if(!newGender.equalsIgnoreCase(LoginDetails.getInstance().getGender())){
            return true;
        }else
            return false;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }

    @Override
    public void onResume() {
        super.onResume();
        getDetails();
    }

    public boolean validateInput() {
        setNewGender(rbmale.isChecked() ? "M" : "F");
        setNewMobileNum(etphone.getText().toString());
        setNewName(etname.getText().toString());
        if (getNewMobileNum() !=null && !("".equalsIgnoreCase(getNewMobileNum())) && !(CommonResources.isValidMobile(getNewMobileNum()) ) )
        {
            etphone.setError("Please enter correct mobile num");
            return false;
        }
        if (getNewName() ==null ||  "".equalsIgnoreCase(getNewName()))
        {
            etname.setError("Please enter valid name");
            return false;
        }
        return true;
    }
    public void getDetails() {


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
        if (LoginDetails.getInstance().getMobilenum() !=null && !"".equalsIgnoreCase(LoginDetails.getInstance().getMobilenum()))
        {
            btverify.setVisibility(View.VISIBLE);
            if (LoginDetails.getInstance().getMob_verified().equals("0"))
            {
//                btverify.setVisibility(View.VISIBLE);
//                ivVerified.setVisibility(View.GONE);
                btverify.setImageResource(R.drawable.search);
            }
            else
            {
                btverify.setImageResource(R.drawable.home);
//                ivVerified.setVisibility(View.VISIBLE);
//                btverify.setVisibility(View.GONE);
            }

        }else{
            btverify.setVisibility(View.GONE);
        }

    }

AsyncConnection as;


//    public void receiveWebOTP() {
//        //createProgressBarProperties();
//        otpVerificationDialog.show();
//        //test data
//        //LoginDetails.getInstance().testData();
//        LoginDetails.getInstance().setIsverifying(true);
//
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("userid",LoginDetails.getInstance().getUserid());
//        params.put("mobilenum",LoginDetails.getInstance().getMobilenum());
//        params.put("instruction", "0");
//        as = new AsyncConnection(context,CommonResources.getURL("UserHandler"),"POST",params,false,null){
//            public void receiveData(JSONObject json){
//                try {
//                    String TAG_SUCCESS = "success";
//                    int success = json.getInt(TAG_SUCCESS);
//                    if (success == 0) {
//                        LoginDetails.getInstance().setOtp_received_from_web(json.getString("otp"));
//                        LoginDetails.getInstance().setIsverifying(true);
//                        CountDownTimer countDownTimer = new MyCountDownTimer(startTime, interval);
//                        countDownTimer.start();
//
//                        //Add timer to progress bar
//
//                    }
//                    else if (success == 1) {
//                        otpVerificationDialog.dismiss();
//                        LoginDetails.getInstance().setIsverifying(false);
//                        //Disable Progress bar
//                    }
//                    else {
//                        otpVerificationDialog.dismiss();
//                        LoginDetails.getInstance().setIsverifying(false);
//                        //Disable Progress bar
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        as.execute();
//
//    }
    private Boolean result = false;

    public Boolean getResult() {
        return result;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }

    int i = 1;
//    public class MyCountDownTimer extends CountDownTimer {
//
//        public  MyCountDownTimer(long startTime,long interval)
//        {
//            super(startTime, interval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            verifyOTP();
//            i++;
//
//        }
//
//        @Override
//        public void onFinish() {
//            otpVerificationDialog.dismiss();
//            if (!getResult())
//            {
//                //progress.setMessage("Couldn't Read OTP");
//                //progress.dismiss();
//                if(as != null){
//                    as.cancel(true);
//                }
//                //setDialogFragment();
//
////                btverify.setText("VERIFY");
////                btverify.setActivated(true);
//            }
//
//
//        }
//    }
    boolean isExecuted;
//    public void verifyOTP()
//    {
//        if (LoginDetails.getInstance().getIsverifying())
//        {
//            if( (LoginDetails.getInstance().getOtp_received_from_device()!=null) && (LoginDetails.getInstance().getOtp_received_from_web()!=null))
//            {
//
//                if(!isExecuted){
//                    isExecuted = true;
//                    setResult(true);
//                    doOTPVerification();
//
//                }
//
//            }
//        }
//        else if ( (LoginDetails.getInstance().getMob_verified()!=null) && (LoginDetails.getInstance().getMob_verified().equalsIgnoreCase("1")) )
//        {
//            setResult(true);
//        }
//
//    }
//    private final long startTime = 25 * 1000;
//    private final long interval = 1 * 1000;
//    AsyncConnection as;

//    public void doOTPVerification()
//    {
//
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("userid",LoginDetails.getInstance().getUserid());
//        params.put("mobilenum",LoginDetails.getInstance().getMobilenum());
//        params.put("otp",LoginDetails.getInstance().getOtp_received_from_device());
//        params.put("instruction", "1");
//        as = new AsyncConnection(context,CommonResources.getURL("UserHandler"),"POST",params,false,null){
//            public void receiveData(JSONObject json){
//                try {
//                    String TAG_SUCCESS = "success";
//                    int success = json.getInt(TAG_SUCCESS);
//                    if (success == 0) {
//                        LoginDetails.getInstance().setMob_verified(json.getString("is_verified"));
//                        new CommonResources(context).saveToSharedPrefs(MessagesString.SHARED_PREFS_IS_MOBILE_VERIFIED, LoginDetails.getInstance().getMob_verified());
//                        LoginDetails.getInstance().setIsverifying(false);
//                        otpVerificationDialog.dismiss();
//                        btverify.setImageResource(R.drawable.home);
//                        //getFragmentManager().popBackStack();
//                    }
//                    else if (success == 1) {
//                        otpVerificationDialog.dismiss();
//                        LoginDetails.getInstance().setIsverifying(false);// OTP Ver failed : Try Again Later
//                    }
//                    else {
//                        otpVerificationDialog.dismiss();
//                        LoginDetails.getInstance().setIsverifying(false);// OTP Ver failed : Try Again Later
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        as.execute();
//
//
//    }

    public void saveData()
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid",LoginDetails.getInstance().getUserid());
        if (hasMobileNumChanged())
            params.put("mobilenum", (getNewMobileNum()==null || "".equalsIgnoreCase(getNewMobileNum())) ?"0":getNewMobileNum());
        if (hasGenderChanged())
            params.put("gender",rbmale.isChecked()?"M":"F");
        if (hasNameChanged())
            params.put("personname",etname.getText().toString());
        params.put("instruction", "4");
        as = new AsyncConnection(context,CommonResources.getURL("UserHandler"),"POST",params,true,""){
            public void receiveData(JSONObject json){
                try {
                    String TAG_SUCCESS = "success";
                    int success = json.getInt(TAG_SUCCESS);
                    if (success == 0) {
                        setLoginDetailsData(json);
                        Toast.makeText(getContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                    }
                    else if (success == 1) {
                        // Error
                        Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // Error
                        Toast.makeText(getContext(),"Facing Error",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();
    }

    public void setLoginDetailsData(JSONObject json)
    {
        try{
            CommonResources resources = new CommonResources(context);
            //resources.saveToSharedPrefs("isLoggedIn", "true");
            Map<String,String> mapUserDetails  = new HashMap<>();
            String userId = json.getString("userid");
            mapUserDetails.put(MessagesString.SHARED_PREFS_UNIQUE_ID,userId);
            String personName = json.getString("name");
            mapUserDetails.put(MessagesString.SHARED_PREFS_PERSON_NAME,personName);
            String gender = json.getString("gender");
            mapUserDetails.put(MessagesString.SHARED_PREFS_GENDER,gender);
            String email = json.getString("username");
            mapUserDetails.put(MessagesString.SHARED_PREFS_EMAIL, email);
            String username = json.getString("username");
            mapUserDetails.put(MessagesString.SHARED_PREFS_USERNAME,username);
            String mobileNum = json.getString("mobilenum");
            mapUserDetails.put(MessagesString.SHARED_PREFS_MOBILE,mobileNum);
            String mobVerified = json.getString("mob_verified");
            mapUserDetails.put(MessagesString.SHARED_PREFS_IS_MOBILE_VERIFIED,mobVerified);
            String loginMethod = json.getString("loginmode");
            mapUserDetails.put(MessagesString.SHARED_PREFS_LOGIN_MODE,loginMethod);
            resources.setUserDetailsInSharedPref(mapUserDetails);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}