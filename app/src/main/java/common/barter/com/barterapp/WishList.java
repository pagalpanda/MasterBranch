package common.barter.com.barterapp;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import common.barter.com.barterapp.globalhome.GlobalHome;

public class WishList extends Fragment{

    Activity context;
    ArrayList<Post> listOfPosts;
    JSONParser jsonParser = new JSONParser();
    RecyclerView lvPosts;
    JSONArray posts = null;
    PostsListAdapter adapter;
    String calledFor;


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//
//        if (menu.findItem(R.id.action_search) != null) {
//            if("userview".equalsIgnoreCase(calledFor))
//                menu.findItem(R.id.action_search).setVisible(true);
//            else
//                menu.findItem(R.id.action_search).setVisible(false);
//        }
//    }

    public WishList(){
        this.calledFor = "wishlist";

    }

    GlobalHome activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);
        activity = (GlobalHome) getActivity();
        lvPosts = (RecyclerView)rootView.findViewById(R.id.listViewWishList);
//        MenuItem item = menu.findItem(R.id.my_item);
//        item.setVisible(false);

        setHasOptionsMenu(true);

        lvPosts.setAdapter(null);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setListOfPosts();
        listOfPosts = new ArrayList<Post>();
        adapter = new PostsListAdapter(getContext(), listOfPosts);

        lvPosts.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());                 // Creating a layout Manager

        lvPosts.setLayoutManager(mLayoutManager);
        lvPosts.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView tvTitle = (TextView) view.findViewById(R.id.tvTitleNamePost);

                (new CommonResources(getContext())).navigateToPostDetails(getFragmentManager(), listOfPosts.get(position), calledFor);
            }
        }));
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(lvPosts,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    removeWishList(listOfPosts.get(position), position);
                                    listOfPosts.remove(position);

                                    adapter.notifyItemRemoved(position);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    removeWishList(listOfPosts.get(position), position);
                                    listOfPosts.remove(position);

                                    adapter.notifyItemRemoved(position);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });

        lvPosts.addOnItemTouchListener(swipeTouchListener);

        doInBackground();
    }
    void removeWishList( Post post,final int pos){
        Toast.makeText(getContext(),"Post id:"+post.getPostId(),Toast.LENGTH_SHORT).show();
        if(!CommonResources.isNetworkAvailable(getActivity())) {
            Toast.makeText(getContext(),"Please check the connectivity",Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String,String> params = new HashMap<String, String>();
        String uniqueId = LoginDetails.getInstance().getUserid();
        if(uniqueId == null || "".equalsIgnoreCase(uniqueId)){
            //User not logged in
            Toast.makeText(getContext(),"Please login to continue",Toast.LENGTH_SHORT).show();
            return;
        }


        params.put("uniqueid", LoginDetails.getInstance().getUserid());
        params.put("postid", post.getPostId());
        String urlToForCall = CommonResources.getURL("switcher_wishlist");
        params.put("instruction", "remove");

        AsyncConnection as = new AsyncConnection(context, urlToForCall, "POST", params, false, null){

            @Override
            public void receiveData(JSONObject json) {
                //If successfull
                if(json != null){
                    String TAG_SUCCESS = "success";
                    try {
                        int success = json.getInt(TAG_SUCCESS);

                        if (success == 0) {

                        } else if (success == 1) {
                            // failed to create product
                        } else {
                            //Invalid input
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();

            }
        };
        as.execute();

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




        /**
         * Creating product
         * */
        protected void doInBackground() {

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();
            String uniqueid = LoginDetails.getInstance().getUserid();
            if(uniqueid == null || "".equalsIgnoreCase(uniqueid)){
                Toast.makeText(getContext(),"Please login to see the wishlist",Toast.LENGTH_SHORT).show();
                return;
            }
            params.put("uniqueid", uniqueid);
            String urlToForCall = CommonResources.getURL("get_my_wishlist");



            AsyncConnection as = new AsyncConnection(context, urlToForCall, "POST", params, false, null) {
                @Override
                public void receiveData(JSONObject json) {
                    if (json == null) {
                        //latch.countDown();
                        return;
                    }

                    String TAG_SUCCESS = "success";
                    try {
                        int success = json.getInt(TAG_SUCCESS);

                        if (success == 0) {

                            posts = json.getJSONArray("posts");
                            if (posts.length() == 0) {
                                Toast.makeText(getContext(), "There are no items in your wishlist", Toast.LENGTH_LONG).show();
                                return;
                            }
                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject c = posts.getJSONObject(i);
                                listOfPosts.add(new Post(c.getString("uniqueid"), c.getString("title"), c.getString("createddate"), c.getString("locality"), c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city"), true));
                            }
                            adapter.notifyDataSetChanged();
                        } else if (success == 1) {
                            // failed to create product
                        } else {
                            //Invalid input
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            };
            as.execute();
        }
}