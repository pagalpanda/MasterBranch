package common.barter.com.barterapp.posts;

import android.content.Context;

import org.json.JSONObject;

import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.wishlist.WishListModel;

/**
 * Created by vikramb on 15/07/16.
 */
public class PostDetailsModel {
    private Context context;
    private ModelCallBackListener<JSONObject> modelCallBackListener;

    public PostDetailsModel(ModelCallBackListener modelCallBackListener, Context context) {
        this.modelCallBackListener = modelCallBackListener;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }


}
