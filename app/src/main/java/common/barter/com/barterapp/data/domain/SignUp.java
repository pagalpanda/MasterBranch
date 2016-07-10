package common.barter.com.barterapp.data.domain;

import android.support.annotation.NonNull;

/**
 * Created by vikram on 28/05/16.
 */
public class SignUp extends LoginUser{
    @NonNull
    private String name;
    private String mobileNum;
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
