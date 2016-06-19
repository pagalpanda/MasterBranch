package common.barter.com.barterapp.Login;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.manageuser.ManageUserFragment;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.forgotpassword.ForgotPassword;

public class LoginFragment extends Fragment implements GoogleplusListener{

    public enum tabs{
        LOGIN,SIGNUP
    }
    private String tabSelected = tabs.LOGIN.name();

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

    LoginButton fbLoginButton;
    private CallbackManager fbCallbackManager;

    LoginPresenter loginPresenter;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null)
            menu.findItem(R.id.action_search).setVisible(false);
    }

    public LoginFragment(){

    }

    public LoginFragment( String tabSelected ){
        this();
        this.tabSelected = tabSelected;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getContext());
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        setHasOptionsMenu(true);
        setPresenter();
        fbCallbackManager = loginPresenter.getFbCallBackManager();
        initializeWidgets(rootView);
        loginPresenter.setLayoutForMode(tabSelected);

        return rootView;
    }

    public String getGender() {
        return rbGenderMale.isChecked() ? "M" : "F";
    }

    public String getMobileNum() {
        return etMobileNum.getText().toString().trim();
    }

    public String getName() {
        return etFirstName.getText().toString();
    }

    public String getTabSelected() {
        return tabSelected;
    }
    public String getEmailId(){
        return etEmailID.getText().toString();
    }
    public String getPassword(){
        return etPassword.getText().toString();
    }
    public String getConfirmationPassword(){
        return etPasswordConf.getText().toString();
    }

    private void setPresenter() {
        if(loginPresenter==null){
            loginPresenter = new LoginPresenter();
        }
        loginPresenter.setLoginFragment(this);
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

        fbLoginButton = (LoginButton) rootView.findViewById(R.id.authButton);
        setFbButtonProperties();

        RelativeLayout btnGoogleSignIn = (RelativeLayout)rootView.findViewById(R.id.sign_in_button);
        btnGoogleSignIn.setOnClickListener(loginPresenter.getGPlusConnectionListener());

        btnLogin.setOnClickListener(loginPresenter.getLoginListener().getBtnLoginOnClickListener());
        tvForgotPwd.setOnClickListener(loginPresenter.getLoginListener().getForgotPwdOnClickListener());
        etPasswordConf.setOnFocusChangeListener(loginPresenter.getLoginListener().getPwdOnFocusChangeListener());

    }

    private void setFbButtonProperties() {
        fbLoginButton.setFragment(this);
        fbLoginButton.setReadPermissions(Arrays.asList("public_profile"));
        fbLoginButton.setReadPermissions(Arrays.asList("user_birthday"));
        fbLoginButton.setReadPermissions(Arrays.asList("email"));
        fbLoginButton.setOnClickListener(loginPresenter.getLoginListener().getFbOnClickListener());
        fbLoginButton.registerCallback(fbCallbackManager, loginPresenter.getLoginListener());
    }

    void setLayoutForLoginMode() {
        etPasswordConf.setVisibility(View.GONE);
        tvForgotPwd.setVisibility(View.VISIBLE);
        btnLogin.setText(MessagesString.BT_LOGIN);
        tvGoogleLoginTxt.setText(MessagesString.BT_GPLUS_LOGIN);
        tvFBLoginTxt.setText(MessagesString.BT_FB_LOGIN);
        etFirstName.setVisibility(View.GONE);
        etMobileNum.setVisibility(View.GONE);
        tvSignUpTermsAndConditions.setVisibility(View.GONE);
        rgGender.setVisibility(View.GONE);
    }

    void setLayoutForSignUpMode(){
        etPasswordConf.setVisibility(View.VISIBLE);
        tvForgotPwd.setVisibility(View.GONE);
        etFirstName.setVisibility(View.VISIBLE);
        etMobileNum.setVisibility(View.VISIBLE);
        btnLogin.setText(MessagesString.BT_SIGNUP);
        tvGoogleLoginTxt.setText(MessagesString.BT_GPLUS_SIGNUP);
        tvFBLoginTxt.setText(MessagesString.BT_FB_SIGNUP);
        tvSignUpTermsAndConditions.setVisibility(View.VISIBLE);
        rgGender.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPlusConnectionListener.gPlusSigninId) {
            loginPresenter.grantPermissionAndReconnect(resultCode);
        }
        else
        {
            fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void navigateToForgotPwd() {
        CommonResources.hideKeyboard(getActivity());
        Fragment fragment = new ForgotPassword();

        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.frame_container, fragment).addToBackStack(MessagesString.FRAG_FORGOT_PWD).commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", MessagesString.FRAG_CREATE_ERROR1);
        }
    }
    public void navigateToManageUser() {
        Fragment fragment = null;
        fragment = new ManageUserFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (fragment != null) {
            ft.setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out, R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
            getFragmentManager().beginTransaction()
                    .add(R.id.frame_container, fragment).commit();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating Manage user fragment");
        }
    }


}