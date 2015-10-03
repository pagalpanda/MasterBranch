package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
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

public class PostsFragment extends Fragment{

    Activity context;
    Button btnGetPosts;
    ArrayList<Post> listOfPosts;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    ListView lvPosts;
    CountDownLatch latch;
    JSONArray posts = null;
    PostsListAdapter adapter;
    String calledFor;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (menu.findItem(R.id.action_search) != null) {
            if("userview".equalsIgnoreCase(calledFor))
                menu.findItem(R.id.action_search).setVisible(true);
            else
                menu.findItem(R.id.action_search).setVisible(false);
        }
    }

    public PostsFragment( ){


    }

    GlobalHome activity;

    String subCategory;
    public PostsFragment( String calledFor, String subCategory){
        this();
        this.calledFor = calledFor;
        this.subCategory=subCategory;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        activity = (GlobalHome) getActivity();
        lvPosts = (ListView)rootView.findViewById(R.id.listViewPosts);
//        MenuItem item = menu.findItem(R.id.my_item);
//        item.setVisible(false);

        setHasOptionsMenu(true);
        if(!"userview".equalsIgnoreCase(calledFor)) {
            activity.setActionBarTitle("My Posts");

        }
        else {
            activity.setActionBarTitle(subCategory);

        }
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
        lvPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvTitle = (TextView) view.findViewById(R.id.tvTitleNamePost);

                (new CommonResources(getContext())).navigateToPostDetails(getFragmentManager(), listOfPosts.get(position), calledFor);

            }
        });

        doInBackground();
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
            String uniqueid = (String) new CommonResources(getContext()).loadFromSharedPrefs("uniqueid");
            JSONObject json = new JSONObject();
            String urlToForCall = "";
            if ("userview".equalsIgnoreCase(calledFor)) {
                params.put("subcategory", subCategory);
                String city = new CommonResources(getContext()).readLocation();
                if ("Select Location".equalsIgnoreCase(city) || "".equalsIgnoreCase(city)) {
                    city = "";
                }
                params.put("city", city);

                if (uniqueid == null || "".equalsIgnoreCase(uniqueid)) {//user not logged in
                    params.put("uniqueid", "0");
                } else { // user logged in
                    params.put("uniqueid", uniqueid);
                }

                urlToForCall = CommonResources.getURL("get_all_posts");

            } else {
                if (null == uniqueid || "null".equalsIgnoreCase(uniqueid) || "".equalsIgnoreCase(uniqueid)) { // user is not logged in
                    return;
                }
                params.put("uniqueid", uniqueid);
                urlToForCall = CommonResources.getURL("get_my_posts");

            }
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
                                Toast.makeText(getContext(), "There are not posts in your city in this sub-category", Toast.LENGTH_LONG).show();
                                return;
                            }
                            for (int i = 0; i < posts.length(); i++) {
                                JSONObject c = posts.getJSONObject(i);
                                listOfPosts.add(new Post(c.getString("uniqueid"), c.getString("title"), c.getString("createddate"), c.getString("locality"), c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city")));
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