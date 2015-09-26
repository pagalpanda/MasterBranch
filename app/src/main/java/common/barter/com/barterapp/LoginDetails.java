package common.barter.com.barterapp;

import com.google.android.gms.plus.model.people.Person;

import java.util.List;

/**
 * Created by vikram on 04/09/15.
 */
public class LoginDetails {

    private static LoginDetails ld = null;

    private String userid; // Genereated by System
    private String personName; // Google & FB
    private String personPhoto;// Google
    private String email;// Google & FB
    private String birthday;// Google & FB ; Can be NULL
    private String id;// Google & FB
    private String gender;// Google & FB; Google int, fb string
    private List<Person.PlacesLived> placesLived; // Google
    private String loginLocation; // Google
    private String homeTown; // FB
    private String gps_location; // Location read from device
    private String password;
    private String mob_verified;
    private String mobilenum;

    private LoginDetails ()
    {
// Singleton Blank constructor
    }

    public static LoginDetails getInstance() {
        if (ld == null)
            ld = new LoginDetails();
        return ld;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }



    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }


    public String getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    private String currentLocation;

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender!=null)
        {
            if ((gender.equalsIgnoreCase("male"))|| (gender.equalsIgnoreCase("m")))
                this.gender = "M";
            if ((gender.equalsIgnoreCase("female"))|| (gender.equalsIgnoreCase("f")))
                this.gender = "F";

        }
    }

    public void setGender(int gender) {

        if ((gender==0))
            this.gender = "M";
        if ((gender==1))
            this.gender = "F";

    }

    public List<Person.PlacesLived> getPlacesLived() {
        return placesLived;
    }

    public void setPlacesLived(List<Person.PlacesLived> placesLived) {
        this.placesLived = placesLived;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getGps_location() {
        return gps_location;
    }

    public void setGps_location(String gps_location) {
        this.gps_location = gps_location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getMob_verified() {
        return mob_verified;
    }

    public void setMob_verified(String mob_verified) {
        this.mob_verified = mob_verified;
    }
    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }




    public void resetDetails()
    {
        // To reset values
        userid=null;
        personName = null;
        personPhoto = null;
        email = null;
        birthday = null;
        id = null;
        gender = null;
        placesLived = null;
        loginLocation = null;
        homeTown = null;
        mobilenum=null;
        //gps_location = null;

    }

}
