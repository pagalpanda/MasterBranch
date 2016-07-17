package common.barter.com.barterapp.posts;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.globalhome.GlobalHome;

public class PostsFragment extends Fragment{
    private ArrayList<NewPost> listOfPosts;
    private RecyclerView lvPosts;
    private String navigationMode;
    private String subCategory;
    private PostsPresenter postsPresenter;

    public String getNavigationMode() {
        return navigationMode;
    }

    public String getSubCategory() {
        return subCategory;
    }
    public NewPost getPost(int position) {
        return listOfPosts.get(position);
    }

    public ArrayList<NewPost> getPosts() {
        return listOfPosts;
    }

    public void setPosts(ArrayList<NewPost> listOfPosts) {
        this.listOfPosts = listOfPosts;
    }

    public void removePostFromPosts(long postId) {
        this.listOfPosts.remove(postId);
    }

    public void addPostToPosts(NewPost post) {
        this.listOfPosts.add(post);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null) {
            if(NavigationMode.USERVIEW.name().equalsIgnoreCase(navigationMode))
                menu.findItem(R.id.action_search).setVisible(true);
            else
                menu.findItem(R.id.action_search).setVisible(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        setHasOptionsMenu(true);
        setActionBarTitle();
        initializeWidgets(rootView);
        setInputArguments();
        setPresenter();
        return rootView;
    }

    private void setPresenter() {
        if (postsPresenter==null){
            postsPresenter = new PostsPresenter();
        }
        postsPresenter.setFragment(this);
    }

    public void setActionBarTitle() {
        GlobalHome activity = (GlobalHome) getActivity();
        if(!NavigationMode.USERVIEW.name().equalsIgnoreCase(navigationMode)) {
            activity.setActionBarTitle(MessagesString.MY_POSTS);
        }
        else {
            activity.setActionBarTitle(subCategory);
        }
    }

    public void initializeWidgets(View rootView) {
        lvPosts = (RecyclerView)rootView.findViewById(R.id.listViewPosts);
        lvPosts.setAdapter(null);
    }

    private void setInputArguments() {
        Bundle bundle = getArguments();
        this.navigationMode = bundle.getString(MessagesString.NAV_MODE);
        this.subCategory=bundle.getString(MessagesString.SUBCATEGORY);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listOfPosts = new ArrayList();
        setAdapter();
        createAndSetLayoutManager();
        setListeners();
        doInBackground();
    }

    public void setListeners() {
        lvPosts.addOnItemTouchListener(postsPresenter.getOnItemTouchListener());
    }

    public void createAndSetLayoutManager() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        lvPosts.setLayoutManager(mLayoutManager);
    }

    public void setAdapter() {
        lvPosts.setAdapter(postsPresenter.getPostsListAdapter());
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
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();
        postsPresenter.cancelAsyncCall();
    }

    public boolean validateInput() {
        return true;
    }
    protected void doInBackground() {
        postsPresenter.loadData(this.getNavigationMode());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        postsPresenter.cancelAsyncCall();
    }

    public void navigateToPostDetails(View view, int position) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitleNamePost);
        (new CommonResources(getContext())).navigateToPostDetails(getFragmentManager(),
                listOfPosts.get(position), navigationMode);
    }
}