package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import common.barter.com.barterapp.Home.HomeFragment;
import common.barter.com.barterapp.posts.PostDetailsFragment;
import common.barter.com.barterapp.posts.PostListOfferAdapter;
import common.barter.com.barterapp.showOffer.ChooseOptionsOffersFragment;

public class ReviewFragment extends Fragment {

    Activity context;
    Button btnGetPosts;
    ArrayList<Post> listSelectedMine,listSelectedHis;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    //GridView lvPostsMine;
    //GridView lvPostsHis;
    CountDownLatch latch;
    JSONArray posts = null;
    PostListOfferAdapter adapter;
    String calledFor;
    public static String cameFrom;
    int status;
    String dateUpdated;
    String currentOfferId;
    boolean isCounterOffer = false;

    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_confirm_offer, menu);
        super.onCreateOptionsMenu(menu, inflater);

        if (menu.findItem(R.id.action_confirm_make_offer) != null) {

            menu.findItem(R.id.action_confirm_make_offer).setVisible(false);
            menu.removeItem(R.id.action_confirm_make_offer);
        }
        if (menu.findItem(R.id.action_search) != null) {

            menu.findItem(R.id.action_search).setVisible(false);
        }
        if (menu.findItem(R.id.action_proceed_make_offer) != null) {
           // menu.findItem(R.id.action_edit_post).setVisible(false);
            menu.removeItem(R.id.action_proceed_make_offer);
//            menu.findItem(R.id.action_proceed_make_offer).setVisible(false);
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


    public ReviewFragment( ){


    }

    public ReviewFragment( ArrayList<Post> listSelectedMine, ArrayList<Post> listSelectedHis, String calledFor, Boolean isCounterOffer, String currentOfferId){
        this();
        this.listSelectedMine=listSelectedMine;
        this.listSelectedHis=listSelectedHis;
        this.calledFor = calledFor;
        this.isCounterOffer = isCounterOffer;
        this.currentOfferId = currentOfferId;
    }


    ArrayList<Post> listOfPostsMine;
    ArrayList<Post> listOfPostsHis;

    public ReviewFragment( ArrayList<Post> listSelectedMine, ArrayList<Post> listSelectedHis, String calledFor, ArrayList<Post> listOfPostsMine, ArrayList<Post> listOfPostsHis
    , int status, String dateUpdated, String currentOfferId){
        this(listSelectedMine, listSelectedHis, calledFor, false, currentOfferId);
        this.listOfPostsMine = listOfPostsMine;
        this.listOfPostsHis = listOfPostsHis;
        this.status = status;
        this.dateUpdated = dateUpdated;
        this.currentOfferId = currentOfferId;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_review_offer, container, false);
        //lvPostsMine = (GridView)rootView.findViewById(R.id.listViewMine);
        //lvPostsHis = (GridView)rootView.findViewById(R.id.listViewHis);
//        MenuItem item = menu.findItem(R.id.my_item);
//        item.setVisible(false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if(position == 0) return;
                        int pos = position - 1;
                        Fragment fragment = null;
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.zoomin, R.anim.zoomout, R.anim.zoomin, R.anim.zoomout);


                            if (pos < listSelectedMine.size()) {
                                fragment = new PostDetailsFragment(listSelectedMine.get(pos), "viewonly");

                            } else {
                                int numOfRowsMine = 0;
                                if (listSelectedMine.size() % 3 == 0) {
                                    numOfRowsMine = listSelectedMine.size() / 3;
                                } else
                                    numOfRowsMine = (listSelectedMine.size() / 3) + 1;
                                if(position == numOfRowsMine*3+1) return; // Nothing should happen when we click the text "His Posts"
                                int posHis = pos - numOfRowsMine * 3 - 1;
                                fragment = new PostDetailsFragment(listSelectedHis.get(posHis), "viewonly");
                            }
                            if (fragment != null) {
                                ft.add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                            } else {
                                Log.e("MainActivity", "Error in creating fragment");
                            }

                        }



                })
        );



        Button btnEditOffer = (Button)rootView.findViewById(R.id.btnEditOffer);
        Button btnSubmitOffer = (Button)rootView.findViewById(R.id.btnSubmitOffer);
        Button btnShowStatus = (Button)rootView.findViewById(R.id.btnStatus);
        Button btnMakeNewOffer = (Button)rootView.findViewById(R.id.btnMakeAnotherOffer);

        //Following buttons are for Edit, Make a new offer and delete in case of Ongoing status in offers made by me.
        Button btnEditOfferOngoing = (Button)rootView.findViewById(R.id.btnEditOfferOnGoing);
        Button btnMakeNewOfferOngoing = (Button)rootView.findViewById(R.id.btnMakeNewOfferOnGoing);
        Button btnDeleteOffer = (Button)rootView.findViewById(R.id.btnDelteOfferOnGoing);

        Button btnAcceptOffer = (Button)rootView.findViewById(R.id.btnAcceptOffersToMe);
        Button btnRejectOffer = (Button)rootView.findViewById(R.id.btnRejectOffersToMe);
        Button btnCounterOffer = (Button)rootView.findViewById(R.id.btnCounterOffer);

        LinearLayout layoutHolderOnGoing = (LinearLayout)rootView.findViewById(R.id.layout_ongoing_buttons_holder);

        LinearLayout layoutHolderCounter = (LinearLayout)rootView.findViewById(R.id.layout_counter_buttons_holder);
        if("view".equalsIgnoreCase(calledFor) ) {
            if("myoffers".equalsIgnoreCase(CommonResources.flowForOffers)) {
                btnSubmitOffer.setVisibility(View.GONE);
                btnShowStatus.setVisibility(View.VISIBLE);
                if (status == 0) {
                    btnShowStatus.setText("Pending Approval");
                    btnShowStatus.setTextColor(Color.BLACK);
                    btnMakeNewOffer.setVisibility(View.GONE);
                    btnEditOffer.setVisibility(View.GONE);
                    layoutHolderOnGoing.setVisibility(View.VISIBLE);

                } else if (status == 1) {//accepted
                    btnShowStatus.setText("Accpted on " + CommonResources.convertDate(dateUpdated));
                    btnShowStatus.setBackgroundColor(Color.GREEN);
                    btnShowStatus.setTextColor(Color.BLACK);
                    btnMakeNewOffer.setVisibility(View.VISIBLE);
                    btnEditOffer.setVisibility(View.GONE);
                    layoutHolderOnGoing.setVisibility(View.GONE);
                } else if (status == 2) {// Rejected
                    btnShowStatus.setText("Rejected on " + CommonResources.convertDate(dateUpdated));
                    btnShowStatus.setBackgroundColor(Color.RED);
                    btnShowStatus.setTextColor(Color.WHITE);
                    btnMakeNewOffer.setVisibility(View.VISIBLE);
                    btnEditOffer.setVisibility(View.GONE);
                    layoutHolderOnGoing.setVisibility(View.GONE);
                } else if (status == 3) {//counter
                    btnShowStatus.setText("Counter Offered on " +CommonResources.convertDate( dateUpdated));
                    btnShowStatus.setBackgroundColor(Color.BLUE);
                    btnShowStatus.setTextColor(Color.WHITE);
                    btnMakeNewOffer.setVisibility(View.VISIBLE);
                    btnEditOffer.setVisibility(View.GONE);
                    layoutHolderOnGoing.setVisibility(View.GONE);
                }

            }else if("hisoffers".equalsIgnoreCase(CommonResources.flowForOffers)){
                btnSubmitOffer.setVisibility(View.GONE);
                btnShowStatus.setVisibility(View.VISIBLE);
                if (status == 0) {
                    btnShowStatus.setText("Pending Approval");
                    btnShowStatus.setTextColor(Color.BLACK);
                    btnMakeNewOffer.setVisibility(View.GONE);
                    btnEditOffer.setVisibility(View.GONE);
                    layoutHolderOnGoing.setVisibility(View.GONE);
                    layoutHolderCounter.setVisibility(View.VISIBLE);

                } else if (status == 1) {//accepted
                    btnShowStatus.setText("Accpted on " + CommonResources.convertDate(dateUpdated));
                    btnShowStatus.setBackgroundColor(Color.GREEN);
                    btnShowStatus.setTextColor(Color.BLACK);

                    btnEditOffer.setVisibility(View.GONE);
                    layoutHolderOnGoing.setVisibility(View.GONE);
                } else if (status == 2) {// Rejected
                    btnShowStatus.setText("Rejected on " + CommonResources.convertDate(dateUpdated));
                    btnShowStatus.setBackgroundColor(Color.RED);
                    btnShowStatus.setTextColor(Color.WHITE);

                    btnEditOffer.setVisibility(View.GONE);
                    layoutHolderOnGoing.setVisibility(View.GONE);
                } else if (status == 3) {//counter
                    btnShowStatus.setText("Counter Offered on " + CommonResources.convertDate(dateUpdated));
                    btnShowStatus.setBackgroundColor(Color.BLUE);
                    btnShowStatus.setTextColor(Color.WHITE);

                    btnEditOffer.setVisibility(View.GONE);
                    layoutHolderOnGoing.setVisibility(View.GONE);
                }
            }

        }else {
            btnShowStatus.setVisibility(View.GONE);
            btnSubmitOffer.setVisibility(View.VISIBLE);
            btnMakeNewOffer.setVisibility(View.GONE);
        }
        btnMakeNewOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("review".equalsIgnoreCase(calledFor))
                    getFragmentManager().popBackStack();
                else {
                    //call MakeOffer

                    Fragment fragment = null;
                    fragment = new MakeOfferFragment(listOfPostsMine, listOfPostsHis, "view",false, currentOfferId);


                    if (fragment != null) {
                        getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                    } else {
                        Log.e("MainActivity", "Error in creating fragment");
                    }

                }
            }
        });
        btnSubmitOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentOfferId != null && !"".equalsIgnoreCase(currentOfferId) &&  isCounterOffer){
                    new MakeOffer().execute("counter",currentOfferId);
                }else
                    new MakeOffer().execute("create");
            }
        });

        btnEditOffer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("review".equalsIgnoreCase(calledFor))
                            getFragmentManager().popBackStack();
                        else {
                            //call MakeOffer

                            Fragment fragment = null;
                            fragment = new MakeOfferFragment(listOfPostsMine, listOfPostsHis, "view", false, currentOfferId);


                            if (fragment != null) {
                                getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                            } else {
                                Log.e("MainActivity", "Error in creating fragment");
                            }

                        }
                    }
                });

        btnEditOfferOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("review".equalsIgnoreCase(calledFor))
                    getFragmentManager().popBackStack();
                else {
                    //call MakeOffer

                    Fragment fragment = null;
                    fragment = new MakeOfferFragment(listOfPostsMine, listOfPostsHis, "view",false,currentOfferId);


                    if (fragment != null) {
                        getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                    } else {
                        Log.e("MainActivity", "Error in creating fragment");
                    }

                }
            }
        });
        btnMakeNewOfferOngoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("review".equalsIgnoreCase(calledFor))
                    getFragmentManager().popBackStack();
                else {
                    //call MakeOffer

                    Fragment fragment = null;
                    fragment = new MakeOfferFragment(listOfPostsMine, listOfPostsHis, "view",false, currentOfferId);


                    if (fragment != null) {
                        getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                    } else {
                        Log.e("MainActivity", "Error in creating fragment");
                    }

                }
            }
        });
        btnDeleteOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MakeOffer().execute("delete");
            }
        });


        btnAcceptOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MakeOffer().execute("accept");
            }
        });

        btnRejectOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MakeOffer().execute("reject");
            }
        });
        btnCounterOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("review".equalsIgnoreCase(calledFor))
                    getFragmentManager().popBackStack();
                else {
                    //call MakeOffer

                    Fragment fragment = null;
                    fragment = new MakeOfferFragment(listOfPostsMine, listOfPostsHis, "view",true, currentOfferId);


                    if (fragment != null) {
                        getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                    } else {
                        Log.e("MainActivity", "Error in creating fragment");
                    }

                }
            }
        });

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mAdapter = new ReviewOfferGridAdapter(getContext(), listSelectedMine,listSelectedHis, "review");
        //mLayoutManager.
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (((ReviewOfferGridAdapter)mAdapter).isHeaderMine(position) || ((ReviewOfferGridAdapter)mAdapter).isHeaderHis(position) ) ? mLayoutManager .getSpanCount() : 1;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        adapter = new PostListOfferAdapter(getContext(), listSelectedMine, "review");

        //lvPostsMine.setAdapter(adapter);
        adapter = new PostListOfferAdapter(getContext(),listSelectedHis, "review");
        //lvPostsHis.setAdapter(adapter);

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





    class MakeOffer extends AsyncTask<String, String, String> {

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
            JSONObject json = new JSONObject();
            if("create".equalsIgnoreCase(args[0])) {
                String uniqueid = LoginDetails.getInstance().getUserid();//(String) new CommonResources(getContext()).loadFromSharedPrefs("uniqueid");

                if (uniqueid == null || "".equalsIgnoreCase(uniqueid)) {//user not logged in
                    return "NOT_LOGGED_IN";
                } else { // user logged in
                    params.put("senderid", uniqueid);
                }

                params.put("receiverid", listSelectedHis.get(0).getUniqueId());
                params.put("numofsenderitems", listSelectedMine.size() + "");
                params.put("numofreceiveritems", listSelectedHis.size() + "");


                String postsMine = new String();
                for (Post i : listSelectedMine) {
                    if (postsMine == null || "".equalsIgnoreCase(postsMine)) {
                        postsMine = i.getPostId();
                    } else {
                        postsMine = postsMine + ":" + i.getPostId();
                    }

                }
                Log.d("Hello", postsMine);
                params.put("postsmine", postsMine);

                String postsHis = new String();
                for (Post i : listSelectedHis) {
                    if (postsHis == null || "".equalsIgnoreCase(postsHis)) {
                        postsHis = i.getPostId();
                    } else {
                        postsHis = postsHis + ":" + i.getPostId();
                    }
                }

                params.put("postshis", postsHis);

                Log.d("Hello", postsHis);
                json = jsonParser.makeHttpRequest(CommonResources.getURL("submit_offer"),
                        params);
            }else if("delete".equalsIgnoreCase(args[0])){
                params.put("offerid",currentOfferId );
                json = jsonParser.makeHttpRequest(CommonResources.getURL("delete_offer"),
                        params);
            }else if("accept".equalsIgnoreCase(args[0])){
                params.put("offerid",currentOfferId );
                json = jsonParser.makeHttpRequest(CommonResources.getURL("accept_offer"),
                        params);
            }else if("reject".equalsIgnoreCase(args[0])){
                params.put("offerid",currentOfferId );
                json = jsonParser.makeHttpRequest(CommonResources.getURL("reject_offer"),
                        params);
            }else if("counter".equalsIgnoreCase(args[0])) {
                String uniqueid = (String) new CommonResources(getContext()).loadFromSharedPrefs("uniqueid");

                if (uniqueid == null || "".equalsIgnoreCase(uniqueid)) {//user not logged in
                    return "NOT_LOGGED_IN";
                } else { // user logged in
                    params.put("senderid", uniqueid);
                }

                params.put("receiverid", listSelectedHis.get(0).getUniqueId());
                params.put("numofsenderitems", listSelectedMine.size() + "");
                params.put("numofreceiveritems", listSelectedHis.size() + "");
                params.put("prevofferid", args[1]);


                String postsMine = new String();
                for (Post i : listSelectedMine) {
                    if (postsMine == null || "".equalsIgnoreCase(postsMine)) {
                        postsMine = i.getPostId();
                    } else {
                        postsMine = postsMine + ":" + i.getPostId();
                    }

                }
                Log.d("Hello", postsMine);
                params.put("postsmine", postsMine);

                String postsHis = new String();
                for (Post i : listSelectedHis) {
                    if (postsHis == null || "".equalsIgnoreCase(postsHis)) {
                        postsHis = i.getPostId();
                    } else {
                        postsHis = postsHis + ":" + i.getPostId();
                    }
                }

                params.put("postshis", postsHis);

                Log.d("Hello", postsHis);
                json = jsonParser.makeHttpRequest(CommonResources.getURL("submit_counter_offer"),
                        params);
            }


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
            adapter.notifyDataSetChanged();
            if("empty".equalsIgnoreCase(isEmpty))
                Toast.makeText(getContext(),"There are not posts in your city in this sub-category",Toast.LENGTH_LONG).show();
            if("details".equalsIgnoreCase(cameFrom)) {
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
            }else if("offers".equalsIgnoreCase(cameFrom)){
                //navigateToHome();
                navigateToOffers();
            }
        }

        public void navigateToHome() {
            Fragment fragment = null;
            fragment=new HomeFragment();
            if(fragment!=null)
            {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
            }
            else
            {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }
        }

        public void navigateToOffers() {
            Fragment fragment = null;
            fragment=new ChooseOptionsOffersFragment();
            if(fragment!=null)
            {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
            }
            else
            {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }
        }

    }





}