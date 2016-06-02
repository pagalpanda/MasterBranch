package common.barter.com.barterapp;

import android.app.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by amitpa on 8/18/2015.
 */

public class MakeOfferFragment extends Fragment{



    private String[] tabs = { "My Posts", "His Posts"};
    Activity context;
    Button btnGetPosts;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    ListView lvPosts;

    JSONArray posts = null;


    String uniqueidMine;
    String uniqueidHis;
    ArrayList<Post> listOfPostsMine = new ArrayList<Post>();
    ArrayList<Post> listOfPostsHis = new ArrayList<Post>();
    String calledFor;
    boolean isCounterOffer=false;
    String currentOfferid;
    MakeOfferAdapter adapter;
    CountDownLatch latch;
    ViewPager viewPager;

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

    private void navigateToReview() {
        ArrayList<Post> listSelectedMine = new ArrayList<Post>();
        ArrayList<Post> listSelectedHis = new ArrayList<Post>();
        for(Post post : PostsOfferFragment.sListOfPostsMine){
            if(post.isSelected())
                listSelectedMine.add(post);
        }
        for(Post post : PostsOfferFragment.sListOfPostsHis){
            if(post.isSelected())
                listSelectedHis.add(post);
        }

        if(listSelectedMine.size() == 0 || listSelectedHis.size() == 0){
            Toast.makeText(getContext(),"Please select some items",Toast.LENGTH_SHORT).show();
        }else{
            Fragment fragment = null;
            fragment = new ReviewFragment(listSelectedMine,listSelectedHis,"review", isCounterOffer, currentOfferid);


            if (fragment != null) {
                getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("make_offer").commit();
            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        }

    }

    public MakeOfferFragment( ){


    }

    public MakeOfferFragment(String uniqueidHis ){
        this();
        this.uniqueidHis=uniqueidHis;


    }
    public MakeOfferFragment(ArrayList<Post> listOfPostsMine, ArrayList<Post> listOfPostsHis, String calledFor, boolean isCounterOffer, String currentOfferid){
        this();
        //this.uniqueidHis=uniqueidHis;
        this.listOfPostsMine = listOfPostsMine;
        this.listOfPostsHis = listOfPostsHis;
        this.calledFor = calledFor;
        this.isCounterOffer=isCounterOffer;
        this.currentOfferid = currentOfferid;


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_make_offer2, container, false);
        this.uniqueidMine=(String)new CommonResources(getContext()).loadFromSharedPrefs("uniqueid");
        ((GlobalHome)getActivity()).getSupportActionBar().setTitle(MessagesString.HEADER_SELECT_POSTS_FOR_OFFER);
        if( null == calledFor || "".equalsIgnoreCase(calledFor))
            new GetPosts().execute();
        else if("view".equalsIgnoreCase(calledFor)){
            PostsOfferFragment.sListOfPostsMine=listOfPostsMine;
            PostsOfferFragment.sListOfPostsHis=listOfPostsHis;

        }

        /*Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((GlobalHome)getActivity()).setSupportActionBar(toolbar);*/

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout2);
//        tabLayout.addTab(tabLayout.newTab().setText("My Posts"));
//        tabLayout.addTab(tabLayout.newTab().setText("His Posts"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager2);
        adapter = new MakeOfferAdapter(
                getFragmentManager(), 2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
////                int tabPos = tab.getPosition();
////                if(tabPos == 0){
////                    PostsOfferFragment.tabSelected="1";
////                }else{
////                    PostsOfferFragment.tabSelected="2";
////                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        /*
        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);


        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tabs[0]),
            PostsOfferFragment.class, null);
       mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tabs[1]),
               PostsOfferFragment.class, null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if ("tab1".equalsIgnoreCase(tabId)) {
                    PostsOfferFragment.tabSelected = "1";
                } else {
                    PostsOfferFragment.tabSelected = "2";
                }
            }
        });


        */
        android.support.design.widget.FloatingActionButton  btnProceedToReview = (android.support.design.widget.FloatingActionButton)rootView.findViewById(R.id.fab2);
        btnProceedToReview.setVisibility(View.VISIBLE);

        //Button btnProceedToReview = (Button)rootView.findViewById(R.id.fab2);
        btnProceedToReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToReview();
            }
        });


        return rootView;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if("view".equalsIgnoreCase(calledFor))
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
//        List<Fragment> l = getChildFragmentManager().getFragments();
//        if(!"view".equalsIgnoreCase(calledFor)) {
//            for (Fragment f : l) {
//                if (f instanceof PostsOfferFragment) {
//                    ((PostsOfferFragment) f).methodInFragmentB();
//                }
//
//            }
//        }
//        int currentTabId = mTabHost.getCurrentTab();
//        mTabHost.clearAllTabs();
//
//
////        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
//
//
//
//        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tabs[0]),
//                PostsOfferFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tabs[1]),
//                PostsOfferFragment.class, null);
//
//        mTabHost.setCurrentTab(currentTabId);
    }


    class GetPosts extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Fetching Posts..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {

            // Building Parameters
            HashMap<String,String> params = new HashMap <String,String>();

//            params.add(new BasicNameValuePair("uniqueid", (String)new CommonResources(getContext()).loadFromSharedPrefs("uniqueid")));
            params.put("uniqueidmine", uniqueidMine);
            params.put("uniqueidhis", uniqueidHis);

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = new JSONObject();

            json = jsonParser.makeHttpRequest(CommonResources.getURL("get_make_offer_post"),
                    params);


            // check log cat fro response
            if(json == null){
                //latch.countDown();
                return null;
            }
            Log.d("Fetching Posts", json.toString());

            // check for success tag
            String TAG_SUCCESS = "success";
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 0) {

                    posts = json.getJSONArray("posts");
                    if(posts.length() == 0) return "empty";
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject c = posts.getJSONObject(i);
                        if(  uniqueidMine.equalsIgnoreCase(c.getString("uniqueid")) )
                            listOfPostsMine.add(new Post(c.getString("uniqueid"),c.getString("title"), c.getString("createddate"), c.getString("locality"),  c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city"),"null".equalsIgnoreCase(c.getString("isaddedtowishlist"))?false:true ));
                        else
                            listOfPostsHis.add(new Post(c.getString("uniqueid"),c.getString("title"), c.getString("createddate"), c.getString("locality"),  c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city"),"null".equalsIgnoreCase(c.getString("isaddedtowishlist"))?false:true ));
                    }

                } else if(success == 1){
                    // failed to create product
                }else{
                    //Invalid input
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "";

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String isEmpty) {
            pDialog.dismiss();
            if("empty".equalsIgnoreCase(isEmpty))
                Toast.makeText(getContext(), "There are not posts in your city in this sub-category", Toast.LENGTH_LONG).show();

            PostsOfferFragment.sListOfPostsMine=listOfPostsMine;
            PostsOfferFragment.sListOfPostsHis=listOfPostsHis;
            viewPager.destroyDrawingCache();
            adapter = new MakeOfferAdapter(
                    getFragmentManager(), 2);
            viewPager.setAdapter(adapter);

            adapter.notifyDataSetChanged();


            //refreshChildListViews("");
        }

    }
}