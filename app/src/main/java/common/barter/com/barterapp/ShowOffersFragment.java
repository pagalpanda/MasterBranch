package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import common.barter.com.barterapp.globalhome.GlobalHome;

public class ShowOffersFragment extends Fragment implements AdapterOnClickListener{

    Activity context;

    ArrayList<Offer> listOfOffers = new ArrayList<Offer>();;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    RecyclerView lvOffers;
    CountDownLatch latch;
    JSONArray myposts = null;
    JSONArray hisposts = null;
    JSONObject offers;
    ShowOffersAdapter adapter;
    String calledFor;
    String dateUpdated;
    int status;
    String currentOfferId;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (menu.findItem(R.id.action_search) != null) {

                menu.findItem(R.id.action_search).setVisible(false);
        }
    }

    public ShowOffersFragment( ){


    }
    String subCategory;
    public ShowOffersFragment( String calledFor,ArrayList<Offer> listOfOffers ){
        this();
        this.listOfOffers=listOfOffers;
        this.calledFor = calledFor;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_show_offers, container, false);
        ReviewFragment.cameFrom="offers";
        lvOffers = (RecyclerView)rootView.findViewById(R.id.listViewShowOffers);
//        MenuItem item = menu.findItem(R.id.my_item);
//        item.setVisible(false);
        lvOffers.setAdapter(null);

        setHasOptionsMenu(true);

            ((GlobalHome) getActivity()).setActionBarTitle("Offers");
        adapter = new ShowOffersAdapter(getContext(), listOfOffers, calledFor, this);

        lvOffers.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());                 // Creating a layout Manager

        lvOffers.setLayoutManager(mLayoutManager);

//        lvOffers.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                String hisId = listOfOffers.get(position).getUserIdHis();
//                selectedPosition = position;
//                status = listOfOffers.get(position).getStatus();
//                dateUpdated = listOfOffers.get(position).getLastUpdateDate();
//                currentOfferId = listOfOffers.get(position).getOfferId();
//                new GetUserPosts().execute(hisId);
//            }
//        }));

       // new GetOffers().execute();

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







    ArrayList<Post> myListOfPostsInOffer;
    ArrayList<Post> hisListOfPostsInOffer;

    @Override
    public void onClick(int position) {

        String hisId = listOfOffers.get(position).getUserIdHis();
        selectedPosition = position;
        status = listOfOffers.get(position).getStatus();
        dateUpdated = listOfOffers.get(position).getLastUpdateDate();
        currentOfferId = listOfOffers.get(position).getOfferId();
        new GetUserPosts().execute(hisId);
    }

    class GetUserPosts extends AsyncTask<String, String, String> {

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
            String hisUniqueId = args[0];
            JSONObject json = new JSONObject();


                params.put("myuniqueid", uniqueid);
                params.put("hisuniqueid", hisUniqueId);
                json = jsonParser.makeHttpRequest(CommonResources.getURL("get_user_posts_for_offers"),
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
                    myListOfPostsInOffer = new ArrayList<>();
                    hisListOfPostsInOffer = new ArrayList<>();
                    myposts = json.getJSONArray("myposts");
                    if(myposts.length() == 0) return "empty";
                    for (int i = 0; i < myposts.length(); i++) {
                        JSONObject c = myposts.getJSONObject(i);
                        myListOfPostsInOffer.add(new Post(c.getString("uniqueid"), c.getString("title"), c.getString("createddate"), c.getString("locality"), c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city"), "null".equalsIgnoreCase(c.getString("isaddedtowishlist"))?false:true));
                    }

                    hisposts = json.getJSONArray("hisposts");
                    if(hisposts.length() == 0) return "empty";
                    for (int i = 0; i < hisposts.length(); i++) {
                        JSONObject c = hisposts.getJSONObject(i);
                        hisListOfPostsInOffer.add(new Post(c.getString("uniqueid"), c.getString("title"), c.getString("createddate"), c.getString("locality"), c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city"), "null".equalsIgnoreCase(c.getString("isaddedtowishlist"))?false:true));
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

            if("empty".equalsIgnoreCase(isEmpty))
                Toast.makeText(getContext(),"There are not posts in your city in this sub-category",Toast.LENGTH_LONG).show();
            navigateToReview();

        }

        private void navigateToReview() {

            ArrayList<Post> listSelectedMine = new ArrayList<>();
            ArrayList<Post> listSelectedHis = new ArrayList<>();
            for(Post post : myListOfPostsInOffer){

                if( (listOfOffers.get(selectedPosition).getMySelectedPosts()).contains(post.getPostId()) ) {
                    post.setIsSelected(true);
                    listSelectedMine.add(post);
                }
            }
            for(Post post : hisListOfPostsInOffer){
                if( (listOfOffers.get(selectedPosition).getHisSelectedPosts() ).contains(post.getPostId()) ) {
                    post.setIsSelected(true);
                    listSelectedHis.add(post);
                }
            }


                Fragment fragment = null;
                fragment = new ReviewFragment(listSelectedMine,listSelectedHis,"view",myListOfPostsInOffer,hisListOfPostsInOffer, status, dateUpdated, currentOfferId);


                if (fragment != null) {
                    getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                } else {
                    Log.e("MainActivity", "Error in creating fragment");
                }


        }

    }


}