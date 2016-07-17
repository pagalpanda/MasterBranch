package common.barter.com.barterapp.reviewOffer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;

import java.util.ArrayList;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.JSONParser;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.data.domain.NewOffer;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.makeOffer.NewPostListOfferAdapter;

/**
 * Created by vikramb on 14/07/16.
 */
public class ReviewSentOfferFragment extends ReviewOfferFragment {
    private NewOffer offer;
    private String calledFor;
    public static String cameFrom;
    private int status;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ReviewOfferPresenter reviewOfferPresenter;
    private Button btnEditOffer;
    private Button btnSubmitOffer;
    private Button btnShowStatus;
    private Button btnMakeNewOffer;
    private Button btnEditOfferOngoing;
    private Button btnMakeNewOfferOngoing;
    private Button btnDeleteOffer;
    private Button btnAcceptOffer;
    private Button btnRejectOffer;
    private Button btnCounterOffer;
    private LinearLayout layoutHolderOnGoing;
    private LinearLayout layoutHolderCounter;


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
            menu.removeItem(R.id.action_proceed_make_offer);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_review_offer, container, false);
        setHasOptionsMenu(true);
        initializeWidgets(rootView);
        setLayoutAndButtonProperty();
        setReceivedData();
        createAndSetLayoutManager();
        setListeners();
        return rootView;
    }

    private void setReceivedData() {
        Bundle bundle= getArguments();
        offer = (NewOffer) bundle.getSerializable(MessagesString.OFFER);
    }

    private void initializeWidgets(View rootView) {
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        btnEditOffer = (Button)rootView.findViewById(R.id.btnEditOffer);
        btnSubmitOffer = (Button)rootView.findViewById(R.id.btnSubmitOffer);
        btnShowStatus = (Button)rootView.findViewById(R.id.btnStatus);
        btnMakeNewOffer = (Button)rootView.findViewById(R.id.btnMakeAnotherOffer);
        btnEditOfferOngoing = (Button)rootView.findViewById(R.id.btnEditOfferOnGoing);
        btnMakeNewOfferOngoing = (Button)rootView.findViewById(R.id.btnMakeNewOfferOnGoing);
        btnDeleteOffer = (Button)rootView.findViewById(R.id.btnDelteOfferOnGoing);
        btnAcceptOffer = (Button)rootView.findViewById(R.id.btnAcceptOffersToMe);
        btnRejectOffer = (Button)rootView.findViewById(R.id.btnRejectOffersToMe);
        btnCounterOffer = (Button)rootView.findViewById(R.id.btnCounterOffer);
        layoutHolderOnGoing = (LinearLayout)rootView.findViewById(R.id.layout_ongoing_buttons_holder);
        layoutHolderCounter = (LinearLayout)rootView.findViewById(R.id.layout_counter_buttons_holder);
    }

    private void setLayoutAndButtonProperty() {
        if("view".equalsIgnoreCase(calledFor)) {
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
    }


    private void setListeners() {
        mRecyclerView.addOnItemTouchListener(reviewOfferPresenter.getOnRecyclerItemClickListener());
        btnMakeNewOffer.setOnClickListener(reviewOfferPresenter.getOnMakeOfferClickListener());
        btnSubmitOffer.setOnClickListener(reviewOfferPresenter.getSubmitOfferOnClickListener());
        btnEditOffer.setOnClickListener(reviewOfferPresenter.getOnMakeOfferClickListener());
        btnEditOfferOngoing.setOnClickListener(reviewOfferPresenter.getOnMakeOfferClickListener());
        btnMakeNewOfferOngoing.setOnClickListener(reviewOfferPresenter.getOnMakeOfferClickListener());
        btnDeleteOffer.setOnClickListener(reviewOfferPresenter.getDeleteOfferOnClickListener());
        btnAcceptOffer.setOnClickListener(reviewOfferPresenter.getAcceptOfferOnClickListener());
        btnRejectOffer.setOnClickListener(reviewOfferPresenter.getRejectOfferOnClickListener());
        btnCounterOffer.setOnClickListener(reviewOfferPresenter.getOnCounterOfferClickListener());
    }

    private void createAndSetLayoutManager() {
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ReviewOfferGridAdapter(getContext(), listSelectedMine,listSelectedHis, "review");
        mLayoutManager.setSpanSizeLookup(reviewOfferPresenter.getSpanSizeLookup());
        mRecyclerView.setAdapter(mAdapter);
        adapter = new NewPostListOfferAdapter(getContext(), listSelectedMine, "review");
        adapter = new NewPostListOfferAdapter(getContext(),listSelectedHis, "review");


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

}
