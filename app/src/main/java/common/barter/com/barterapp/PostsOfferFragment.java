package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
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

public class PostsOfferFragment extends Fragment {

    Activity context;
    Button btnGetPosts;
    static ArrayList<Post> listOfPosts;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    RecyclerView lvPosts;
    CountDownLatch latch;
    JSONArray posts = null;
    PostListOfferAdapter adapter;
    String calledFor;
    CommonResources commonResources;

    String tabSelected = "1";
    static ArrayList<Post> sListOfPostsMine = new ArrayList<Post>();
    static ArrayList<Post> sListOfPostsHis = new ArrayList<Post>();





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

    public PostsOfferFragment( ){
        int x = 0;

    }

    public PostsOfferFragment( String tabSelected){
        this.tabSelected = tabSelected;

    }
    String subCategory;
    public PostsOfferFragment( String calledFor, String subCategory){
        this();
        this.calledFor = calledFor;
        this.subCategory=subCategory;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_posts_offer, container, false);
        commonResources = new CommonResources(getContext());

        lvPosts = (RecyclerView)rootView.findViewById(R.id.listViewPostsOffer);


            lvPosts.setAdapter(null);

        return rootView;
    }

    public void methodInFragmentB(){

        adapter.notifyDataSetChanged();
        //adapter.notifyDataSetInvalidated();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setListOfPosts();
        listOfPosts = new ArrayList<Post>();
        if("1".equalsIgnoreCase(tabSelected))
            adapter = new PostListOfferAdapter(getContext(), sListOfPostsMine,true);
        else
            adapter = new PostListOfferAdapter(getContext(),sListOfPostsHis, false );

        lvPosts.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());                 // Creating a layout Manager

        lvPosts.setLayoutManager(mLayoutManager);

        TextView test = (TextView)view.findViewById(R.id.tvNoPostsErrorOffer);
        if("1".equalsIgnoreCase(tabSelected)){
            test.setText("Tab 1");
            lvPosts.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    System.out.print(calledFor);
                    Fragment fragment = null;
                    if(calledFor == null) {// coming from Select Posts in offer
                        fragment = new PostDetailsFragment(sListOfPostsMine.get(position), "select_posts");
                    }else {
                        fragment = new PostDetailsFragment(sListOfPostsMine.get(position), "viewonly");
                    }
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.zoomin,R.anim.zoomout,R.anim.zoomin,R.anim.zoomout);
                    if (fragment != null) {
                        ft.add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                    } else {
                        Log.e("MainActivity", "Error in creating fragment");
                    }
                }
            }));

        } else{
            test.setText("Tab 2");
            lvPosts.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Fragment fragment = null;

                    fragment = new PostDetailsFragment(sListOfPostsHis.get(position), "viewonly");
                    if(calledFor == null) {// coming from Select Posts in offer
                        fragment = new PostDetailsFragment(sListOfPostsHis.get(position), "select_posts");
                    }else {
                        fragment = new PostDetailsFragment(sListOfPostsHis.get(position), "viewonly");
                    }
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.zoomin, R.anim.zoomout, R.anim.zoomin, R.anim.zoomout);
                    //Animation zoomin = AnimationUtils.loadAnimation(getContext(),R.anim.zoomin);

                    if (fragment != null) {
                        ft.add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                    } else {
                        Log.e("MainActivity", "Error in creating fragment");
                    }
                }
            }));


        }


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





}