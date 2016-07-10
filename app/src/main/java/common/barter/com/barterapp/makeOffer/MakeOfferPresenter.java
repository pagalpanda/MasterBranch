package common.barter.com.barterapp.makeOffer;

import android.content.Context;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.Post;
import common.barter.com.barterapp.data.domain.NewOffer;
import common.barter.com.barterapp.data.domain.NewPost;

/**
 * Created by vikram on 06/07/16.
 */
public class MakeOfferPresenter implements ModelCallBackListener<JSONObject> {
    private MakeOfferFragment makeOfferFragment;
    private MakeOfferListener makeOfferListener;


    public MakeOfferListener getMakeOfferListener() {
        if (makeOfferListener==null){
            makeOfferListener = new MakeOfferListener(this);
        }
        return makeOfferListener;
    }

    private View.OnClickListener getOnClickListener() {
        return this.getMakeOfferListener().getOnClickListener();
    }

    private Context getContext() {
        return this.getFragment().getContext();
    }

    private MakeOfferFragment getFragment() {
        return makeOfferFragment;
    }
    private void setFragment(MakeOfferFragment makeOfferFragment) {
        this.makeOfferFragment=makeOfferFragment;
    }

    @Override
    public void onSuccess(JSONObject json) {
        try {
            JSONArray postArray  = json.getJSONArray(MessagesString.POSTS);
            if (postArray.length() == 0) {
                CommonUtil.flash(this.getContext(), MessagesString.NO_POSTS_AVAILABLE);
                return;
            }
            ArrayList<NewPost> posts= NewPost.getPostListFromJsonArray(postArray);
            ArrayList<NewPost> senderPosts = new ArrayList<>();
            ArrayList<NewPost> receiverPosts= new ArrayList<>();
            for (int i = 0; i < posts.size(); i++) {
                if (LoginDetails.getInstance().getUserid().equalsIgnoreCase(String.valueOf(posts.get(i).getId())))
                    senderPosts.add(posts.get(i));
                else
                    receiverPosts.add(posts.get(i));
            }
            setReceiverPosts(receiverPosts);
            setSenderPosts(senderPosts);
            this.getFragment().callAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSenderPosts(ArrayList<NewPost> senderPosts) {
        this.getFragment().setSenderPosts(senderPosts);
    }

    private void setReceiverPosts(ArrayList<NewPost> receiverPosts) {
        this.getFragment().setReceiverPosts(receiverPosts);
    }

    @Override
    public void onFailure(String errorMessage) {

    }

    public void onReviewClicked() {
        this.getFragment().navigateToReview();
    }

    public NewOffer createOffer(ArrayList<NewPost> senderSelectedPosts, ArrayList<NewPost> receiverSelectedPosts, String currentOfferid) {
        return null;
    }
}
