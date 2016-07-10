package common.barter.com.barterapp.data.constants;

/**
 * Created by vikram on 28/05/16.
 */
public enum LoginMode {

    GPLUS(0),FACEBOOK(1), MANUALLOGIN(2),MANUALSIGNUP(3);

    private final int loginId;
    LoginMode(int loginId){
            this.loginId=loginId;
        }
}
