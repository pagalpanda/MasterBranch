package common.barter.com.barterapp.posts;

/**
 * Created by amitpa on 8/18/2015.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.data.dto.PostDTO;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.makeOffer.MakeOfferFragment;
import common.barter.com.barterapp.postad.PostAdA;
import common.barter.com.barterapp.reviewOffer.ReviewOfferFragment;

public class PostDetailsFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private PostDetailsPagerAdapter adapter;
    private PostDTO post;
    private LinearLayout dotsLayout;
    private ViewPager vpImages;
    private TextView dots[];
    private int dotsCount;
    private TextView tvTitle, tvLocality, tvDatePosted, tvNumberOfViews, tvDescription;
    private String navigationMode;
    private ImageButton ibAddToWishList;
    private FloatingActionButton floatingButton;
    private PostDetailsPresenter postDetailsPresenter;

    public PostDTO getPost() {
        return post;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_post_ad, menu);
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null) {
                menu.findItem(R.id.action_search).setVisible(false);
        }
        if (menu.findItem(R.id.action_proceed_make_offer) != null) {
            menu.findItem(R.id.action_proceed_make_offer).setVisible(false);
        }
        if (menu.findItem(R.id.action_edit_post) != null) {
            if(NavigationMode.USERVIEW.name().equalsIgnoreCase(navigationMode) ||NavigationMode.SELECT_POSTS.name().
                    equalsIgnoreCase(navigationMode)|| NavigationMode.VIEW_ONLY.name().equalsIgnoreCase(navigationMode) ||
                    NavigationMode.WISHLIST.name().equalsIgnoreCase(navigationMode)) {
                menu.findItem(R.id.action_edit_post).setVisible(false);
                menu.removeItem(R.id.action_edit_post);
            }
            else
                menu.findItem(R.id.action_edit_post).setVisible(true);
        }
    }

    public void setInputArguments() {
        Bundle bundle = getArguments();
        this.post = (PostDTO) bundle.getSerializable(MessagesString.POSTDTO);
        this.navigationMode = bundle.getString(MessagesString.NAV_MODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_post:
                CommonUtil.flash(getContext(), "Here in PostDetails");
                postDetailsPresenter.downloadImagesAndNavigateToPostAd(getFragmentManager(), post);
                return true;
            default:
                return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_details, container, false);
        setHasOptionsMenu(true);
        setInputArguments();
        initializeWidgets(rootView);
        setDotsLayout();
        setAdapter();
        setPresenter();
        setTextsInTextViews(post);
        setDrawerIndicator();
        setLayoutBasedOnNavMode();
        setListeners();
        return rootView;
    }

    public void setListeners() {
        vpImages.setOnPageChangeListener(this);
        ibAddToWishList.setOnClickListener(postDetailsPresenter.getOnWishListClickListener());
        floatingButton.setOnClickListener(postDetailsPresenter.getOnFloatingButtonClickListener());
    }

    private void setPresenter() {
        if (postDetailsPresenter==null){
            postDetailsPresenter = new PostDetailsPresenter();
        }
        postDetailsPresenter.setFragment(this);
    }

    public void setLayoutBasedOnNavMode() {
        setFloatingButtonVisibility(View.INVISIBLE);
        setWishListVisibility(View.GONE);
        switch (NavigationMode.valueOf(navigationMode)){
            case VIEW_ONLY:
                setActionBarTitle(MessagesString.MAKE_AN_OFFER);
                break;
            case SELECT_POSTS:
                setActionBarTitle(MessagesString.HEADER_SELECT_POSTS_FOR_OFFER);
                break;
            case MYPOSTS:
                setActionBarTitle(MessagesString.MY_POSTS);
                break;
            case WISHLIST:
                setActionBarTitle(MessagesString.WISHLIST);
                break;
            default:
                if(LoginDetails.getInstance().getUserid() != null) {
                    setWishListVisibility(View.VISIBLE);
                    if(post.isAddedToWishList()){
                        ibAddToWishList.setBackground(getResources().getDrawable(R.drawable.hearton));
                    }
                    else {
                        ibAddToWishList.setBackground(getResources().getDrawable(R.drawable.heartoff));
                    }
                }
                break;
        }
    }

    public void setWishListVisibility(int visibility) {
        ibAddToWishList.setVisibility(visibility);
    }

    public void setActionBarTitle(String title) {
        ((GlobalHome) getActivity()).setActionBarTitle(title);
    }

    public void setFloatingButtonVisibility(int visibility) {
        floatingButton.setVisibility(visibility);
    }

    public void setDrawerIndicator() {
        ((GlobalHome) getActivity()).getmDrawerToggle().setDrawerIndicatorEnabled(false);
    }

    public void initializeWidgets(View rootView) {
        floatingButton = (FloatingActionButton)rootView.findViewById(R.id.fab);
        ibAddToWishList = (ImageButton)rootView.findViewById(R.id.ibAddToWishList);
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitlePostDetails);
        tvLocality = (TextView) rootView.findViewById(R.id.tvLocalityPostDetails);
        tvDatePosted = (TextView) rootView.findViewById(R.id.tvDateCreatedPostDetails);
        tvNumberOfViews = (TextView) rootView.findViewById(R.id.tvNumberOfViewsPostDetails);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescriptionPostDetails);
        vpImages = (ViewPager) rootView.findViewById(R.id.vpPostDetails);
        dotsLayout = (LinearLayout) rootView.findViewById(R.id.imageCountLayout);
    }

    public void setDotsLayout() {
        dotsCount = post.getNumOfImages();
        dots = new TextView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(".");
            dots[i].setTextSize(80);
            dots[i].setTypeface(null, Typeface.BOLD);
            dots[i].setTextColor(getResources().getColor(R.color.colorBackGround));
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[0].setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    public void setAdapter() {
        adapter = new PostDetailsPagerAdapter(getContext(), post.getId(), post.getNumOfImages());
        vpImages.setAdapter(adapter);
    }

    private void setTextsInTextViews(PostDTO post) {
        tvTitle.setText(post.getTitle());
        tvLocality.setText(post.getLocality());
        tvDatePosted.setText(CommonResources.convertDate(post.getCreateDate()));
        // TODO Add code to read and save num of views and display the same.
        tvNumberOfViews.setText("33");
        tvDescription.setText(post.getDescription());
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setTextColor(Color.GRAY);
        }
        dots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void navigateToPostAd(FragmentManager fragmentManager, NewPost post, ArrayList<Bitmap> bitmaps) {
        Fragment fragment = new PostAdA();
        Bundle bundle = new Bundle();
        bundle.putString(MessagesString.NAV_MODE,NavigationMode.EDIT.name());
        bundle.putSerializable(MessagesString.POST, (Serializable) post);
        bundle.putSerializable(MessagesString.IMAGES,bitmaps);
        fragment.setArguments(bundle);
        if (fragment != null) {
            fragmentManager.beginTransaction().add(R.id.frame_container, fragment).addToBackStack("edit_ad").commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void navigateToMakeOffer() {
        String actionBarTitle = ((GlobalHome)getActivity()).getSupportActionBar().getTitle().toString();
        CommonResources.headerStack.push(actionBarTitle);
        Fragment fragment = new MakeOfferFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(MessagesString.POST_ID,post.getId());
        CommonResources.flowForOffers = "";
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().add(R.id.frame_container, fragment).addToBackStack("make_offer").commit();
            ReviewOfferFragment.cameFrom = "details";

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void setIsAddedToWishList(boolean bValue) {
        if (bValue){
            post.setAddedToWishList(true);
            ibAddToWishList.setBackground(getResources().getDrawable(R.drawable.hearton));
        } else {
            post.setAddedToWishList(false);
            ibAddToWishList.setBackground(getResources().getDrawable(R.drawable.heartoff));
        }

    }
}