package common.barter.com.barterapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import common.barter.com.barterapp.forgotpassword.ForgotPassword;


public class GlobalHome extends ActionBarActivity implements LocationAddress.LocationCallback, LocationListener{


    protected DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;
    AutoCompleteTextView actv;
    TextView tvLocationDialogText;
    Button btnSetCurrentLocation;
    LocationsDialog dialog;
    static String location;
    private GlobalHome globalHome;
    SharedPreferences prefs;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    public static String navBarSelectedItem;
    private boolean readLocationForce;
    private GlobalHomePresenter presenter;
    private boolean isExecuting;
    private Bundle savedInstanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_home);
        this.savedInstanceState = savedInstanceState;
        prefs  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.globalHome = this;
        presenter = new GlobalHomePresenter(this);
        presenter.onCreateView();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.onLeftNavClicked(view, position);
            }
        }));


    }

    public void setDrawerListener() {
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void setActionBarToggler() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
      )
        {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0); // this disables the animation
            }
        };
    }

    public void setActionBarBehavior() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f68b23")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void displayHomeFragmentByDefault() {
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }


    public void showLocationDialog() {
        dialog = new LocationsDialog(GlobalHome.this, globalHome, mRecyclerView, CommonResources.getListOfCities(), false);
        dialog.show();

        actv = dialog.getAutoCompleteTextView();
        tvLocationDialogText = dialog.getTvLocationDialogText();
        btnSetCurrentLocation = dialog.getBtnSetCurrentLocation();

        actv.setOnItemClickListener(onLocationSelectListener);
        actv.setHint(MessagesString.HINT_CITY);
        btnSetCurrentLocation.setVisibility(View.VISIBLE);
        btnSetCurrentLocation.setText(MessagesString.LOCATION_DIALOG_BUTTON_TEXT);

        btnSetCurrentLocation.setOnClickListener(currentLocationClickListener);
        tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_CITY);

    }

    View.OnClickListener currentLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.onCurrentLocationClick();


        }
    };



    AdapterView.OnItemClickListener onLocationSelectListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            presenter.onLocationSelect(parent,view,position, prefs);

            new CommonResources(getApplicationContext()).hideKeyBoard(globalHome, view);
            if (mRecyclerView != null)
                mRecyclerView.getAdapter().notifyDataSetChanged();
            dialog.cancel();
        }
    };

    public void createLeftNavigationBar() {
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_items);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), true, "7"));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        //Setting default title for actionbar
        navBarSelectedItem = navMenuTitles[0];

        mAdapter = new NavDrawerListAdapter(this,navDrawerItems,location);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        new CommonResources(getApplicationContext()).setToolBarHeight(toolbar.getHeight());
    }


