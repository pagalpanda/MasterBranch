package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment implements View.OnClickListener {

    Activity context;
    Button btnLogin;
    TextView tvForgotPwd;
    EditText etEmailID;
    EditText etPassword;
    EditText etPasswordConf;
    CheckBox ckNewUser;

    String email;
    String pwd;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    // FB Sign in
    LoginButton authButton;
    private CallbackManager callbackManager;

    //Google sign in
    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

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

    public LoginFragment( ){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getContext());
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
//        MenuItem item = menu.findItem(R.id.my_item);
//        item.setVisible(false);
        setHasOptionsMenu(true);
        btnLogin = (Button)rootView.findViewById(R.id.btnLoginLogin);
        tvForgotPwd = (TextView)rootView.findViewById(R.id.tvforgotpwd);
        etEmailID = (EditText)rootView.findViewById(R.id.etEmailIdlogin);
        etPassword = (EditText)rootView.findViewById(R.id.etPasswordLogin);
        etPasswordConf = (EditText)rootView.findViewById(R.id.etConfPassword);
        ckNewUser = (CheckBox)rootView.findViewById(R.id.ckNewUser);

        // FB Login

        callbackManager = CallbackManager.Factory.create();
        authButton = (LoginButton) rootView.findViewById(R.id.authButton);
        authButton.setFragment(this);
        authButton.setReadPermissions(Arrays.asList("user_friends"));
        authButton.setReadPermissions(Arrays.asList("public_profile"));
        authButton.setReadPermissions(Arrays.asList("email"));
        authButton.setReadPermissions(Arrays.asList("user_birthday"));
        authButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                flash("FB Success");
                new FBLoginAsync(getContext(),getFragmentManager(),loginResult).execute();
            }

            @Override
            public void onCancel() {
                flash("FB Cancel");
            }

            @Override
            public void onError(FacebookException e) {
                flash("FB Error");
                flash(e.toString());
            }
        });

        rootView.findViewById(R.id.sign_in_button).setOnClickListener(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Login Clicked", Toast.LENGTH_SHORT).show();
                String email = etEmailID.getText().toString();
                String pwd = etPassword.getText().toString();

                if (ckNewUser.isChecked() && !validateNewUserPassword()) {
                    etPasswordConf.setError("Password mismatch");
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
                        if (ckNewUser.isChecked())
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
                Toast.makeText(context,"Forgot Pass Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ckNewUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked ) {
                    etPasswordConf.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Checked", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    etPasswordConf.setVisibility(View.GONE);
                    Toast.makeText(context, "Unchecked", Toast.LENGTH_SHORT).show();
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

        return rootView;
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
            onSignInClicked();
        }

    }
    private void onSignInClicked() {
        flash("Signing in");
        new GplusLoginAsync(getActivity(),getContext(),getFragmentManager()).execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        flash("onActivityResult:" + requestCode + ":" + resultCode + ":" + data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void showSignedOutUI() {
        flash("showSignedOutUI");

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
        new LoginAsync(getContext(),login_mode,getFragmentManager() ).execute();
    }

    /** Hashing the username password
     public String hashUser(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

     String hashUser = SHA1.Sha1Hash(username);
     String hashPass = SHA1.Sha1Hash(password);
     String luser = hashPass+hashUser;
     String lastUser = SHA1.Sha1Hash(luser);
     return lastUser;
     }

     public String hashPass(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
     String hashUser = SHA1.Sha1Hash(username);
     String hashPass = SHA1.Sha1Hash(password);
     String lpass = hashPass+hashUser;
     String lastPass = SHA1.Sha1Hash(lpass);
     return lastPass;
     }
     */
}