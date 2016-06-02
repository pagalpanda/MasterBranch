package common.barter.com.barterapp.Login;

import android.view.View;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;


/**
 * Created by vikram on 29/05/16.
 */
public class LoginListener implements FacebookCallback<LoginResult> {

    private LoginPresenter loginPresenter;

    public LoginListener(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    public View.OnClickListener getBtnLoginOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    loginPresenter.onLoginBtnClicked();
                }
        };
    }

    public View.OnClickListener getForgotPwdOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.onForgotPwdClicked();
            }
        };
    }

    public View.OnFocusChangeListener getPwdOnFocusChangeListener(){
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    loginPresenter.onPwdFocusChange();
                }
            }
        };
    }

    public View.OnClickListener getFbOnClickListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                loginPresenter.onFbButtonClicked();
            }
        };
    }

    @Override
    public void onSuccess(LoginResult loginResult) {

        loginPresenter.raiseFbLoginRequest(loginResult);
    }

    @Override
    public void onCancel() {
        loginPresenter.cancelFbRequest();
    }

    @Override
    public void onError(FacebookException e) {
        loginPresenter.onErrorFbRequest();

    }
}
