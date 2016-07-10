package common.barter.com.barterapp.data.domain;

import android.support.annotation.NonNull;

/**
 * Created by vikram on 28/05/16.
 */

public class LoginUser {

    @NonNull
    private int loginMode;
    @NonNull
    private String emailId;
    @NonNull
    private String password;

    public int getLoginMode() {
        return loginMode;
    }

    public void setLoginMode(int loginMode) {
        this.loginMode = loginMode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
