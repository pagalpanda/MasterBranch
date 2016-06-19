package common.barter.com.barterapp.showOffer;

/**
 * Created by amitpa on 8/18/2015.
 */
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.JSONParser;
import common.barter.com.barterapp.Offer;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.globalhome.GlobalHome;

public class ChooseOptionsOffersFragment extends Fragment {

    Activity context;

    ArrayList<Offer> listOfOffers = new ArrayList<Offer>();;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    Button btnMyOffers;
    Button btnHisOffers;
    CountDownLatch latch;
    JSONArray myposts = null;
    JSONArray hisposts = null;
    JSONObject offers;
    Fragment fragment = null;
    ChooseOffersAdapter adapter;

    ViewPager viewPager;



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        if (menu.findItem(R.id.action_search) != null) {

            menu.findItem(R.id.action_search).setVisible(false);
        }
        if (menu.findItem(R.id.action_proceed_make_offer) != null) {

            menu.findItem(R.id.action_proceed_make_offer).setVisible(false);
            menu.removeItem(R.id.action_proceed_make_offer);

            //menu.findItem(R.id.action_proceed_make_offer).setVisible(false);

        }
    }

    public ChooseOptionsOffersFragment( ){


    }
    String subCategory;
    public ChooseOptionsOffersFragment( String calledFor, String subCategory){
        this();
        this.subCategory=subCategory;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_choose_options_offer, container, false);

        setHasOptionsMenu(true);

        ((GlobalHome) getActivity()).setActionBarTitle("Offers");


        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout_show_offers);
//        tabLayout.addTab(tabLayout.newTab().setText("My Posts"));
//        tabLayout.addTab(tabLayout.newTab().setText("His Posts"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) rootView.findViewById(R.id.pagerShowOffers);
        adapter = new ChooseOffersAdapter(
                getFragmentManager(), 2, listOfMyOffers, listOfHisOffers);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



     /*   btnMyOffers = (Button)rootView.findViewById(R.id.buttonShowMyOffers);
        btnHisOffers = (Button)rootView.findViewById(R.id.buttonShowHisOffers);


        btnMyOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ShowOffersFragment("myoffers", listOfMyOffers);
                CommonResources.flowForOffers = "myoffers";
                if (fragment != null) {
                    getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                } else {
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        });
        btnHisOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new ShowOffersFragment("hisoffers",listOfHisOffers);
                CommonResources.flowForOffers = "hisoffers";
                if (fragment != null) {
                    getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                } else {
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        });

        */




        new GetOffers().execute();

        return rootView;
    }

    int selectedPosition = -1;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setListOfPosts();


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
        context=activity;

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


    ArrayList<Offer> listOfMyOffers = new ArrayList<Offer>();
    ArrayList<Offer> listOfHisOffers = new ArrayList<Offer>();

    class GetOffers extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Fetching Posts..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            HashMap <String,String> params = new HashMap <String,String>();
            String uniqueid = (String)new CommonResources(getContext()).loadFromSharedPrefs("uniqueid");
            JSONObject json = new JSONObject();

            params.put("myuniqueid", uniqueid);
            json = jsonParser.makeHttpRequest(CommonResources.getURL("get_all_my_offers"),
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
                    JSONArray offers = json.getJSONArray("offers");
                    if(offers.length() == 0) return "empty";

                    for (int i = 0; i < offers.length(); i++) {
                        JSONObject c = offers.getJSONObject(i);
                        JSONArray myOffers = c.getJSONArray("myoffers");
                        JSONArray hisOffers = c.getJSONArray("hisoffers");

                        for(int j=0;  j<myOffers.length();j++){
                            JSONObject myOffer = myOffers.getJSONObject(j);
                            ArrayList<String> listOfMyPostsInOffer = new ArrayList<String>();
                            ArrayList<String> listOfHisPostsInOffer = new ArrayList<String>();
                            String offerID = myOffer.getString("offerid");
                            String createdDate = myOffer.getString("createdate");
                            String receiverId = myOffer.getString("receiverid");
                            int status = myOffer.getInt("status");
                            int isDeleted = myOffer.getInt("isdeleted");
                            String lastUpdateDate = myOffer.getString("lastupdatedate");
                            String hisUserName = myOffer.getString("receivername");
                            JSONArray myPosts = myOffer.getJSONArray("myposts");
                            if(isDeleted == 0) {
                                for (int k = 0; k < myPosts.length(); k++) {
                                    listOfMyPostsInOffer.add(myPosts.getString(k));

                                }

                                JSONArray hisPosts = myOffer.getJSONArray("hisposts");
                                for (int k = 0; k < hisPosts.length(); k++) {
                                    listOfHisPostsInOffer.add(hisPosts.getString(k));
                                }

                                listOfMyOffers.add(new Offer(offerID, listOfMyPostsInOffer, listOfHisPostsInOffer, receiverId, hisUserName, createdDate, lastUpdateDate, status));
                                listOfOffers = listOfMyOffers;
                            }
                        }

                        for(int j=0;  j<hisOffers.length();j++){
                            JSONObject hisOffer = hisOffers.getJSONObject(j);
                            String offerID = hisOffer.getString("offerid");
                            String createdDate = hisOffer.getString("createdate");
                            String senderId = hisOffer.getString("senderid");
                            int status = hisOffer.getInt("status");
                            String lastUpdateDate = hisOffer.getString("lastupdatedate");
                            String hisUserName = hisOffer.getString("sendername");
                            JSONArray myPosts = hisOffer.getJSONArray("myposts");
                            ArrayList<String> listOfMyPostsInOffer = new ArrayList<String>();
                            ArrayList<String> listOfHisPostsInOffer = new ArrayList<String>();
                            for(int k=0;  k<myPosts.length();k++){
                                listOfMyPostsInOffer.add(myPosts.getString(k));

                            }

                            JSONArray hisPosts = hisOffer.getJSONArray("hisposts");
                            for(int k=0;  k<hisPosts.length();k++){
                                listOfHisPostsInOffer.add(hisPosts.getString(k));
                            }

                            listOfHisOffers.add(new Offer(offerID,listOfMyPostsInOffer,listOfHisPostsInOffer,senderId,hisUserName,createdDate, lastUpdateDate,status));
                        }

                        System.out.print("xyz");
                        //listOfOffers.add(new Post(c.getString("uniqueid"),c.getString("title"), c.getString("createddate"), c.getString("locality"),  c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city") ));
                    }

                } else if(success == 1){
                    // failed to create product
                }else{
                    //Invalid input
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String isEmpty) {
            // dismiss the dialog once done
            pDialog.dismiss();

            viewPager.destroyDrawingCache();
//            adapter = new MakeOfferAdapter(
//                    getFragmentManager(), 2);
//            viewPager.setAdapter(adapter);

            adapter.notifyDataSetChanged();






        }


    }





}