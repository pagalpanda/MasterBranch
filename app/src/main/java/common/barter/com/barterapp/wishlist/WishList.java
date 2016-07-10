package common.barter.com.barterapp.wishlist;

/**
 * Created by amitpa on 8/18/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.data.domain.NewPost;

public class WishList extends Fragment{

    private RecyclerView lvPosts;
    private WishListPresenter wishListPresenter;
    private ArrayList<NewPost> posts;
    // TODO Remove calledFor.Use proper mechanism
    private String calledFor;

    public WishList(){
        this.calledFor = "wishlist";

    }
    public NewPost getPost(int position) {
        return posts.get(position);
    }

    public ArrayList<NewPost> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<NewPost> posts) {
        this.posts = posts;
    }

    public void removePostFromPosts(long postId) {
        this.posts.remove(postId);
    }

    public void addPostToPosts(NewPost post) {
        this.posts.add(post);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);
        setHasOptionsMenu(true);
        initializeWidgets(rootView);
        setPresenter();
        setAdapter();
        createAndSetLayoutManager();
        setListeners();
        return rootView;
    }

    private void setListeners() {
        lvPosts.addOnItemTouchListener(this.getWishListPresenter().getOnItemTouchListener());
        lvPosts.addOnItemTouchListener(this.getWishListPresenter().getSwipeableRecyclerViewTouchListener(lvPosts));
    }

    public WishListPresenter getWishListPresenter() {
        return wishListPresenter;
    }

    private void setPresenter() {
        if (wishListPresenter==null){
            wishListPresenter= new WishListPresenter();
        }
        wishListPresenter.setFragment(this);
    }

    private void setAdapter() {
        lvPosts.setAdapter(this.getWishListPresenter().getPostsListAdapter());
    }

    private void initializeWidgets(View rootView) {
        lvPosts = (RecyclerView)rootView.findViewById(R.id.listViewWishList);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllMyWishlists();
    }

    void navigateToPostDetails(int position){
//        (new CommonResources(getContext())).navigateToPostDetails(getFragmentManager(), posts.get(position), calledFor);
        (new CommonResources(getContext())).navigateToPostDetails(getFragmentManager(), null, calledFor);
    }
    private void createAndSetLayoutManager() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        lvPosts.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public boolean validateInput() {
        return true;
    }

    protected void getAllMyWishlists() {
        this.getWishListPresenter().getAllMyWishlists();
    }

}