package common.barter.com.barterapp;

/**
 * Created by amitpa on 9/23/2015.
 */
public class MessagesString {

    public final static String LOCATION_NOT_READ = "Could not Read Your Location!";
    public final static String LOCATION_SET_MANUALLY = "Choose Location";
    public final static String LOCATION_DIALOG_MESSAGE = "Enable GPS to see posts in your locality";
    public final static String LOCATION_DIALOG_POSTIVE_TEXT = "Location Settings";
    public final static String HINT_CITY = "Your City";
    public final static String HINT_LOCALITY = "Your Locality";
    public final static String HINT_CATEGORY = "Category";
    public final static String HINT_SUBCATEGORY = "Sub-category";
    public final static String LOCATION_DIALOG_BUTTON_TEXT = "Current Location";
    public final static String DIALOG_TITLE_TEXT_CITY = "Select your city";
    public final static String DIALOG_TITLE_TEXT_LOCALITY = "Select your locality";
    public final static String DIALOG_TITLE_TEXT_CATEGORY = "Select Category";
    public final static String DIALOG_TITLE_TEXT_SUBCATEGORY = "Select Sub-category";
    public final static String CONNECT_TO_INTERNET = "No internet connection";
    public final static String OTP_NUMBER_MESSAGE = "Please enter the pin we just sent to ";

    //Following Strings are keys for shared prefs-for user details
    public final static String SHARED_PREFS_UNIQUE_ID = "uniqueid";
    public final static String SHARED_PREFS_PERSON_NAME = "personname";
    public final static String SHARED_PREFS_GENDER = "gender";
    public final static String SHARED_PREFS_EMAIL = "email";
    public final static String SHARED_PREFS_USERNAME = "username";
    public final static String SHARED_PREFS_MOBILE = "mobilenum";
    public final static String SHARED_PREFS_IS_MOBILE_VERIFIED = "ismobileverified";
    public final static String SHARED_PREFS_LOGIN_MODE = "loginmode";

    public final static String HEADER_MY_ACCOUNT = "My Account";
    public final static String HEADER_FORGOT_PASSWORD = "Forgot Password";
    public final static String HEADER_CHANGE_PASSWORD = "Change Password";
    public final static String  HEADER_VERIFY_OTP = "Verify OTP";
    public final static String  HEADER_SELECT_POSTS_FOR_OFFER = "Select Posts";

    //Following Strings are Fragment names while replacing
    public final static String  FRAG_FORGOT_PWD = "Forgot_Pass";
    public final static String  FRAG_CREATE_ERROR1 = "Error in creating fragment";

    //Following Strings are common and used at multiple places
    public final static String  TAG_SUCCESS = "success";
    public final static String  VALID_MOBILE = "^[1-9][0-9]{9}$";
    public final static String  SEND_EMAIL = "Sending Email";
    public final static String  INVALID_EMAIL = "Please enter a valid email address";


    //Following Strings are present on Login Fragment
    public final static String PASSWORD_MISMATCH = "Password Mismatch";
    public final static String CHECK_NETWORK_CONNECTIVITY = "Please check Connectivity";
    public final static String GPLUS_CONNECTING = "Connecting to Google..";
    public final static String FB_CONNECTING = "Connecting to Facebook..";
    public final static String  GPLUS_ERROR2 = "No information received. Please try again";
    public final static String FB_GRANT_EMAIL_PERMISSION = "Please grant email permission";
    public final static String FB_EMAIL_IS_MANDATORY = "Email ID is mandatory";
    public final static String ERROR_OCCURED_TRY_AGAIN = "Error occured. Please try again later";
    public final static String  LOGIN_NAME_BLANK = "Please enter your name!";
    public final static String  LOGIN_PWD_MISMATCH = "Password mismatch";
    public final static String  BT_LOGIN = "Login";
    public final static String  BT_GPLUS_LOGIN = "Log in via Google+";
    public final static String  BT_FB_LOGIN = "Log in via Facebook";
    public final static String  BT_SIGNUP = "Sign Up";
    public final static String  BT_GPLUS_SIGNUP = "Sign up via Google+";
    public final static String  BT_FB_SIGNUP = "Sign up via Facebook";
    public final static String PWD_TOO_SHORT = "Password should be 8 characters long";
    public final static String PWD_IS_BLANK = "Password should not be blank";
    public final static String INVALID_PASSWORD = "Invalid Password";
    public final static String  GPLUS_CONNECT1 = "Receiving Data";
    public final static String  PWD_CONCAT_STRING = "123";

    //Following Strings are present on Forgot Pwd
    public final static String RESET_LINK_SENT_MESSAGE = "A link to reset password has been sent to: ";
    public final static String TRY_AGAIN_LATER_MESSAGE = "We are facing some issues. Please try again later.";
    public final static String EMAIL_ID_NOT_REGISTERED_MESSAGE = "Email id not resgistered.";

    //Following Strings are present on Login Async
    public final static String  LOGGING_IN = "Logging in..";
    public final static String RECEIVING_DATA="Receiving Data ..";
    public final static String EMAIL="email";
    public final static String FB_CLEAR_DATA_ERROR="Error while Logging in.";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String MOBILENUM = "mobilenum";
    public static final String GENDER = "gender";
    public static final String LOGINMODE = "loginmode";
}
