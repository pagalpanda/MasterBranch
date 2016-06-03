package common.barter.com.barterapp.Home;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.postad.PostAdA;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.GridViewAdapter;
import common.barter.com.barterapp.postad.PostAdA;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.SubCategory.SubCategoryFragment;

public class HomeFragment extends Fragment {

    GridView homeGrid;
    TextView textView;
    Activity context;
    android.support.design.widget.FloatingActionButton floatingButton;

    HomePresenter homePresenter;


    public HomeFragment( ){


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ((GlobalHome)getActivity()).getmDrawerToggle().setDrawerIndicatorEnabled(true);
        new CommonResources(getContext()).clearBackStack(getFragmentManager());

        initializeWidgets(rootView);
        setActionBarTitle();
        setPresenter();
        setListeners();

        return rootView;
    }

    private void setListeners() {
        homeGrid.setOnItemClickListener(homeGridListener);
        floatingButton.setOnClickListener(floatingButtonListener);
    }

    private void setPresenter() {
        if(homePresenter==null){
            homePresenter = new HomePresenter();
        }
        homePresenter.setHomeFragment(this);
    }

    private void initializeWidgets(View view) {
        homeGrid = (GridView) view.findViewById(R.id.gvHome);
        textView = (TextView) view.findViewById(R.id.txtLabel);
        textView.setText("Categories");
        homeGrid.setAdapter(new GridViewAdapter(context, CommonResources.categories));
        floatingButton = (android.support.design.widget.FloatingActionButton)view.findViewById(R.id.fab);
    }

    AdapterView.OnItemClickListener homeGridListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            TextView tvCategoryName = (TextView) view.findViewById(R.id.textViewCategoryName);
            String selectedCategory = tvCategoryName.getText().toString();

            homePresenter.displaySubCategories(selectedCategory);

        }
    };

    View.OnClickListener floatingButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             homePresenter.navigateToPostAd();
        }
    };

    private void setActionBarTitle() {

        ((GlobalHome) getActivity()).setActionBarTitle(GlobalHome.navBarSelectedItem);
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
//    @Override
//    public void onResume() {
//        super.onResume();
//        ((GlobalHome) getActivity()).setActionBarTitle("Home");
//    }



    void displaySubCategories(String selectedCategory) {
        // update the main content by replacing fragments


        Fragment fragment = null;
        fragment = new SubCategoryFragment(selectedCategory);


        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.enter, R.anim.abc_fade_out, R.anim.pop_enter, R.anim.abc_fade_out);

            ft.add(R.id.frame_container, fragment).addToBackStack("sub_category").commit();


        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }



    void navigateToPostAd() {
        // update the main content by replacing fragments
        (new CommonResources(getContext())).navigateToPostAd(getFragmentManager(), "home");
    }


}