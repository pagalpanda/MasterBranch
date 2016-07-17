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


    //Following Strings are present on LoginUser Fragment
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
    public final static String  BT_LOGIN = "LoginUser";
    public final static String  BT_GPLUS_LOGIN = "Log in via Google+";
    public final static String  BT_FB_LOGIN = "Log in via Facebook";
    public final static String  BT_SIGNUP = "Sign Up";
    public final static String  BT_GPLUS_SIGNUP = "Sign up via Google+";
    public final static String  BT_FB_SIGNUP = "Sign up via Facebook";
    public final static String PWD_TOO_SHORT = "Password should be 8 characters long";
    public final static String PWD_IS_BLANK = "Password should not be blank";
    public final static String  GPLUS_CONNECT1 = "Receiving Data";
    public final static String  PWD_CONCAT_STRING = "123";
    public final static String RESET_LINK_SENT_MESSAGE = "A link to reset password has been sent to: ";
    public final static String TRY_AGAIN_LATER_MESSAGE = "We are facing some issues. Please try again later.";
    public final static String EMAIL_ID_NOT_REGISTERED_MESSAGE = "Email id not resgistered.";
    public static final String PLEASE_ENTER_CORRECT_PASSWORD = "Please enter correct password";
    public static final String PASSWORD_UPDATED = "Password updated";
    public static final String NEW_PASSWORDS_DONT_MATCH = "New Passwords don't match";
    public static final String INVALID_PASSWORD = "Invalid Password";
    public static final String PASSWORD_SHOULD_NOT_BE_BLANK = "Password should not be blank";
    public static final String PASSWORD_SHOULD_BE_8_CHARACTERS_LONG = "Password should be 8 characters long";
    public static final String NEW_PASSWORD_CAN_T_BE_SAME_AS_THE_OLD_ONE = "New Password can't be same as the old one!";
    public static final String MAXIMUM_LIMIT_OF_IMAGE_UPLOAD = "You Can Only upload 6 images.";
    public final static String RECEIVING_DATA="Receiving Data ..";
    public final static String EMAIL="email";
    public final static String FB_CLEAR_DATA_ERROR="Error while Logging in.";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String MOBILENUM = "mobilenum";
    public static final String GENDER = "gender";
    public static final String LOGINMODE = "loginmode";
    public static final String USER_ID = "userid";
    public static final String WRONG_DATA_ENTERED = "Wrong data Entered";
    public static final String NO_CHANGES_MADE = "No Changes Made";
    public static final Object ENTER_VALID_NAME = "Please enter valid name";
    public static final Object ENTER_VALID_MOBILENUM = "Please enter correct mobile num";


    public static final String INSTRUCTION = "instruction";
    public static final String PHP_USERHANDLER = "UserHandler";
    public static final String POST = "POST";
    public static final String ERROR_IN_UPDATING_USER_DETAILS = "Error in Updating User details";
    public static final String DATA_SAVED = "Data Saved";
    public static final String INCORRECT_ENTRY = "Incorrect Entry";
    public static final String SEND_AGAIN_IN = "SEND AGAIN IN ";
    public static final String REGISTER_BROADCAST_RECEIVER = "Registered broadcast receiver";
    public static final String UNREGISTER_BROADCAST_RECEIVER = "unregistered broadcst receiver";
    public static final String OTP_NOT_VERIFIED = "OTP not verified. Please try again";
    public static final String IS_VERIFIED = "is_verified";
    public static final String OTP = "otp";
    public static final String OTP_NOT_GENERATED = "OTP not generated. Please try again";
    public static final String ANDROID_TELEPHONY_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static final String MOB_VERIFIED_IS_FALSE = "0";
    public static final String MOB_VERIFIED_IS_TRUE = "1";
    public static final int OTP_LENGTH = 5;
    public static final String FETCHING_POSTS = "Fetching Posts..";
    public static final String GET_OFFER = "GetOffer";
    public static final String OFFERS = "offers";
    public static final String HIS_USER_ID = "hisuserid";
    public static final String GET_USER_POSTS_FOR_OFFERS = "get_user_posts_for_offers";
    public static final String MY_POSTS = "My Posts";
    public static final String HIS_POSTS = "His Posts";
    public static final String NO_POSTS_IN_CITY_AND_CATEGORY = "There are not posts in your city for this Category";
    public static final String FETCHING_OFFERS = "Fetching Offers..";
    public static final String MY_OFFERS = "MYOFFERS";
    public static final String OFFER_TO = "Offer to";
    public static final String OFFER_FROM = "Offer from";
    public static final String PENDING = "Pending";
    public static final String ACCEPTED = "Accepted";
    public static final String REJECTED = "Rejected";
    public static final String COUNTER_OFFERED = "Counter Offered";
    public static final String IV_PRIMARY_IMAGE_OFFER = "ivPrimaryImageOffer";
    public static final String IB_PRIMARY_IMAGE_OFFER = "ibPrimaryImageOffer";
    public static final int SHOW_OFFER_IMAGEBUTTON_WIDTH = 50;
    public static final int SHOW_OFFER_IMAGEBUTTON_HEIGHT = 50;
    public static final int SHOW_OFFERS_LOADER_ID = 1;
    public static final String SENDER_ID = "senderId";
    public static final String RECEIVER_ID = "receiverId";
    public static final String SENT_OFFERS_INSTRUCTION = "0";
    public static final String RECEIVED_OFFERS_INSTRUCTION = "1";
    public static final String ALL_OFFERS_INSTRUCTION = "2";
    public static final String OFFER_ID = "offerId";
    public static final String SENDER_NAME = "senderName";
    public static final String RECEIVER_NAME = "receiverName";
    public static final String IS_DELETED = "isDeleted";
    public static final String STATUS = "status";
    public static final String LAST_UPDATE_DATE = "lastUpdateDate";
    public static final String CREATE_DATE = "createDate";
    public static final String NUM_OF_SENDER_POSTS = "numOfSenderPosts";
    public static final String NUM_OF_RECEIVER_POSTS = "numOfReceiverPosts";
    public static final String SENDER_POSTS = "senderPosts";
    public static final String RECEIVER_POSTS = "receiverPosts";
    public static final String IMAGE_LOCATION = "/Users/vikram/uploadedimages";
    public static final String GET_MY_WISHLIST = "get_my_wishlist";
    public static final String POST_ID = "postid";
    public static final String REMOVE = "remove";
    public static final String SWITCHER_WISHLIST = "switcher_wishlist";
    public static final String POST_REMOVED_FROM_WISHLIST = "Post Removed From WishList";
    public static final String POSTS = "posts";
    public static final String NO_ITEM_IN_WISHLIST = "There are no items in your wishlist";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String SUBCATEGORY = "subcategory";
    public static final String CITY = "city";
    public static final String NUM_OF_IMAGES = "numofimages";
    public static final String LOGIN_TO_SEE_WISHLIST = "Please login to see the wishlist";
    public static final String GET_MAKE_OFFER_POSTS = "get_make_offer_post";
    public static final String NO_POSTS_AVAILABLE = "No Posts Available";
    public static final String PLEASE_SELECT_SOME_ITEMS = "Please select some items";
    public static final String OFFER = "offer";
    public static final String GET_ALL_POSTS = "get_all_posts";
    public static final String GET_MY_POSTS = "get_my_posts";
    public static final String NAV_MODE = "navigationMode";
    public static final String IS_ADDED_TO_WISHLIST = "isAddedtoWishList";
    public static final String POSTDTO = "postDTO";
    public static final String MAKE_AN_OFFER = "Make an Offer";
    public static final String WISHLIST = "Wish List";
    public static final String ACTION = "action";
    public static final String ADDED = "added";
    public static final String IMAGES = "IMAGES";
    public static final String LOCALITY = "locality";
    public static final String POST_AD = "Post Ad";
    public static final String EDIT_POST = "Edit Post";
    public static final String SAVE = "Save";
    public static final int CAMERA_REQUEST = 0;
    public static final int GALLERY_REQUEST = 1;
    public static final String LOCATION = "location";
    public static final String ALL_FIELDS_ARE_MANDATORY = "All fields are mandatory";
    public static final String PLEASE_LOGIN_TO_CONTINUE = "Please Log in to Continue";
    public static final String CATEGORY = "category";
    public static final String CAME_FROM="camefrom";
}
