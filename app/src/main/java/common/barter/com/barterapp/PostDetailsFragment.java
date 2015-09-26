package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class PostDetailsFragment extends Fragment implements ViewPager.OnPageChangeListener {

    Activity context;
    Button btnGetPosts;
    ArrayList listOfPosts;
    JSONParser jsonParser = new JSONParser();
    ListView lvPosts;
    JSONArray posts = null;
    PostDetailsPagerAdapter adapter;
    Post post;
    ArrayList<Bitmap> photos;
    LinearLayout dotsLayout;
    ViewPager vpImages;
    TextView dots[];
    int dotsCount;
    TextView tvTitle, tvLocality, tvDatePosted, tvNumberOfViews, tvDescription;
    CountDownLatch latch;
    PhotosGridViewAdapter adaper;
    private ProgressDialog pDialog;
    String calledFor;
    public PostDetailsFragment(Post post, String calledFor) {
        this();
        this.post = post;
        this.calledFor=calledFor;

    }
    public PostDetailsFragment() {


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
            if("userview".equalsIgnoreCase(calledFor) || "viewonly".equalsIgnoreCase(calledFor)) {
                menu.findItem(R.id.action_edit_post).setVisible(false);
                menu.removeItem(R.id.action_edit_post);
            }
            else
                menu.findItem(R.id.action_edit_post).setVisible(true);
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_post:
                Toast.makeText(getContext(), "Here in PostDetails", Toast.LENGTH_SHORT).show();
                navigateToPostAdEdit(getFragmentManager(), post, post.getPostId());
                return true;
            default:
//                if("myposts".equalsIgnoreCase(calledFor))
//                    ((GlobalHome)getActivity()).getmDrawerToggle().setDrawerIndicatorEnabled(true);
                return false;
        }

    }

    GlobalHome activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post_details, container, false);


        Button btnMakeOffer = (Button)rootView.findViewById(R.id.btnMakeOffer);

        activity = (GlobalHome) getActivity();
        activity.getmDrawerToggle().setDrawerIndicatorEnabled(false);

        if("viewonly".equalsIgnoreCase(calledFor)) {
            setHasOptionsMenu(true);
            btnMakeOffer.setVisibility(View.INVISIBLE);
            ((GlobalHome) getActivity()).setActionBarTitle("Make an Offer");

        }else if("myposts".equalsIgnoreCase(calledFor)){
            setHasOptionsMenu(true);
            btnMakeOffer.setVisibility(View.INVISIBLE);
            ((GlobalHome) getActivity()).setActionBarTitle("My Posts");
        }else {
            setHasOptionsMenu(true);
            btnMakeOffer.setVisibility(View.VISIBLE);

        }
        Log.d("Details: ", post.getTitle() + " " + post.getHasImage() + " " + post.getNumOfImages() + " " + post.getCreatedDate() + " " + post.getLocality() + " " + post.getDescription());

        tvTitle = (TextView) rootView.findViewById(R.id.tvTitlePostDetails);
        tvLocality = (TextView) rootView.findViewById(R.id.tvLocalityPostDetails);
        tvDatePosted = (TextView) rootView.findViewById(R.id.tvDateCreatedPostDetails);
        tvNumberOfViews = (TextView) rootView.findViewById(R.id.tvNumberOfViewsPostDetails);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescriptionPostDetails);
        setTextsInTextViews(post);

        vpImages = (ViewPager) rootView.findViewById(R.id.vpPostDetails);
        adapter = new PostDetailsPagerAdapter(getContext(), post.getPostId(), post.getNumOfImages());
        vpImages.setAdapter(adapter);
        vpImages.setOnPageChangeListener(this);

        dotsLayout = (LinearLayout) rootView.findViewById(R.id.imageCountLayout);
        dotsCount = post.getNumOfImages();
        dots = new TextView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(".");
            dots[i].setTextSize(45);
            dots[i].setTypeface(null, Typeface.BOLD);
            dots[i].setTextColor(android.graphics.Color.GRAY);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[0].setTextColor(Color.GREEN);

        btnMakeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new MakeOfferFragment(post.getUniqueId());
                CommonResources.flowForOffers = "";

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.frame_container, fragment).addToBackStack("make_offer").commit();
                    ReviewFragment.cameFrom = "details";


                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        });
        return rootView;
    }

    private void setTextsInTextViews(Post post) {
        tvTitle.setText(post.getTitle());
        tvLocality.setText(post.getLocality());
        tvDatePosted.setText(CommonResources.convertDate(post.getCreatedDate()));
        tvNumberOfViews.setText("33");
        tvDescription.setText(post.getDescription());
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;

    }

    public boolean validateInput() {

        return true;
    }

    @Override

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setTextColor(Color.GRAY);
        }
        // only one selected
        dots[position].setTextColor(Color.GREEN);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    public void  navigateToPostAdEdit(final FragmentManager fragmentManager, final Post post, final String postid) {
        // update the main content by replacing fragments
        photos = new ArrayList<Bitmap>();


        if (post.getNumOfImages() > 0) {
            for (int i = 0; i < post.getNumOfImages(); i++) {
                latch = new CountDownLatch(1);
                String url = CommonResources.getStaticURL() + "uploadedimages/" + postid + "_" + (i + 1);
                class RetrieveFeedTask extends AsyncTask<String, Void, Bitmap> {


                    @Override
                    protected Bitmap doInBackground(String... urls) {
                        try

                        {
                            URL url = new URL(urls[0]);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap myBitmap = BitmapFactory.decodeStream(input);
                            return myBitmap;
                        } catch (IOException e) {
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        photos.add(bitmap);
                        if (photos.size() == post.getNumOfImages()) {
                            Fragment fragment = null;
                            fragment = new PostAdA("edit", post, postid, photos);
                            if (fragment != null) {
                                fragmentManager.beginTransaction()
                                        .add(R.id.frame_container, fragment).addToBackStack("edit_ad").commit();
                            } else {
                                // error in creating fragment
                                Log.e("MainActivity", "Error in creating fragment");
                            }
                        }
                    }
                }
                new RetrieveFeedTask().execute(url);
            }
        }else{
            Fragment fragment = null;
            fragment = new PostAdA("edit", post, postid, photos);
            if (fragment != null) {
                fragmentManager.beginTransaction()
                        .add(R.id.frame_container, fragment).addToBackStack("edit_ad").commit();
            } else {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }
        }
    }


}