package common.barter.com.barterapp.showOffer;

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

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import common.barter.com.barterapp.Offer;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.makeOffer.ReviewFragment;
import common.barter.com.barterapp.globalhome.GlobalHome;

public class ShowOffersFragment extends Fragment{

    Activity context;

    private ArrayList<Offer> listOfOffers = new ArrayList<Offer>();;
    private RecyclerView lvOffers;
    private CountDownLatch latch;
    private ShowOffersHolderAdapter adapter;
    private String calledFor;
    private String dateUpdated;
    private int status;
    private String currentOfferId;
    private int selectedPosition = -1;
    private ShowOffersPresenter showOffersPresenter;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null) {
                menu.findItem(R.id.action_search).setVisible(false);
        }
    }

    public ShowOffersFragment( ){}

    public ShowOffersFragment( String calledFor,ArrayList<Offer> listOfOffers ){
        this();
        this.listOfOffers=listOfOffers;
        this.calledFor = calledFor;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_show_offers, container, false);
        setHasOptionsMenu(true);
        // TODO: Add Enum or any other mechanism
        ReviewFragment.cameFrom="offers";
        initializeWidgets(rootView);
        setActionBarTitle();
        setPresenter();
        createAndSetAdapter();
        createAndSetLinearLayoutManager();
        return rootView;
    }

    private void setPresenter() {
        if (showOffersPresenter==null){
            showOffersPresenter = new ShowOffersPresenter();
        }
        showOffersPresenter.setShowOffersFragment(this);
    }

    private void initializeWidgets(View rootView) {
        lvOffers = (RecyclerView)rootView.findViewById(R.id.listViewShowOffers);
        lvOffers.setAdapter(null);
    }

    private void createAndSetLinearLayoutManager() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        lvOffers.setLayoutManager(mLayoutManager);
    }

    private void setActionBarTitle() {
        ((GlobalHome) getActivity()).setActionBarTitle("Offers");
    }

    private void createAndSetAdapter() {
//        adapter = new ShowOffersHolderAdapter(getContext(), listOfOffers, calledFor, showOffersPresenter);
//        lvOffers.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
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
        // TODO: WTF is this
        return true;
    }

    void getUserPosts(int position) {
//        String hisId = listOfOffers.get(position).getUserIdHis();
//        selectedPosition = position;
//        status = listOfOffers.get(position).getStatus();
//        dateUpdated = listOfOffers.get(position).getLastUpdateDate();
//        currentOfferId = listOfOffers.get(position).getOfferId();
//        new GetUserPosts().execute(hisId);
    }

}