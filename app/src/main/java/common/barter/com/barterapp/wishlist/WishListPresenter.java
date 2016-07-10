package common.barter.com.barterapp.wishlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.RecyclerItemClickListener;
import common.barter.com.barterapp.SwipeableRecyclerViewTouchListener;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.posts.PostsListAdapter;

/**
 * Created by vikram on 05/07/16.
 */
public class WishListPresenter implements ModelCallBackListener<JSONObject>{
    private WishList fragment;
    private PostsListAdapter postsListAdapter;
    private WishListListener wishListListener;
    private WishListModel wishListModel;


    public Context getContext() {
        return this.getFragment().getContext();
    }

    public WishListModel getWishListModel() {
        if(wishListModel==null){
            wishListModel = new WishListModel(this,this.getContext());
        }
        return wishListModel;
    }

    public PostsListAdapter getPostsListAdapter() {
        if (postsListAdapter==null){
            postsListAdapter=new PostsListAdapter(getContext(),null);
        }
        return postsListAdapter;
    }

    public void setFragment(WishList fragment) {
        this.fragment = fragment;
    }

    public WishList getFragment() {
        return fragment;
    }

    public WishListListener getWishListListener() {
        if(wishListListener==null){
            wishListListener= new WishListListener(getContext(),this);
        }
        return wishListListener;
    }

    public RecyclerItemClickListener getOnItemTouchListener() {
        return this.getWishListListener().getOnItemTouchListener();
    }

    public SwipeableRecyclerViewTouchListener getSwipeableRecyclerViewTouchListener(RecyclerView recyclerView) {
        return this.getWishListListener().getSwipeableRecyclerViewTouchListener(recyclerView);
    }

    public void onItemTouched(int position) {
//        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitleNamePost);
        this.getFragment().navigateToPostDetails(position);
    }

    public boolean canSwipe(int position) {
        return true;
    }

    public void onDismissedBySwipeLeft(int[] reverseSortedPositions) {
        this.onDismissedBySwipe(reverseSortedPositions);
    }

    private void onDismissedBySwipe(int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            removePostFromMyWishlist(this.getFragment().getPost(position));
            this.getPostsListAdapter().notifyItemRemoved(position);
        }
        this.getPostsListAdapter().notifyDataSetChanged();
    }

    public void onDismissedBySwipeRight(int[] reverseSortedPositions) {
        this.onDismissedBySwipe(reverseSortedPositions);
    }

    public void getAllMyWishlists() {
        if (!this.isLoggedIn()) return;
        this.getWishListModel().getAllMyWishlists();
    }

    public boolean isLoggedIn() {
        return CommonResources.isLoggedIn(this.getContext(), MessagesString.LOGIN_TO_SEE_WISHLIST);
    }

    public boolean checkNetworkAvailability() {
        return CommonResources.isNetworkAvailable(this.getFragment().getActivity());
    }

    public void removePostFromMyWishlist(NewPost post) {
        CommonUtil.flash(this.getContext(), "Post id:" + post.getId());
        if (!checkNetworkAvailability()){
            CommonUtil.flash(this.getContext(), MessagesString.CHECK_NETWORK_CONNECTIVITY);
            return;
        }
        this.getWishListModel().removePostFromMyWishlist(post);
        this.getFragment().removePostFromPosts(post.getId());
    }

    @Override
    public void onSuccess(JSONObject json) {
        try {
            JSONArray posts = json.getJSONArray(MessagesString.POSTS);
            if (posts.length() == 0) {
                CommonUtil.flash(this.getContext(), MessagesString.NO_ITEM_IN_WISHLIST);
                return;
            }
            this.getFragment().setPosts(NewPost.getPostListFromJsonArray(posts));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.getPostsListAdapter().notifyDataSetChanged();
    }

    @Override
    public void onFailure(String errorMessage) {
        CommonUtil.flash(this.getContext(), errorMessage);
        this.getPostsListAdapter().notifyDataSetChanged();
    }
}
