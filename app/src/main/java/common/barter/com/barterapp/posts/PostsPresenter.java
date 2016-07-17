package common.barter.com.barterapp.posts;

import android.content.Context;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.RecyclerItemClickListener;
import common.barter.com.barterapp.data.domain.NewPost;

/**
 * Created by vikramb on 14/07/16.
 */
public class PostsPresenter implements ModelCallBackListener<JSONObject> {
    private PostsFragment postsFragment;
    private PostsListener postsListener;
    private PostsListAdapter postsListAdapter;
    private PostsModel postsModel;

    public PostsModel getPostsModel() {
        if (postsModel==null){
            postsModel = new PostsModel(getContext(),this);
        }
        return postsModel;
    }

    public PostsListAdapter getPostsListAdapter() {
        if (postsListAdapter==null){
            postsListAdapter = new PostsListAdapter(getContext(),this.getFragment().getPosts());
        }
        return postsListAdapter;
    }

    public PostsListener getPostsListener() {
        if (postsListener==null){
            postsListener = new PostsListener(this);
        }
        return postsListener;
    }

    private Context getContext() {
        return postsFragment.getContext();
    }

    public void setFragment(PostsFragment postsFragment) {
        this.postsFragment=postsFragment;
    }
    public PostsFragment getFragment() {
        return postsFragment;
    }

    public RecyclerItemClickListener getOnItemTouchListener(){
        return this.getPostsListener().getOnItemTouchListener(getContext());
    }

    public void notifyDataSetChanged() {
        this.getPostsListAdapter().notifyDataSetChanged();
    }



    @Override
    public void onSuccess(JSONObject json) {
        try {
            JSONArray posts = json.getJSONArray(MessagesString.POSTS);
            if (posts.length() == 0) {
                CommonUtil.flash(getContext(), MessagesString.NO_POSTS_IN_CITY_AND_CATEGORY);
                return;
            }
            this.getFragment().setPosts(NewPost.getPostListFromJsonArray(posts));
            notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        CommonUtil.flash(getContext(),errorMessage);
    }

    public void onItemClicked(View view, int position) {
        this.getFragment().navigateToPostDetails(view,position);
    }

    public void cancelAsyncCall() {
        this.getPostsModel().cancelAsyncCall();
    }

    public void loadData(String mode) {
        if (NavigationMode.USERVIEW.name().equalsIgnoreCase(mode)) {
            this.getPostsModel().getAllPostsForSubCategoryAndCity(this.getFragment().getSubCategory());
        } else {
            if (CommonUtil.isUserLoggedIn()){
                this.getPostsModel().getAllMyPosts();
            }
        }
    }


}
