

package common.barter.com.barterapp.Login;

        import android.app.Activity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;

        import android.support.v4.view.ViewPager;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;

        import org.json.JSONArray;

        import common.barter.com.barterapp.JSONParser;
        import common.barter.com.barterapp.R;

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
            return true;
        }
        return false;
    }


    public LoginParentFragment( ){


    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_make_offer, container, false);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setHasOptionsMenu(true);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        viewPager.destroyDrawingCache();
        adapter = new LoginFragmentAdapter(getFragmentManager());
        FloatingActionButton  btnProceedToReview = (FloatingActionButton)rootView.findViewById(R.id.fab2);
        btnProceedToReview.setVisibility(View.GONE);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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