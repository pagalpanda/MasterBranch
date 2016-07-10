package common.barter.com.barterapp.data.repository;

/**
 * Created by vikram on 26/06/16.
 */
public interface UserRepository {
    void dologinUser();
    void doSignUpUser();
    void generateOTP();
    void verifyOTP();
}
