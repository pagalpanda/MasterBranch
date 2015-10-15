package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,GoogleplusListener {

    private int tabSelected = 1;
    Activity context;
    Button btnLogin;
    TextView tvForgotPwd;
    EditText etEmailID;
    EditText etPassword;
    EditText etPasswordConf;
    TextView tvGoogleLoginTxt;
    TextView tvFBLoginTxt;
    EditText etFirstName;

    EditText etMobileNum;
    ScrollView scrollViewLogin;
    TextView tvSignUpTermsAndConditions;
    RadioGroup rgGender;
    RadioButton rbGenderMale;
    RadioButton rbGenderFemale;
    CommonResources resource;
    //CheckBox ckNewUser;

    String email;
    String pwd;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    // FB Sign in
    LoginButton authButton;
    private CallbackManager callbackManager;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    private static final String TAG_SUCCESS = "success";


    private static int  login_mode =0; //0 - gmail, 1- fb, 2 - manual, 3 - newuser


//    @Override
//    public void onCrea(Menu menu) {
//        super.onPrepareOptionsMenu(menu);
//        if (menu.findItem(R.id.action_search) != null)
//            menu.findItem(R.id.action_search).setVisible(false);
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null)
            menu.findItem(R.id.action_search).setVisible(false);
//        if (menu.findItem(R.id.action_sign_up) != null)
//            menu.findItem(R.id.action_sign_up).setVisible(true);
    }

    public LoginFragment(){

    }
    public LoginFragment( int tabSelected ){
        this();
        this.tabSelected = tabSelected;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getContext());
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
//        MenuItem item = menu.findItem(R.id.my_item);
//        item.setVisible(false);

        setHasOptionsMenu(true);
        initializeWidgets(rootView);

        if(tabSelected == 1){// User in Login Mode
            setLayoutForLoginMode();
        }else if(tabSelected == 2){ //  User in Sign Up mode
            setLayoutForSignUpMode();
        }

        return rootView;
    }

    private void initializeWidgets(View rootView){
        btnLogin = (Button)rootView.findViewById(R.id.btnLoginLogin);
        tvForgotPwd = (TextView)rootView.findViewById(R.id.tvforgotpwd);
        etEmailID = (EditText)rootView.findViewById(R.id.etEmailIdlogin);
        etPassword = (EditText)rootView.findViewById(R.id.etPasswordLogin);
        etPasswordConf = (EditText)rootView.findViewById(R.id.etConfPassword);
        tvGoogleLoginTxt = (TextView)rootView.findViewById(R.id.tvGoogleLoginTxt);
        tvFBLoginTxt = (TextView)rootView.findViewById(R.id.tvFBLoginTxt);
        etFirstName = (EditText)rootView.findViewById(R.id.etFirstName);

        etMobileNum = (EditText) rootView.findViewById(R.id.etMobileNum);
        scrollViewLogin = (ScrollView)rootView.findViewById(R.id.scrollViewLogin);
        tvSignUpTermsAndConditions = (TextView)rootView.findViewById(R.id.tvSignUpTermsAndConditions);
        rgGender = (RadioGroup)rootView.findViewById(R.id.rgGender);
        rbGenderMale = (RadioButton)rootView.findViewById(R.id.rbGenderMale);
        rbGenderFemale = (RadioButton)rootView.findViewById(R.id.rbGenderFemale);
        rbGenderMale.setChecked(true);
        resource = new CommonResources(context);
//        scrollViewLogin.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
////                int scrollY = scrollViewLogin.getScrollY();
////                ((GlobalHome)getActivity()).getSupportActionBar().hide();
//
//                    CommonResources.hideKeyboard(getActivity());
//            }
//        });
        //ckNewUser = (CheckBox)rootView.findViewById(R.id.ckNewUser);




        // FB Login

        callbackManager = CallbackManager.Factory.create();
        authButton = (LoginButton) rootView.findViewById(R.id.authButton);
        //setFBButtonProperties();
        authButton.setFragment(this);
        authButton.setReadPermissions(Arrays.asList("public_profile"));
        authButton.setReadPermissions(Arrays.asList("user_birthday"));
        authButton.setReadPermissions(Arrays.asList("email"));
        //rootView.findViewById(R.id.com_facebook_login_activity_progress_bar).setVisibility(View.GONE);
        authButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createProcessDialog("Connecting to Facebook");
            }
        });

        authButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                CommonResources.hideKeyboard(getActivity());
                //flash("FB Success");
                new FBLoginAsync(getContext(), getFragmentManager(), loginResult,pDialog).execute();
                authButton.clearPermissions();
            }

            @Override
            public void onCancel() {
                //flash("FB Cancel");
                dismissProcessDialog();
                CommonResources.hideKeyboard(getActivity());
            }

            @Override
            public void onError(FacebookException e) {
                flash("Error occured. Please try again later");
                dismissProcessDialog();
                CommonResources.hideKeyboard(getActivity());
                //flash(e.toString());
            }
        });


        RelativeLayout btnGoogleSignIn = (RelativeLayout)rootView.findViewById(R.id.sign_in_button);
        btnGoogleSignIn.setOnClickListener(this);

        //setGooglePlusButtonText(btnGoogleSignIn);//For setting the text of the google+ sign in button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonResources.hideKeyboard(getActivity());
                Toast.makeText(context, "Login Clicked", Toast.LENGTH_SHORT).show();
                String email = etEmailID.getText().toString();
                String pwd = etPassword.getText().toString();

                if(tabSelected == 2){
                    String name = etFirstName.getText().toString();
                    etFirstName.setError("Please enter your name!");
                    return;
                }


                if (tabSelected == 2 && !validateNewUserPassword()) {
                    etPasswordConf.setError("Password mismatch");
                    return;
                }
                if (!isValidEmail(email)) {
                    etEmailID.setError("Invalid Email");
                } else {
                    if (!isValidPassword(pwd)) {
                        // DO Nothing
                    } else {
                        // Read Mobile# and other important information
                        LoginDetails.getInstance().resetDetails();
                        LoginDetails.getInstance().setEmail(email);
                        LoginDetails.getInstance().setPassword(pwd);
                        LoginDetails.getInstance().setPersonName(etFirstName.getText().toString().trim());
                        LoginDetails.getInstance().setMobilenum(etMobileNum.getText().toString().trim());
                        LoginDetails.getInstance().setGender(rbGenderMale.isChecked() ? "m" : "f");

                        if (tabSelected == 2)
                            setLogin_mode(3);
                        else
                            setLogin_mode(2);

                        addUser(1);
                    }
                }
            }
        });
        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resource.hideKeyboard(getActivity());
                Fragment fragment = new ForgotPassword();

                if(fragment!=null)
                {
                    getFragmentManager().beginTransaction()
                            .add(R.id.frame_container, fragment).addToBackStack("Forgot_Pass").commit();
                }
                else
                {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        });



        etPasswordConf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateNewUserPassword();
                }
            }
        });

    }


    private void setLayoutForLoginMode() {
        etPasswordConf.setVisibility(View.GONE);

        tvForgotPwd.setVisibility(View.VISIBLE);
        btnLogin.setText("Login");
        tvGoogleLoginTxt.setText("Log in via Google+");
        tvFBLoginTxt.setText("Log in via Facebook");
        etFirstName.setVisibility(View.GONE);

        etMobileNum.setVisibility(View.GONE);
        tvSignUpTermsAndConditions.setVisibility(View.GONE);
        rgGender.setVisibility(View.GONE);

    }
    private void setLayoutForSignUpMode(){
        etPasswordConf.setVisibility(View.VISIBLE);
        tvForgotPwd.setVisibility(View.GONE);
        //authButton.setText("Sign up via Facebook");
        etFirstName.setVisibility(View.VISIBLE);

        etMobileNum.setVisibility(View.VISIBLE);
        btnLogin.setText("Sign Up");
        tvGoogleLoginTxt.setText("Sign up via Google+");
        tvFBLoginTxt.setText("Sign up via Facebook");
        tvSignUpTermsAndConditions.setVisibility(View.VISIBLE);
        rgGender.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isValidPassword(String pass) {
        if (pass != null && !pass.equals("") && pass.length() > 7) {
            return true;
        }
        if (pass.length() < 8)
        {
            etPassword.setError("Password should be 8 characters long");
        }
        else if (pass == null || pass.equals(""))
        {
            etPassword.setError("Password should not be blank");
        }
        else
        {
            etPassword.setError("Invalid Password");
        }
        return false;
    }
    public boolean validateNewUserPassword() {
        String pwd = etPassword.getText().toString();
        String pwdConf = etPasswordConf.getText().toString();
        if (pwd.equals(pwdConf))
        {
            return true;
        }
        etPasswordConf.setError("Password Mismatch");
        return false;
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            CommonResources.hideKeyboard(getActivity());
            onSignInClicked();
        }

    }

    private void createProcessDialog(String msg) {
        if(pDialog==null){
            pDialog = ProgressDialog.show(context,"",msg,true,true);
        } else {
            pDialog.setMessage(msg);
        }

        if ( !(pDialog.isShowing()) )
            pDialog.show();
    }

    private void dismissProcessDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void onSignInClicked() {
        //flash("Signing in");
        mIsResolving = false;
        createProcessDialog("Connecting to Google..");
        buildGoogleApiClient();
        if (mGoogleApiClient !=null)
        {
            mGoogleApiClient.connect();
        }
        else {
            flash("Please check Connectivity");
            dismissProcessDialog();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != Activity.RESULT_OK) {
                mShouldResolve = false;
            }
            else {
                mShouldResolve = true;
                createProcessDialog("Connecting to Google..");
            }
            mIsResolving = false;
            if(mShouldResolve) {
                if (mGoogleApiClient == null) {
                    buildGoogleApiClient();
                    mGoogleApiClient.connect();
                } else {
                    mGoogleApiClient.connect();
                }
            }

        }
        else
        {
            //flash("onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void showSignedOutUI() {
        //flash("showSignedOutUI");
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

    }

    public  void flash(String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public void setLogin_mode(int login_mode)
    {
        this.login_mode =login_mode;

    }
    public void addUser(int cond)
    {
        new LoginAsync(getContext(),login_mode,getFragmentManager(),null ).execute();
    }



    private GoogleApiClient mGoogleApiClient;
        /* Is there a ConnectionResult resolution in progress? */
        private boolean mIsResolving = false;

        /* Should we automatically resolve ConnectionResults when possible? */
        private boolean mGoogleAPIConnected = false;
        //Google sign in
        private static final int RC_SIGN_IN = 14;
        public void loginUser()
        {
            new LoginAsync(context,0,getFragmentManager(),pDialog ).execute();
            showSignedOutUI();
        }

        protected synchronized void buildGoogleApiClient() {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
            //flash("onConnected:" + bundle);
            mGoogleAPIConnected=true;
            getGoogleInfo();

            // Show the signed-in UI
        }

        private void getGoogleInfo() {

            if (mGoogleApiClient.isConnected()) {
                if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                    createProcessDialog("Receiving Data");
                    Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                    LoginDetails.getInstance().resetDetails();
                    LoginDetails.getInstance().setPersonName(currentPerson.getDisplayName());
                    LoginDetails.getInstance().setPersonPhoto(currentPerson.getImage().getUrl());
                    LoginDetails.getInstance().setEmail(Plus.AccountApi.getAccountName(mGoogleApiClient));
                    LoginDetails.getInstance().setBirthday(currentPerson.getBirthday());
                    LoginDetails.getInstance().setLoginLocation(currentPerson.getCurrentLocation());
                    LoginDetails.getInstance().setGender(currentPerson.getGender() == 0 ? "M" : "F");
                    LoginDetails.getInstance().setId(currentPerson.getId());

                    LoginDetails.getInstance().setPassword(LoginDetails.getInstance().getEmail().concat("123"));
                    loginUser();
                } else {
                    flash("No information received. Please try again");
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
            //flash("onConnectionFailed:" + connectionResult);

            if (!mIsResolving) {

                if (connectionResult.hasResolution()) {
                    try {
                        dismissProcessDialog();
                        connectionResult.startResolutionForResult(getActivity(), RC_SIGN_IN);
                        mIsResolving = true;
                       // mGoogleApiClient.connect();
                    } catch (IntentSender.SendIntentException e) {
                        //flash("Could not resolve ConnectionResult:" + e);
                        mIsResolving = false;
                        mGoogleApiClient.connect();
                    }
                } else {
                    // Could not resolve the connection result, show the user an
                    // error dialog.
                    //showErrorDialog(connectionResult);
                    dismissProcessDialog();
                    //flash(new Integer(connectionResult.getErrorCode()).toString());
                }
            } else {
                // Show the signed-out UI
                showSignedOutUI();
            }
        }


}