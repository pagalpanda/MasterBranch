package common.barter.com.barterapp.data.domain;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import common.barter.com.barterapp.MessagesString;

/**
 * Created by vikram on 26/06/16.
 */
public class NewPost extends BaseEntity {

    @NonNull
    private long userId;
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private int subCategory;
    private String gpsLocation;
    @NonNull
    private String city;
    @NonNull
    private String locality;
    @NonNull
    private String status;
    @NonNull
    private int numOfImages;
    @NonNull
    private String lastUpdateDate;
    private String primaryImage;
    private String[] images;

    public int getNumOfImages() {
        return numOfImages;
    }

    @NonNull
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(@NonNull String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setNumOfImages(int numOfImages) {
        this.numOfImages = numOfImages;
    }

    public String getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(String primaryImage) {
        this.primaryImage = primaryImage;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    public String getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static NewPost getOfferFromJson(JSONObject json) throws JSONException {
        NewPost post = new NewPost();
        post.setId(json.getLong(MessagesString.POST_ID));
        post.setUserId(json.getLong(MessagesString.USER_ID));
        post.setTitle(json.getString(MessagesString.TITLE));
        post.setDescription(json.getString(MessagesString.DESCRIPTION));
        post.setSubCategory(json.getInt(MessagesString.SUBCATEGORY));
        post.setCity(json.getString(MessagesString.CITY));
        post.setStatus(json.getString(MessagesString.STATUS));
        post.setNumOfImages(json.getInt(MessagesString.NUM_OF_IMAGES));
        post.setCreateDate(json.getString(MessagesString.CREATE_DATE));
        post.setLastUpdateDate(json.getString(MessagesString.LAST_UPDATE_DATE));
        return post;
    }
    public static ArrayList<NewPost> getPostListFromJsonArray(JSONArray jsonArray) throws JSONException {
        ArrayList<NewPost> posts = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            posts.add(NewPost.getOfferFromJson(json));
        }
        return posts;
    }

}
