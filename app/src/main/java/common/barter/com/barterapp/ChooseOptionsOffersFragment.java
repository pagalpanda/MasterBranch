package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
        btnMyOffers = (Button)rootView.findViewById(R.id.buttonShowMyOffers);
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
                    "POST", params);




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
                            JSONArray myPosts = myOffer.getJSONArray("myposts");
                            if(isDeleted == 0) {
                                for (int k = 0; k < myPosts.length(); k++) {
                                    listOfMyPostsInOffer.add(myPosts.getString(k));

                                }

                                JSONArray hisPosts = myOffer.getJSONArray("hisposts");
                                for (int k = 0; k < hisPosts.length(); k++) {
                                    listOfHisPostsInOffer.add(hisPosts.getString(k));
                                }

                                listOfMyOffers.add(new Offer(offerID, listOfMyPostsInOffer, listOfHisPostsInOffer, receiverId, "", createdDate, lastUpdateDate, status));
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

                            listOfHisOffers.add(new Offer(offerID,listOfMyPostsInOffer,listOfHisPostsInOffer,senderId,"",createdDate, lastUpdateDate,status));
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








        }


    }





}