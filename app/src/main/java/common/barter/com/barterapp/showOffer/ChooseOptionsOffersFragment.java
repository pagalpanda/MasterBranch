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

    private static final int NUM_OF_TABS = 2;
    private Activity context;
    private ProgressDialog pDialog;
    private ChooseOffersAdapter adapter;
    private ViewPager viewPager;
    private String subCategory;
    private int selectedPosition = -1;
    private ArrayList<Offer> listOfMyOffers = new ArrayList<Offer>();
    private ArrayList<Offer> listOfHisOffers = new ArrayList<Offer>();


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
        }
    }

    public ChooseOptionsOffersFragment( ){ }
    public ChooseOptionsOffersFragment( String calledFor, String subCategory){
        this();
        this.subCategory=subCategory;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_choose_options_offer, container, false);
        setHasOptionsMenu(true);
        initializaWidgets(rootView);
        getOffers();
        return rootView;
    }

    private void getOffers() {
//        new GetOffers().execute();
    }

    private void initializaWidgets(View rootView) {
        ((GlobalHome) getActivity()).setActionBarTitle("Offers");
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout_show_offers);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) rootView.findViewById(R.id.pagerShowOffers);
        adapter = new ChooseOffersAdapter(
                getFragmentManager(), NUM_OF_TABS, listOfMyOffers, listOfHisOffers);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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