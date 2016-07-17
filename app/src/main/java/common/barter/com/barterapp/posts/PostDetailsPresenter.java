package common.barter.com.barterapp.posts;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.data.dto.PostDTO;
import common.barter.com.barterapp.data.repository.ImageRepository;
import common.barter.com.barterapp.data.repository.impl.ImageRepositoryImpl;
import common.barter.com.barterapp.wishlist.WishListModel;

/**
 * Created by vikramb on 15/07/16.
 */
public class PostDetailsPresenter implements ModelCallBackListener<JSONObject> {
    private PostDetailsFragment postDetailsFragment;
    private PostDetailsListener postDetailsListener;
    private PostDetailsModel postDetailsModel;

    public void setFragment(PostDetailsFragment postDetailsFragment) {
        this.postDetailsFragment=postDetailsFragment;
    }
    public PostDetailsFragment getFragment() {
        return this.postDetailsFragment;
    }
    public Context getContext() {
        return this.getFragment().getContext();
    }

    public PostDetailsListener getPostDetailsListener() {
        if (postDetailsListener==null){
            postDetailsListener = new PostDetailsListener(this);
        }
        return postDetailsListener;
    }

    public PostDetailsModel getPostDetailsModel() {
        if (postDetailsModel==null){
            postDetailsModel = new PostDetailsModel(this,getContext());
        }
        return postDetailsModel;
    }
    public View.OnClickListener getOnWishListClickListener() {
        return this.getPostDetailsListener().getOnWishListClickListener();
    }
    public View.OnClickListener getOnFloatingButtonClickListener() {
        return this.getPostDetailsListener().getOnFloatingButtonClickListener();
    }

    public boolean checkNetworkAvailability() {
        return CommonResources.isNetworkAvailable(this.getFragment().getActivity());
    }

    public void addOrRemovePostFromMyWishlist(long postId) {
        if (!checkNetworkAvailability()){
            CommonUtil.flash(this.getContext(), MessagesString.CHECK_NETWORK_CONNECTIVITY);
            return;
        }
        String userid = LoginDetails.getInstance().getUserid();
        if(userid == null || "".equalsIgnoreCase(userid)){
            Toast.makeText(getContext(),"Please login to continue",Toast.LENGTH_SHORT).show();
            return;
        }
        WishListModel wishListModel = new WishListModel(this,getContext());
        wishListModel.addOrRemovePostFromMyWishlist(postId);
    }

    public void onWishListClicked() {
        this.addOrRemovePostFromMyWishlist(this.getFragment().getPost().getId());
    }

    @Override
    public void onSuccess(JSONObject json) {
        try {
            String actionTaken = json.getString(MessagesString.ACTION);
            if(MessagesString.ADDED.equalsIgnoreCase(actionTaken) ){
                this.getFragment().setIsAddedToWishList(true);
            }else if("removed".equalsIgnoreCase(actionTaken)){
                this.getFragment().setIsAddedToWishList(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        CommonUtil.flash(this.getContext(), errorMessage);
    }

    public void onFloatingButtonClicked() {
        this.getFragment().navigateToMakeOffer();
    }

    public void downloadImagesAndNavigateToPostAd(FragmentManager fragmentManager, PostDTO post) {
        if (post.getNumOfImages() <= 0) {
            this.getFragment().navigateToPostAd(fragmentManager,post,null);
        } else {
            try
            {
                ImageRepository imageRepository = new ImageRepositoryImpl();
                ArrayList<Bitmap> bitmaps= imageRepository.getAllImagesForPost(getContext(),post.getId(),post.getNumOfImages());
                this.getFragment().navigateToPostAd(fragmentManager,post,bitmaps);
            } catch (InterruptedException e) {
                e.printStackTrace();
                CommonUtil.flash(this.getContext(), MessagesString.TRY_AGAIN_LATER_MESSAGE);
            }
        }
    }
}
