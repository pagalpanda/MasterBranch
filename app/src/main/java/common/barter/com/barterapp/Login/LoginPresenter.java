package common.barter.com.barterapp.Login;

import android.content.Context;


import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;


/**
 * Created by vikram on 28/05/16.
 */
public class LoginPresenter implements ModelCallBackListener <JSONObject> {
    private LoginFragment loginFragment;
    private LoginModel loginModel;
    private LoginListener loginListener;
    private LoginProcessDialog pDialog;
    private GPlusConnectionListener gPlusConnectionListener;

    public LoginPresenter() {

    }

    public GPlusConnectionListener getGPlusConnectionListener() {
        if(gPlusConnectionListener==null){
            gPlusConnectionListener = new GPlusConnectionListener(loginFragment.getActivity(),this);
        }
        return gPlusConnectionListener;
    }

    public void setLoginFragment(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    public LoginModel getLoginModel() {
        if (loginModel==null){
            loginModel = new LoginModel(this,this.getContext());
        }
        return loginModel;
    }

    public LoginListener getLoginListener() {
        if (loginListener==null){
            loginListener = new LoginListener(this);
        }
        return loginListener;
    }

    public LoginProcessDialog getpDialog() {
        if (pDialog==null){
            pDialog = new LoginProcessDialog(this.getContext());
        }
        return pDialog;
    }

    public void setLayoutForMode(String tabSelected) {
        if(tabSelected.equals(LoginFragment.tabs.LOGIN.name())){
            loginFragment.setLayoutForLoginMode();
        }else if(tabSelected.equals(LoginFragment.tabs.SIGNUP.name())){
            loginFragment.setLayoutForSignUpMode();
        }
    }

    public void cancelFbRequest() {
        this.getpDialog().dismissProcessDialog();
        CommonResources.hideKeyboard(loginFragment.getActivity());
    }

    public void onErrorFbRequest() {
        CommonUtil.flash(this.getContext(),MessagesString.ERROR_OCCURED_TRY_AGAIN);
        this.getpDialog().dismissProcessDialog();
        CommonResources.hideKeyboard(loginFragment.getActivity());
    }

    private void doManualLogin(Login login) {
        this.getLoginModel().manualLogin(login);
    }

    private void doManualSignUp(SignUp signUp) {
        this.getLoginModel().manualSignUp(signUp);
    }

    public void doFacebookLogin(SignUp signUp) {
        this.getLoginModel().doFacebookLogin(signUp);
    }

    public Login getLoginObject() {
        Login login = new Login();
        login.setEmailId(loginFragment.getEmailId());
        login.setPassword(loginFragment.getPassword());
        return login;
    }

    public  SignUp getSignUpObject() {
        SignUp signUp = new SignUp();
        signUp.setEmailId(loginFragment.getEmailId());
        signUp.setPassword(loginFragment.getPassword());
        signUp.setName(loginFragment.getName());
        signUp.setMobileNum(loginFragment.getMobileNum());
        signUp.setGender(loginFragment.getGender());
        return signUp;
    }

    public SignUp getSignUpObject(Person currentPerson) {
        SignUp signUp = new SignUp();
        signUp.setEmailId(Plus.AccountApi.getAccountName(this.getGPlusConnectionListener().getgPlusApiClient()));
        signUp.setPassword(signUp.getEmailId().concat(MessagesString.PWD_CONCAT_STRING));
        signUp.setName(currentPerson.getDisplayName());
        signUp.setMobileNum(loginFragment.getMobileNum());
        signUp.setGender(currentPerson.getGender() == 0 ? "M" : "F");
        return signUp;
    }

    public SignUp getSignUpObject(JSONObject user) {
        SignUp signUp = new SignUp();
        signUp.setEmailId(user.optString("email"));
        signUp.setName(user.optString("name"));
        signUp.setGender(user.optString("gender"));
        signUp.setPassword(user.optString("email").concat("123"));
        return signUp;
    }

    private boolean isValidEmailAndPwd() {
        if (!CommonResources.isValidEmail(loginFragment.getEmailId())) {
            CommonUtil.flash(this.getContext(), MessagesString.INVALID_EMAIL);
            return false;
        }
        if (!isValidPassword(loginFragment.getPassword())) {
            return false;
        }
        return true;
    }

    private boolean isValidName(String name) {
        return  (name == null || "".equalsIgnoreCase(name.trim())) ? false:true;
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && !pass.equals("") && pass.length() > 7) {return true;}
        if (pass.length() < 8){
            CommonUtil.flash(this.getContext(), MessagesString.PWD_TOO_SHORT);
        } else if (pass == null || pass.equals("")) {
            CommonUtil.flash(this.getContext(), MessagesString.PWD_IS_BLANK);
        } else {
            CommonUtil.flash(this.getContext(), MessagesString.INVALID_PASSWORD);
        }
        return false;
    }

    public boolean validateNewUserPassword(String firstPwd,String secondPwd) {
        if (firstPwd.equals(secondPwd)){return true;}
        CommonUtil.flash(this.getContext(), MessagesString.PASSWORD_MISMATCH);
        return false;
    }

    public CallbackManager getFbCallBackManager() {
        return CallbackManager.Factory.create();
    }

    public void raiseFbLoginRequest(LoginResult loginResult) {
        new FBLoginAsync(loginResult,this).execute();
    }

     void onLoginBtnClicked(){
         CommonResources.hideKeyboard(loginFragment.getActivity());
         if (!isValidEmailAndPwd()) return;
         if (loginFragment.getTabSelected().equals(LoginFragment.tabs.SIGNUP.name())) {
             if (!(isValidName(loginFragment.getName()))) { return;}
             if (!validateNewUserPassword(loginFragment.getPassword(),loginFragment.getConfirmationPassword())) { return;}
             if (loginFragment.getMobileNum()!=null && CommonResources.isValidMobile(loginFragment.getMobileNum())){ return;}
             this.doManualSignUp(this.getSignUpObject());

         }
         else{
             this.doManualLogin(this.getLoginObject());
         }
    }

    void onForgotPwdClicked(){
        loginFragment.navigateToForgotPwd();
    }

    void onPwdFocusChange(){
        validateNewUserPassword(loginFragment.getPassword(), loginFragment.getConfirmationPassword());
    }

    public void onFbButtonClicked() {
        this.getpDialog().createProcessDialog(MessagesString.FB_CONNECTING);
    }

    public void doGPlusLogin(SignUp signUp) {
        this.getLoginModel().gPlusLogin(signUp);
    }

    public void grantPermissionAndReconnect(int resultCode){
        this.getGPlusConnectionListener().grantPermissionAndReconnect(resultCode);
    }

    public Context getContext() {
        return this.loginFragment.getContext();
    }

    @Override
    public void onSuccess(JSONObject json) {
        this.getLoginModel().setLoginDetailsData(json);
        this.getpDialog().dismissProcessDialog();
        loginFragment.navigateToManageUser();
    }

    @Override
    public void onFailure(String errorMessage) {
        this.getpDialog().dismissProcessDialog();
        CommonUtil.flash(this.getContext(), errorMessage);
    }

}
