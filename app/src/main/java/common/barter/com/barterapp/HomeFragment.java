package common.barter.com.barterapp;

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

public class HomeFragment extends Fragment {
    GridView homeGrid;
    TextView textView;
    Activity context;
    android.support.design.widget.FloatingActionButton floatingButton;



    public HomeFragment( ){


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ((GlobalHome)getActivity()).getmDrawerToggle().setDrawerIndicatorEnabled(true);
        new CommonResources(getContext()).clearBackStack(getFragmentManager());
        homeGrid = (GridView)rootView.findViewById(R.id.gvHome);
        textView = (TextView)rootView.findViewById(R.id.txtLabel);
        textView.setText("Categories");
        homeGrid.setAdapter(new GridViewAdapter(context, CommonResources.categories));
        
        setActionBarTitle();


        homeGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView tvCategoryName = (TextView) view.findViewById(R.id.textViewCategoryName);
                String selectedCategory = tvCategoryName.getText().toString();

                displaySubCategories(selectedCategory);
            }
        });

        floatingButton = (android.support.design.widget.FloatingActionButton)rootView.findViewById(R.id.fab);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new CommonResources(getContext())).navigateToPostAd(getFragmentManager(),"home");
            }
        });

        return rootView;
    }

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



    private void displaySubCategories(String selectedCategory) {
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



    private void navigateToPostAd() {
        // update the main content by replacing fragments


        Fragment fragment = null;
        fragment = new PostAdA();


        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).addToBackStack("post_ad").commit();


        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


}