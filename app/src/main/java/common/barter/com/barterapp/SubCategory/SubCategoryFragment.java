package common.barter.com.barterapp.SubCategory;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.Home.HomePresenter;
import common.barter.com.barterapp.PostsFragment;
import common.barter.com.barterapp.R;
import android.support.design.widget.FloatingActionButton;
import common.barter.com.barterapp.globalhome.GlobalHome;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubCategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubCategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Activity context;
    private OnFragmentInteractionListener mListener;

    public static String selectedCategory;
    ListView lvSubCategoris;
    TextView tvTitleOfFragment;
    GlobalHome holdingActivity;
    FloatingActionButton floatingButton;
    SubCategoryPresenter subCategoryPresenter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubCategoryFragment newInstance(String param1, String param2) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public SubCategoryFragment(String selectedCategory) {
        this();
        this.selectedCategory = selectedCategory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public void onResume() {
        super.onResume();


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        holdingActivity = (GlobalHome) getActivity();
        View rootView = inflater.inflate(R.layout.fragment_sub_category, container, false);

        initializeWidgets(rootView);
        setPresenter();
        setListeners();

        return rootView;
    }

    private void setListeners() {
        lvSubCategoris.setOnItemClickListener(lvSubCategorisListener);
        floatingButton.setOnClickListener(floatingButtonListener);
    }

    private void setPresenter() {
        if(subCategoryPresenter==null){
            subCategoryPresenter = new SubCategoryPresenter();
        }
        subCategoryPresenter.setSubCategoryFragment(this);
    }

    private void initializeWidgets(View view) {
        holdingActivity.setActionBarTitle(getSelectedCategory());
        holdingActivity.getmDrawerToggle().setDrawerIndicatorEnabled(false);
        setHasOptionsMenu(true);
        lvSubCategoris = (ListView)view.findViewById(R.id.listViewSubCategories);
        lvSubCategoris.setAdapter(new SubCategoriesListAdapter((GlobalHome) getActivity(), CommonResources.getSubCategories(getSelectedCategory())));
        floatingButton = (FloatingActionButton)view.findViewById(R.id.fab);
    }

    AdapterView.OnItemClickListener lvSubCategorisListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView tvSubCategory = (TextView) view.findViewById(R.id.tvSubCategoryName);
            String subCategory = tvSubCategory.getText().toString();

            subCategoryPresenter.navigateToViewPosts(subCategory);
        }
    };

    View.OnClickListener floatingButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            subCategoryPresenter.navigateToPostAd();
        }
    };

    void navigateToPostAd() {
        (new CommonResources(getContext())).navigateToPostAd(getFragmentManager(), "floatingButtonListener");
    }

    void navigateToViewPosts(String subCategory) {

        Fragment fragment = new PostsFragment("userview", subCategory);
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_container, fragment).addToBackStack("view_posts").commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }






    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the holdingActivity and potentially other fragments contained in that
     * holdingActivity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public static String getSelectedCategory() {
        return selectedCategory;
    }

    public static void setSelectedCategory(String selectedCategory) {
        SubCategoryFragment.selectedCategory = selectedCategory;
    }
}
