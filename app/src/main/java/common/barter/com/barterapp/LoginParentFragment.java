

package common.barter.com.barterapp;

        import android.app.ActionBar;
        import android.app.Activity;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTabHost;

        import android.support.v4.view.ViewPager;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TabHost;
        import android.widget.Toast;

        import org.apache.http.NameValuePair;
        import org.apache.http.message.BasicNameValuePair;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.concurrent.CountDownLatch;

/**
 * Created by amitpa on 8/18/2015.
 */

public class LoginParentFragment extends Fragment {



    private String[] tabs = { "Log in", "Sign Up"};
    Activity context;

    JSONParser jsonParser = new JSONParser();

    JSONArray posts = null;

    LoginFragmentAdapter adapter;
    ViewPager viewPager;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_proceed_offer, menu);
        super.onCreateOptionsMenu(menu, inflater);

        if (menu.findItem(R.id.action_confirm_make_offer) != null) {
            menu.findItem(R.id.action_confirm_make_offer).setVisible(false);
            menu.removeItem(R.id.action_confirm_make_offer);

        }
        if (menu.findItem(R.id.action_proceed_make_offer) != null) {
            menu.findItem(R.id.action_proceed_make_offer).setVisible(false);
            menu.removeItem(R.id.action_proceed_make_offer);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_proceed_make_offer) {

            //navigateToReview();
            return true;
        }
        return false;
    }


    public LoginParentFragment( ){


    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_make_offer, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        //((GlobalHome)getActivity()).getSupportActionBar().setHideOnContentScrollEnabled(true);
        tabLayout.addTab(tabLayout.newTab().setText(tabs[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[1]));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager);

        adapter = new LoginFragmentAdapter(getFragmentManager(),2);
        android.support.design.widget.FloatingActionButton  btnProceedToReview = (android.support.design.widget.FloatingActionButton)rootView.findViewById(R.id.fab2);
        btnProceedToReview.setVisibility(View.GONE);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                CommonResources.hideKeyboard(getActivity());
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return rootView;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }




}