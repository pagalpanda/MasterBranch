package common.barter.com.barterapp.data.domain;

import android.support.annotation.NonNull;

import common.barter.com.barterapp.LoginDetails;

/**
 * Created by vikram on 05/07/16.
 */
public class UsersOTP extends BaseEntity{
    @NonNull
    private Long userId;
    @NonNull
    private String mobileNum;
    @NonNull
    private Long otp;


    @NonNull
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Long userId) {
        this.userId = userId;
    }

    @NonNull
    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(@NonNull String mobileNum) {
        this.mobileNum = mobileNum;
    }

    @NonNull
    public Long getOtp() {
        return otp;
    }

    public void setOtp(@NonNull Long otp) {
        this.otp = otp;
    }

}
