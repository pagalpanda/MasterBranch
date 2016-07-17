package common.barter.com.barterapp.posts;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import common.barter.com.barterapp.asyncConnections.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;

/**
 * Created by vikramb on 14/07/16.
 */
public class PostsModel {
    private Context context;
    private ModelCallBackListener<JSONObject> modelCallBackListener;
    AsyncConnection asyncConnection;

    public PostsModel(Context context, ModelCallBackListener<JSONObject> modelCallBackListener) {
        this.context = context;
        this.modelCallBackListener = modelCallBackListener;
    }

    public void cancelAsyncCall() {
        if (asyncConnection!=null){
            asyncConnection.cancel(true);
        }
    }

    private Context getContext() {
        return context;
    }

    public void getAllPostsForSubCategoryAndCity(String subCategory) {
        this.sendHttpRequestToGetAllPostsForSubCategoryAndCity(this.getParamsForGetAllPostsForSubCategoryAndCity(subCategory));
    }

    private void sendHttpRequestToGetAllPostsForSubCategoryAndCity(HashMap<String, String> params) {
        this.cancelAsyncCall();
        asyncConnection = this.getAllPostsForSubCategoryAndCityAsnycConnection(params, context);
        asyncConnection.execute();
    }

    private HashMap<String, String> getParamsForGetAllPostsForSubCategoryAndCity(String subCategory) {
        HashMap<String, String> params = new HashMap<>();
        String userId=LoginDetails.getInstance().getUserid();
        if (userId == null || "".equalsIgnoreCase(userId)) {
            userId="0";
        }
        String city = new CommonResources(getContext()).readLocation();
        if ("Select Location".equalsIgnoreCase(city) || "".equalsIgnoreCase(city)) {
            city = "";
        }
        params.put(MessagesString.USER_ID, userId);
        params.put("city", city);
        params.put(MessagesString.SUBCATEGORY, subCategory);
        return params;
    }

    private AsyncConnection getAllPostsForSubCategoryAndCityAsnycConnection(HashMap<String, String> params, Context context) {
        return new  AsyncConnection(context, CommonResources.getURL(MessagesString.GET_ALL_POSTS), MessagesString.POST,
                params, false, null)  {
            @Override
            public void receiveData(JSONObject json) {
                try{
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    if (success == 0) {
                        modelCallBackListener.onSuccess(json);
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

    public void getAllMyPosts() {
        this.sendHttpRequestToGetAllMyPosts(this.getParamsForGetAllMyPosts());
    }

    private void sendHttpRequestToGetAllMyPosts(HashMap<String, String> params) {
        this.cancelAsyncCall();
        asyncConnection = this.getAllMyPostsAsnycConnection(params, context);
        asyncConnection.execute();
    }

    private HashMap<String, String> getParamsForGetAllMyPosts() {
        HashMap<String, String> params = new HashMap<>();
        params.put(MessagesString.USER_ID, LoginDetails.getInstance().getUserid());
        return params;
    }

    private AsyncConnection getAllMyPostsAsnycConnection(HashMap<String, String> params, Context context) {
        return new  AsyncConnection(context, CommonResources.getURL(MessagesString.GET_MY_POSTS), MessagesString.POST,
                params, false, null)  {
            @Override
            public void receiveData(JSONObject json) {
                try{
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    if (success == 0) {
                        modelCallBackListener.onSuccess(json);
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
}
