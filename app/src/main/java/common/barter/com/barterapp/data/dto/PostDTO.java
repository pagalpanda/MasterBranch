package common.barter.com.barterapp.data.dto;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.data.domain.NewPost;

/**
 * Created by vikramb on 15/07/16.
 */
public class PostDTO extends NewPost {
    @NonNull
    private Boolean selected=false;
    @NonNull
    private Boolean addedToWishList=false;
    @NonNull
    private boolean edited=false;

    private ArrayList<Bitmap> images;

    @Override
    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
    }

    @NonNull
    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(@NonNull Boolean selected) {
        this.selected = selected;
    }

    @NonNull
    public Boolean isAddedToWishList() {
        return addedToWishList;
    }

    public void setAddedToWishList(@NonNull Boolean addedToWishList) {
        this.addedToWishList = addedToWishList;
    }

    @NonNull
    public boolean isEdited() {
        return edited;
    }

    public void setEdited(@NonNull boolean edited) {
        this.edited = edited;
    }

    public static PostDTO getPostDTOFromJson(JSONObject json) throws JSONException {
        PostDTO post = new PostDTO();
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
        post.setSelected(false);
        post.setAddedToWishList(json.getBoolean(MessagesString.IS_ADDED_TO_WISHLIST));
        post.setEdited(false);
        return post;
    }
    public static ArrayList<PostDTO> getPostDTOListFromJsonArray(JSONArray jsonArray) throws JSONException {
        ArrayList<PostDTO> posts = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            posts.add(PostDTO.getPostDTOFromJson(json));
        }
        return posts;
    }

}
