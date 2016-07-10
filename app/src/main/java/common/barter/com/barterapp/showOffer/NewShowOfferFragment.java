package common.barter.com.barterapp.showOffer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.makeOffer.ReviewFragment;
import common.barter.com.barterapp.globalhome.GlobalHome;

/**
 * Created by vikram on 23/06/16.
 */
public class NewShowOfferFragment  extends Fragment {
    private NewShowOffersPresenter showOffersPresenter;
    private RecyclerView lvOffers;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null) {
            menu.findItem(R.id.action_search).setVisible(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setPresenter();
        setLoaderManager();
        View rootView = inflater.inflate(R.layout.fragment_show_offers, container, false);
        setHasOptionsMenu(true);
        // TODO: Add Enum or any other mechanism
        ReviewFragment.cameFrom=MessagesString.OFFERS;
        setActionBarTitle();
        initializeWidgets(rootView);
        createAndSetLinearLayoutManager();
        createAndSetAdapter();
        return rootView;
    }

    private void initializeWidgets(View rootView) {
        lvOffers = (RecyclerView)rootView.findViewById(R.id.listViewShowOffers);
        lvOffers.setAdapter(null);
    }

    private void createAndSetLinearLayoutManager() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        lvOffers.setLayoutManager(mLayoutManager);
    }

    private void createAndSetAdapter() {
        lvOffers.setAdapter(showOffersPresenter.getShowOfferAdapter());
        showOffersPresenter.notifyDataSetChanged();
    }

    private void setLoaderManager() {
        this.getLoaderManager().initLoader(MessagesString.SHOW_OFFERS_LOADER_ID, null, showOffersPresenter.getShowOfferLoaderManager());
    }

    private void setPresenter() {
        if (showOffersPresenter==null){
            showOffersPresenter = new NewShowOffersPresenter();
        }
        showOffersPresenter.setShowOffersFragment(this);
    }

    private void setActionBarTitle() {
        ((GlobalHome) getActivity()).setActionBarTitle(MessagesString.OFFERS);
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
//        context=activity;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return false;
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
