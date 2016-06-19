package common.barter.com.barterapp.otp;

import android.os.CountDownTimer;

/**
 * Created by vikram on 05/06/16.
 */
public class MyCountDownTimer extends CountDownTimer {

    private OTPPresenter otpPresenter;
    public  MyCountDownTimer(long startTime,long interval,OTPPresenter otpPresenter){
        super(startTime, interval);
        this.otpPresenter=otpPresenter;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        otpPresenter.onEachTick(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        otpPresenter.onCountDownFinish();
    }

}
