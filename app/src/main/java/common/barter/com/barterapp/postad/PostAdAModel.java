package common.barter.com.barterapp.postad;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import common.barter.com.barterapp.asyncConnections.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.asyncConnections.LocalitiesAsyncConnection;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.posts.NavigationMode;

/**
 * Created by Panda on 17-05-2016.
 */
public class PostAdAModel {
    private Context context;
    private ModelCallBackListener modelCallBackListener;

    public Context getContext() {
        return context;
    }

    public PostAdAModel(Context context, ModelCallBackListener modelCallBackListener) {
        this.context = context;
        this.modelCallBackListener = modelCallBackListener;
    }

    public void getLocalitiesForLocation(String location) {
        this.sendHttpRequestTogetLocalitiesForLocation(location);
    }

    public void sendHttpRequestTogetLocalitiesForLocation(String location) {
        LocalitiesAsyncConnection asyncConnection = this.getLocalitiesForLocationAsnycConnection(location);
        asyncConnection.execute();
    }

    public LocalitiesAsyncConnection getLocalitiesForLocationAsnycConnection(String location) {
        return new  LocalitiesAsyncConnection(getContext(),location)  {
            @Override
            public void populateLocalities(ArrayList<String> localities) {
                if (localities.isEmpty()){
                    CommonUtil.flash(getContext(), MessagesString.CHECK_NETWORK_CONNECTIVITY);
                    return;
                }
                CommonResources.setListOfLocalities(localities);
            }
        };
    }
    public void createOrEditPost(NewPost post,String mode) {
        this.sendHttpRequestToCreateOrEditPost(this.getParamsToCreateOrEditPost(post), mode);
    }

    public void sendHttpRequestToCreateOrEditPost(HashMap<String, String> params,String mode) {
        String phpName = null;
        if (NavigationMode.EDIT.name().equalsIgnoreCase(mode)) {
            phpName = "edit_my_post";
        } else{
            phpName = "create_post";
        }
        AsyncConnection asyncConnection = this.createOrEditPostAsnycConnection(params, context,phpName);
        asyncConnection.execute();
    }

    public HashMap<String, String> getParamsToCreateOrEditPost(NewPost post) {
        return NewPost.getMapFromPostObject(post);
    }

    public AsyncConnection createOrEditPostAsnycConnection(HashMap<String, String> params, Context context,String phpName) {
        return new  AsyncConnection(context, CommonResources.getURL(phpName), MessagesString.POST,
                params, false, null)  {
            @Override
            public void receiveData(JSONObject json) {
                try{
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    if (success == 0) {
                        modelCallBackListener.onFailure(MessagesString.POST_REMOVED_FROM_WISHLIST);
                    }
                    else{
                        modelCallBackListener.onFailure(MessagesString.TRY_AGAIN_LATER_MESSAGE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    public NewPost createPostObject(String title, String description, String category, String subCategory, String city, String locality, ArrayList<Bitmap> photos, String postId) {
        NewPost post = new NewPost();
        post.setTitle(title);
        post.setDescription(description);
        post.setCategory(category);
        post.setSubCategory(subCategory);
        post.setCity(city);
        post.setLocality(locality);
        post.setImages(photos);
        post.setNumOfImages(photos.size());
        if (postId!=null){
            post.setId(Long.parseLong(postId));
        } else {
            post.setId(-1L);
        }
        return post;
    }
}