//    public Toolbar getToolbar() {
//        return toolbar;
//    }

    public void setActionBarTitleForNavBar( View view){
        TextView tv = (TextView)view.findViewById(R.id.title);
        navBarSelectedItem = tv.getText().toString();
        setActionBarTitle(navBarSelectedItem);
    }



    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_global_home, menu);

        return true;
    }

    //protected OnBackPressedListener onBackPressedListener;


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent objEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyUp(keyCode, objEvent);
    }

    @Override
    public void onBackPressed() {

        goBack();
    }

    public void goBack() {
        backMenuPressedAction();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getApplicationContext(),"Search",Toast.LENGTH_LONG).show();

                return true;

            case R.id.action_edit_post:
                return super.onOptionsItemSelected(item);
            case R.id.action_proceed_make_offer:
                return false;
            case R.id.action_confirm_make_offer:
                return false;
            case R.id.action_logout:
                return false;
            default:
                if(mDrawerToggle.isDrawerIndicatorEnabled()) {
                    Toast.makeText(getApplicationContext(), "In Global Home loading location2", Toast.LENGTH_LONG).show();
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                    return super.onOptionsItemSelected(item);
                }else{
                    backMenuPressedAction();
                    return false;
                }



        }
    }




    private void backMenuPressedAction() {

        Fragment f = this.getSupportFragmentManager().findFragmentById(R.id.frame_container);// returns current fragment
        String actionBarTitle = this.getSupportActionBar().getTitle().toString();
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(mRecyclerView);
        }else if(f instanceof SubCategoryFragment) {
            this.setActionBarTitle("Home");
            this.getmDrawerToggle().setDrawerIndicatorEnabled(true);
            getSupportFragmentManager().popBackStack();
        }else if(f instanceof PostsFragment){
            if (navMenuTitles[1].equalsIgnoreCase(actionBarTitle)){
                //coming back from my posts
                this.getmDrawerToggle().setDrawerIndicatorEnabled(true);
            }else {
                //coming back from all posts
                this.getmDrawerToggle().setDrawerIndicatorEnabled(false);
                this.setActionBarTitle(SubCategoryFragment.selectedCategory);
            }
            getSupportFragmentManager().popBackStack();
        }else if(f instanceof PostDetailsFragment){

            if(navMenuTitles[1].equalsIgnoreCase(actionBarTitle)){
                //coming back from my posts details
                this.getmDrawerToggle().setDrawerIndicatorEnabled(true);
            }else if(navMenuTitles[3].equalsIgnoreCase(actionBarTitle)){
                //coming back from WishList details
                this.getmDrawerToggle().setDrawerIndicatorEnabled(true);
            }else if(navMenuTitles[3].equalsIgnoreCase(actionBarTitle)){
                //coming back to Select Posts for Offer
                this.getmDrawerToggle().setDrawerIndicatorEnabled(false);
            }else {
                //coming back from all posts details
                this.getmDrawerToggle().setDrawerIndicatorEnabled(false);
                //this.setActionBarTitle(SubCategoryFragment.selectedCategory);
            }
            getSupportFragmentManager().popBackStack();
        }else if(f instanceof PostAdA){
            //this.getmDrawerToggle().setDrawerIndicatorEnabled(false);

            if("subcategories".equalsIgnoreCase(PostAdA.sMode)){
                getmDrawerToggle().setDrawerIndicatorEnabled(false);
                setActionBarTitle(SubCategoryFragment.selectedCategory);
            }else if(navMenuTitles[0].equalsIgnoreCase(PostAdA.sMode)){
                getmDrawerToggle().setDrawerIndicatorEnabled(true);
                setActionBarTitle(navMenuTitles[0]);
            }else if("edit".equalsIgnoreCase(PostAdA.sMode)){
                getmDrawerToggle().setDrawerIndicatorEnabled(false);
                setActionBarTitle(navMenuTitles[1]);
            }
            getSupportFragmentManager().popBackStack();
        }else if(f instanceof ForgotPassword){
            getSupportFragmentManager().popBackStack();
            getmDrawerToggle().setDrawerIndicatorEnabled(true);
            setActionBarTitle(MessagesString.HEADER_MY_ACCOUNT);
        }else if(f instanceof ChangePassword){
            getSupportFragmentManager().popBackStack();
            getmDrawerToggle().setDrawerIndicatorEnabled(true);
                setActionBarTitle(MessagesString.HEADER_MY_ACCOUNT);
        }else if(f instanceof OTPFragment){
            getSupportFragmentManager().popBackStack();
            getmDrawerToggle().setDrawerIndicatorEnabled(true);
            setActionBarTitle(MessagesString.HEADER_MY_ACCOUNT);
        }else if(f instanceof MakeOfferFragment){
            getSupportFragmentManager().popBackStack();
            getmDrawerToggle().setDrawerIndicatorEnabled(false);
            setActionBarTitle(CommonResources.headerStack.pop());
        }else if(f instanceof OTPFragment){
            getSupportFragmentManager().popBackStack();
            getmDrawerToggle().setDrawerIndicatorEnabled(true);
            setActionBarTitle(MessagesString.HEADER_MY_ACCOUNT);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);

    }


    public void displayView(int position) {
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        GlobalHomeFragmentFactory fragmentFactory = new GlobalHomeFragmentFactory(fragmentManager);
        new CommonResources(getApplicationContext()).clearBackStack(fragmentManager);
                if(position == 4) {
                    if (isLoggedInFromClass()) {
                        fragment = fragmentFactory.getFragment(41);
                    } else {
                        fragment = fragmentFactory.getFragment(42);
                    }
                }else{

                    fragment = fragmentFactory.getFragment(position);

                }

        if (fragment != null) {
            FragmentTransaction ft  = fragmentManager.beginTransaction();
            ft.replace(R.id.frame_container, fragment).commit();
        }
        setTitle(navMenuTitles[position]);
        mDrawerLayout.closeDrawer(mRecyclerView);
        getmDrawerToggle().setDrawerIndicatorEnabled(true);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    public ActionBarDrawerToggle getmDrawerToggle(){
        return mDrawerToggle;
    }
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        getmDrawerToggle().syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean isLoggedInFromClass(){
        if(LoginDetails.getInstance().getUserid() == null){
            return false;
        }
        return true;

    }

    @Override
    public void UpdateMyLocation(final SharedPreferences prefs) {
        setIsExecuting(false);
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        readLocationForce = false;
        mAdapter.notifyDataSetChanged();
        if(!MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(GlobalHome.location)) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    DeviceStoreUtil.saveToSharedPrefs(prefs, "location", GlobalHome.location);
                }
            });
            t.start();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if(CommonResources.isNetworkAvailable(GlobalHome.this) && !isExecuting()){ // If internet is available and location change service is not executing now
            if(MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(this.location)){ // is location has already been set
                new LocationAddress(getApplicationContext(),this).execute();
                setIsExecuting(true);
            }else if(isReadLocationForce()) {// If the location is already set, but the user wants to read from device by clicking the "Current Location" button
                setReadLocationForce(false);
                new LocationAddress(getApplicationContext(),this).execute();
                setIsExecuting(true);
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 14) {
            // If the error resolution was not successful we should not resolve further.

//                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
//                if(fragment!=null)
//                    fragment.onActivityResult(requestCode, resultCode, data);

            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof GoogleplusListener)
                    {
                        fragment.onActivityResult(requestCode, resultCode, data);
                        break;
                    }
                }
            }


        }

    }
    public void showDialogForEnablingGPS() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(MessagesString.LOCATION_DIALOG_MESSAGE);
        dialog.setPositiveButton(MessagesString.LOCATION_DIALOG_POSTIVE_TEXT, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                GlobalHome.this.startActivity(myIntent);
                //get gps
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                paramDialogInterface.dismiss();


            }
        });

        dialog.show();
    }



    public boolean isExecuting() {
        return isExecuting;
    }

    public void setIsExecuting(boolean isExecuting) {
        this.isExecuting = isExecuting;
    }

    public boolean isReadLocationForce() {
        return readLocationForce;
    }

    public void setReadLocationForce(boolean readLocationForce) {
        this.readLocationForce = readLocationForce;
    }

    public RecyclerView.Adapter getmAdapter() {
        return mAdapter;
    }

}