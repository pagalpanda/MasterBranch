package common.barter.com.barterapp.wishlist;

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
 * Created by vikram on 05/07/16.
 */
public class WishListModel {
    private ModelCallBackListener modelCallBackListener;
    private Context context;

    public WishListModel(ModelCallBackListener modelCallBackListener, Context context) {
        this.modelCallBackListener = modelCallBackListener;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void getAllMyWishlists() {
        this.sendHttpRequestToGetAllMyWishlist(this.getParamsForGetAllMyWishList());
    }

    public void sendHttpRequestToGetAllMyWishlist(HashMap<String, String> params) {
        AsyncConnection asyncConnection = this.getAllWishListAsnycConnection(params, context);
        asyncConnection.execute();
    }

    public HashMap<String, String> getParamsForGetAllMyWishList() {
        HashMap<String, String> params = new HashMap<>();
        params.put(MessagesString.USER_ID,LoginDetails.getInstance().getUserid());
        return params;
    }

    public AsyncConnection getAllWishListAsnycConnection(HashMap<String, String> params, Context context) {
        return new  AsyncConnection(context, CommonResources.getURL(MessagesString.GET_MY_WISHLIST), MessagesString.POST,
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

    public void addOrRemovePostFromMyWishlist(long postId) {
        this.sendHttpRequestToRemovePostFromMyWishlist(this.getParamsForRemovePostFromMyWishlist(postId));
    }

    public void sendHttpRequestToRemovePostFromMyWishlist(HashMap<String, String> params) {
        AsyncConnection asyncConnection = this.removePostFromMyWishlistAsnycConnection(params, context);
        asyncConnection.execute();
    }

    public HashMap<String, String> getParamsForRemovePostFromMyWishlist(long postId) {
        HashMap<String, String> params = new HashMap<>();
        params.put(MessagesString.USER_ID, LoginDetails.getInstance().getUserid());
        params.put(MessagesString.POST_ID, String.valueOf(postId));
//        params.put(MessagesString.INSTRUCTION, MessagesString.REMOVE);
        return params;
    }

    public AsyncConnection removePostFromMyWishlistAsnycConnection(HashMap<String, String> params, Context context) {
        return new  AsyncConnection(context, CommonResources.getURL(MessagesString.SWITCHER_WISHLIST), MessagesString.POST,
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
}
