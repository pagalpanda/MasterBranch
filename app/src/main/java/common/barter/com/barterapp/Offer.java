package common.barter.com.barterapp;

import java.util.ArrayList;

/**
 * Created by amitpa on 9/10/2015.
 */
public class Offer {
    String offerId;
    ArrayList<String> mySelectedPosts;
    ArrayList<String> hisSelectedPosts;
    String userIdHis;
    String userNameHis;
    int status;

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    String lastUpdateDate;


    public ArrayList<String> getMySelectedPosts() {
        return mySelectedPosts;
    }

    public String getOfferId() {
        return offerId;
    }

    public ArrayList<String> getHisSelectedPosts() {
        return hisSelectedPosts;
    }

    public String getUserNameHis() {
        return userNameHis;
    }

    public String getUserIdHis() {
        return userIdHis;
    }



    public String getDateOffered() {
        return dateOffered;
    }

    public void setDateOffered(String dateOffered) {
        this.dateOffered = dateOffered;
    }

    String dateOffered;

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public void setMySelectedPosts(ArrayList<String> mySelectedPosts) {
        this.mySelectedPosts = mySelectedPosts;
    }

    public void setHisSelectedPosts(ArrayList<String> hisSelectedPosts) {
        this.hisSelectedPosts = hisSelectedPosts;
    }

    public void setUserIdHis(String userIdHis) {
        this.userIdHis = userIdHis;
    }

    public void setUserNameHis(String userNameHis) {
        this.userNameHis = userNameHis;
    }

    public Offer(String offerId, ArrayList<String> mySelectedPosts, ArrayList<String> hisSelectedPosts, String userIdHis, String userNameHis, String dateOffered,
    String lastUpdateDate, int status) {
        this.offerId = offerId;
        this.mySelectedPosts = mySelectedPosts;
        this.hisSelectedPosts = hisSelectedPosts;
        this.userIdHis = userIdHis;
        this.userNameHis = userNameHis;
        this.dateOffered = dateOffered;
        this.lastUpdateDate=lastUpdateDate;
        this.status=status;

    }



}
