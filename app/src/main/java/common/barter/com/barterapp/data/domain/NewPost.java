package common.barter.com.barterapp.data.domain;

import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import common.barter.com.barterapp.DeviceStoreUtil;
import common.barter.com.barterapp.Image;
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
    private String subCategory;

    private String gpsLocation;
    @NonNull
    private String city;
    @NonNull
    private String category;
    @NonNull
    private String locality;
    @NonNull
    private String status;
    @NonNull
    private int numOfImages;
    @NonNull
    private String lastUpdateDate;
    private String primaryImage;
    private ArrayList<Bitmap> images;

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

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public void setImages(ArrayList<Bitmap> images) {
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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
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

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static NewPost getPostFromJson(JSONObject json) throws JSONException {
        NewPost post = new NewPost();
        post.setId(json.getLong(MessagesString.POST_ID));
        post.setUserId(json.getLong(MessagesString.USER_ID));
        post.setTitle(json.getString(MessagesString.TITLE));
        post.setDescription(json.getString(MessagesString.DESCRIPTION));
        post.setSubCategory(json.getString(MessagesString.SUBCATEGORY));
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
            posts.add(NewPost.getPostFromJson(json));
        }
        return posts;
    }

    public static HashMap<String, String> getMapFromPostObject(NewPost post) {
        HashMap <String,String> params = new HashMap <String,String>();
        params.put(MessagesString.TITLE, post.getTitle());
        params.put(MessagesString.DESCRIPTION, post.getDescription());
        params.put(MessagesString.CATEGORY, post.getCategory());
        params.put(MessagesString.SUBCATEGORY, post.getSubCategory());
        params.put(MessagesString.CITY, post.getCity());
        params.put(MessagesString.LOCALITY, post.getLocality());
        params.put(MessagesString.USER_ID, String.valueOf(post.getUserId()));
        params.put(MessagesString.NUM_OF_IMAGES, String.valueOf(post.getNumOfImages()));
        int i = 0;
        for (Bitmap bitmap : post.getImages()) {
            try {
                params.put("image_" + i, Image.getBase64EncodedImage(bitmap));
                i++;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return params;
    }
}
