package common.barter.com.barterapp.makeOffer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.data.domain.NewOffer;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.posts.PostsOfferFragment;
import common.barter.com.barterapp.reviewOffer.ReviewOfferFragment;

/**
 * Created by amitpa on 8/18/2015.
 */

public class MakeOfferFragment extends Fragment{

    private Button btnGetPosts;
    private ListView lvPosts;
    private ArrayList<NewPost> senderPosts = new ArrayList<>();
    private ArrayList<NewPost> receiverPosts = new ArrayList<>();
    private ArrayList<NewPost> senderSelectedPosts = new ArrayList<>();
    private ArrayList<NewPost> receiverSelectedPosts = new ArrayList<>();
    String viewMode;
    private String currentOfferid;
    private MakeOfferAdapter adapter;
    private ViewPager viewPager;
    private MakeOfferPresenter makeOfferPresenter;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_proceed_offer, menu);
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_confirm_make_offer) != null) {
            menu.findItem(R.id.action_confirm_make_offer).setVisible(false);
            menu.removeItem(R.id.action_confirm_make_offer);
        }
        if (menu.findItem(R.id.action_proceed_make_offer) != null) {
            menu.findItem(R.id.action_proceed_make_offer).setVisible(false);
            menu.removeItem(R.id.action_proceed_make_offer);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_proceed_make_offer) {
            //navigateToReview();
            return true;
        }
        return false;
    }

    void navigateToReview() {
        if(senderSelectedPosts.size() == 0 || receiverSelectedPosts.size() == 0){
            CommonUtil.flash(getContext(),MessagesString.PLEASE_SELECT_SOME_ITEMS);
        }else{
            Fragment fragment = null;
            NewOffer offer = makeOfferPresenter.createOffer(senderSelectedPosts,receiverSelectedPosts,currentOfferid);
            fragment = new ReviewOfferFragment(offer,"review");
            if (fragment != null) {
                getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("make_offer").commit();
            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_make_offer2, container, false);
        setActionBar();
        if( null == viewMode || "".equalsIgnoreCase(viewMode))
            new GetPosts().execute();
        else if("view".equalsIgnoreCase(viewMode)){
            PostsOfferFragment.sListOfPostsMine=listOfPostsMine;
            PostsOfferFragment.sListOfPostsHis=listOfPostsHis;

        }


        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout2);
//        tabLayout.addTab(tabLayout.newTab().setText("My Posts"));
//        tabLayout.addTab(tabLayout.newTab().setText("His Posts"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager2);
        adapter = new MakeOfferAdapter(
                getFragmentManager(), 2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        android.support.design.widget.FloatingActionButton  btnProceedToReview = (android.support.design.widget.FloatingActionButton)rootView.findViewById(R.id.fab2);
        btnProceedToReview.setVisibility(View.VISIBLE);
        btnProceedToReview.setOnClickListener(this. getOnClickListener());


        return rootView;
    }

    private void setActionBar() {
        ((GlobalHome)getActivity()).getSupportActionBar().setTitle(MessagesString.HEADER_SELECT_POSTS_FOR_OFFER);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if("view".equalsIgnoreCase(viewMode))
            refreshChildListViews("view");
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
    public void onDetach() {
        super.onDetach();
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




    void refreshChildListViews(String calledFor){
        adapter = new MakeOfferAdapter(
                getFragmentManager(), 2);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }




    public void callAdapter() {
        PostsOfferFragment.sListOfPostsMine=senderPosts;
        PostsOfferFragment.sListOfPostsHis=receiverPosts;
        viewPager.destroyDrawingCache();
        adapter = new MakeOfferAdapter(
                getFragmentManager(), 2);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public NewPost getPostFromSenderPosts(int position) {
        return senderPosts.get(position);
    }

    public ArrayList<NewPost> getSenderPosts() {
        return senderPosts;
    }

    public void setSenderPosts(ArrayList<NewPost> senderPosts) {
        this.senderPosts = senderPosts;
    }

    public void removePostFromSenderPosts(long postId) {
        this.senderPosts.remove(postId);
    }

    public void addPostToSenderPosts(NewPost post) {
        this.senderPosts.add(post);
    }
    public NewPost getPostFromReceiverPosts(int position) {
        return receiverPosts.get(position);
    }

    public ArrayList<NewPost> getReceiverPosts() {
        return receiverPosts;
    }

    public void setReceiverPosts(ArrayList<NewPost> receiverPosts) {
        this.receiverPosts = receiverPosts;
    }

    public void removePostFromReceiverPosts(long postId) {
        this.receiverPosts.remove(postId);
    }

    public void addPostToReceiverPosts(NewPost post) {
        this.receiverPosts.add(post);
    }

}