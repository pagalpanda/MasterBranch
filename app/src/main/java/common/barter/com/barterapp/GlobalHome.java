package common.barter.com.barterapp;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class GlobalHome extends ActionBarActivity implements LocationAddress.LocationCallback, LocationListener{
    protected DrawerLayout mDrawerLayout;
    //private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    static String location;

    private GlobalHome globalHome;
    private ArrayAdapter<String> citiesAdapter;
    //ArrayList<String> list_of_Cities;
    Spinner spinner_location;
    SharedPreferences prefs; //  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    RecyclerView mRecyclerView;                           // Declaring RecyclerView

    public RecyclerView.Adapter getmAdapter() {
        return mAdapter;
    }



    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;
    static Toolbar toolbar;
    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;


    public static String navBarSelectedItem;


    AutoCompleteTextView actv;
    TextView tvLocationDialogText;
    Button btnSetCurrentLocation;
    private boolean readLocationForce;
    LocationsDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_global_home);
        prefs  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.globalHome = this;
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        new CommonResources(getApplicationContext()).setToolBarHeight(toolbar.getHeight());
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
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (position != 0) {
                            displayView(position - 1);
                            setActionBarTitleForNavBar(view);
                        } else {
                            dialog = new LocationsDialog(GlobalHome.this,globalHome, mRecyclerView, CommonResources.getListOfCities(), false);
                            dialog.show();
                            actv = dialog.getAutoCompleteTextView();
                            tvLocationDialogText = dialog.getTvLocationDialogText();
                            btnSetCurrentLocation = dialog.getBtnSetCurrentLocation();
                            actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    new CommonResources(getApplicationContext()).hideKeyBoard(globalHome,view);
                                    String cityName = (String)parent.getItemAtPosition(position);//(String) CommonResources.getListOfCities().get(position);
                                    //actv.getListSelection();
                                    saveToSharedPrefs("location", cityName);
                                    GlobalHome.location = cityName;
                                    if (mRecyclerView != null)
                                        mRecyclerView.getAdapter().notifyDataSetChanged();
                                    dialog.cancel();
                                }
                            });
                            actv.setHint(MessagesString.HINT_CITY);

//                            actv.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
//                                @Override
//                                public void onDismiss() {
//                                    readLocationForce = false; // Setting the forceful location change flag to false if the user clicks on the change loc button but doesn't enable location service and dismisses the actv
//                                }
//                            });
                            btnSetCurrentLocation.setVisibility(View.VISIBLE);
                            btnSetCurrentLocation.setText(MessagesString.LOCATION_DIALOG_BUTTON_TEXT);
                            btnSetCurrentLocation.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(CommonResources.isNetworkAvailable(GlobalHome.this)){
                                        readLocationForce = true;
                                        if(checkGPS()){
                                            isExecuting = true;
                                            new LocationAddress(getApplicationContext(),GlobalHome.this).execute();

                                        }
                                    }else {
                                        Toast.makeText(getApplicationContext(),MessagesString.CONNECT_TO_INTERNET,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            tvLocationDialogText.setText(MessagesString.DIALOG_TITLE_TEXT_CITY);

                        }

                    }


                })
        );


        // enabling action bar app icon and behaving it as toggle button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f68b23")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);





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




        mDrawerLayout.setDrawerListener(mDrawerToggle);


        if(MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(GlobalHome.location)){
                checkGPS();

        }
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }



    }


    public Toolbar getToolbar() {
        return toolbar;
    }

    public boolean checkGPS() {
        locationManager = (LocationManager) GlobalHome.this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }


            this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 100.0F, this);




            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 100.0F, this);



        if (!gps_enabled && !network_enabled && (CommonResources.isNetworkAvailable(GlobalHome.this)) ) {
            // notify user
            final AlertDialog.Builder dialog = new AlertDialog.Builder(GlobalHome.this);
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
            return false;
        }else
            return true;

    }


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


    private void displayView(int position) {
        new CommonResources(getApplicationContext()).clearBackStack(getSupportFragmentManager());
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft  = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                ft.setCustomAnimations(R.anim.enter_from_left,R.anim.abc_fade_out,R.anim.enter_from_left,R.anim.abc_fade_out);
                break;
            case 1:
                fragment = new PostsFragment("myposts", "");
                ft.setCustomAnimations(R.anim.enter_from_left,R.anim.abc_fade_out,R.anim.enter_from_left,R.anim.abc_fade_out);
                break;
            case 2:
                fragment = new ChooseOptionsOffersFragment();
                ft.setCustomAnimations(R.anim.enter_from_left,R.anim.abc_fade_out,R.anim.enter_from_left,R.anim.abc_fade_out);
                break;
            case 3:
                fragment = new WishList();
                ft.setCustomAnimations(R.anim.enter_from_left,R.anim.abc_fade_out,R.anim.enter_from_left,R.anim.abc_fade_out);
                break;
            case 4:
                if (isLoggedInFromClass())
                {
                    fragment = new ManageUser();
                    ft.setCustomAnimations(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out,R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
                }
                else
                {
                    fragment = new LoginParentFragment();
                    ft.setCustomAnimations(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out,R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
                }
                break;
            default:
                break;
        }

        if (fragment != null) {


            switch (position) {
                case 0:

                    ft.replace(R.id.frame_container, fragment).commit();
                    break;
                default:
                    ft.replace(R.id.frame_container, fragment).commit();
                    break;
            }
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mRecyclerView);
            getmDrawerToggle().setDrawerIndicatorEnabled(true);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
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
        mDrawerToggle.syncState();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean isLoggedInFromClass(){

// COMMENTED FOR TESTING

        if(LoginDetails.getInstance().getUserid() == null){
            //logic for reading current location should go here
            return false;
        }
        return true;

    }
    public Boolean isLoggedInFromPrefs(){
        Object LoggedInFromPrefs = loadFromSharedPrefs("uniqueid");
        if(LoggedInFromPrefs == null){
            //logic for reading current location should go here
            return false;
        }else{
            return true;
        }
    }


    Object obj;
    public  Object loadFromSharedPrefs(String key){
        Gson gson = new Gson();
        obj = null;
        if(prefs != null) {
            String json = prefs.getString(key, "");
            obj = gson.fromJson(json, String.class);
        }
        return obj;
    }

    public void saveToSharedPrefs(String key, Object objToSave){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(objToSave,String.class);
        editor.putString(key, json);
        editor.commit();
    }


    @Override
    public void UpdateMyLocation(SharedPreferences prefs) {
        isExecuting = false;
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
        readLocationForce = false;
        mAdapter.notifyDataSetChanged();
        if(!MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(GlobalHome.location)) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    saveToSharedPrefs("location", GlobalHome.location);
                }
            });
            t.start();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if(CommonResources.isNetworkAvailable(GlobalHome.this) && !isExecuting){ // If internet is available and location change service is not executing now
            if(MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(this.location)){ // is location has already been set
                new LocationAddress(getApplicationContext(),this).execute();
                isExecuting = true;
            }else if(readLocationForce) {// If the location is already set, but the user wants to read from device by clicking the "Current Location" button
                readLocationForce = false;
                new LocationAddress(getApplicationContext(),this).execute();
                isExecuting = true;
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        }

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

            //mGoogleApiClient.connect();
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
    LocationManager locationManager;
    boolean isExecuting;
}