package common.barter.com.barterapp.reviewOffer;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.RecyclerItemClickListener;
import common.barter.com.barterapp.makeOffer.MakeOfferFragment;
import common.barter.com.barterapp.makeOffer.NewPostDetailsFragment;
import common.barter.com.barterapp.posts.PostDetailsFragment;

/**
 * Created by vikramb on 13/07/16.
 */
public class ReviewOfferPresenter implements ModelCallBackListener<JSONObject> {
    private ReviewOfferFragment reviewOfferFragment;
    private ReviewOfferListener reviewOfferListener;

    public ReviewOfferFragment getFragment() {
        return reviewOfferFragment;
    }

    public void setFragment(ReviewOfferFragment reviewOfferFragment) {
        this.reviewOfferFragment = reviewOfferFragment;
    }

    public Context getContext() {
        return this.getFragment().getContext();
    }

    public ReviewOfferListener getReviewOfferListener() {
        if (reviewOfferListener==null){
            reviewOfferListener = new ReviewOfferListener(getContext(),this);
        }
        return reviewOfferListener;
    }
    public RecyclerItemClickListener getOnRecyclerItemClickListener(){
        return this.getReviewOfferListener().getOnRecyclerItemClickListener();
    }
    public View.OnClickListener getSubmitOfferOnClickListener() {
        return this.getReviewOfferListener().getSubmitOfferOnClickListener();
    }
    public View.OnClickListener getRejectOfferOnClickListener() {
        return this.getReviewOfferListener().getRejectOfferOnClickListener();
    }
    public View.OnClickListener getAcceptOfferOnClickListener() {
        return this.getReviewOfferListener().getAcceptOfferOnClickListener();
    }
    public View.OnClickListener getDeleteOfferOnClickListener() {
        return this.getReviewOfferListener().getDeleteOfferOnClickListener();
    }
    public View.OnClickListener getOnCounterOfferClickListener() {
        return this.getReviewOfferListener().getOnCounterOfferClickListener();
    }
    public View.OnClickListener getOnMakeOfferClickListener() {
        return this.getReviewOfferListener().getOnMakeOfferClickListener();
    }
    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return this.getReviewOfferListener().getSpanSizeLookup();
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    public void onRecyclerItemClicked(int position) {
        if(position == 0) return;
        int pos = position - 1;
        Fragment fragment = null;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.zoomin, R.anim.zoomout, R.anim.zoomin, R.anim.zoomout);


        if (pos < listSelectedMine.size()) {
            fragment = new NewPostDetailsFragment(listSelectedMine.get(pos), "viewonly");

        } else {
            int numOfRowsMine = 0;
            if (listSelectedMine.size() % 3 == 0) {
                numOfRowsMine = listSelectedMine.size() / 3;
            } else
                numOfRowsMine = (listSelectedMine.size() / 3) + 1;
            if(position == numOfRowsMine*3+1) return; // Nothing should happen when we click the text "His Posts"
            int posHis = pos - numOfRowsMine * 3 - 1;
            fragment = new PostDetailsFragment(listSelectedHis.get(posHis), "viewonly");
        }
        if (fragment != null) {
            ft.add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void onSubmitOfferClicked() {
        if( currentOfferId != null && !"".equalsIgnoreCase(currentOfferId) &&  isCounterOffer){
            new MakeOffer().execute("counter",currentOfferId);
        }else
            new MakeOffer().execute("create");
    }

    public void onRejectOfferClicked() {
        new MakeOffer().execute("reject");
    }

    public void onAcceptOfferClicked() {
        new MakeOffer().execute("accept");
    }

    public void onCounterOfferClicked() {
        if ("review".equalsIgnoreCase(calledFor))
            getFragmentManager().popBackStack();
        else {
            //call MakeOffer

            Fragment fragment = null;
            fragment = new MakeOfferFragment(listOfPostsMine, listOfPostsHis, "view",true, currentOfferId);


            if (fragment != null) {
                getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }

        }
    }

    public void onMakeOfferClicked() {
        if ("review".equalsIgnoreCase(calledFor))
            getFragmentManager().popBackStack();
        else {
            //call MakeOffer

            Fragment fragment = null;
            fragment = new MakeOfferFragment(listOfPostsMine, listOfPostsHis, "view", false, currentOfferId);


            if (fragment != null) {
                getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }

        }
    }

    public int getSpanSize(int position) {
        return (((ReviewOfferGridAdapter)mAdapter).isHeaderMine(position) || ((ReviewOfferGridAdapter)mAdapter).isHeaderHis(position) ) ? mLayoutManager .getSpanCount() : 1;
    }
}
