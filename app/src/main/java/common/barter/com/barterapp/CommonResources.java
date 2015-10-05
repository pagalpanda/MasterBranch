package common.barter.com.barterapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by amitpa on 8/20/2015.
 */
public class CommonResources {


        static String flowForOffers;
        Context context;
        CommonResources(Context context){
                this.context = context;
        }
        public static ArrayList<String> list_of_Cities= new ArrayList<String>( Arrays.asList("Adilabad","Ahmedabad","Ahmedabad City","Alleppey","Alwaye","Amalapuram","Ambala","Amreli","Anakapalle","Anand","Anantapur","Aurangabad(Bihar)","Bagalkot","Banasanktha","Bangalore","Baramulla","Bardoli","Bastar","Begusarai","Belgaum","Bellary","Bhagalpur","Bharuch","Bhavnagar","Bhimavaram","Bhiwani","Bhojpur","Bidar","Bijapur","Bilaspur","Cachar","Calicut","Cannanore","Chamba","Champaran","Changanacherry","Channapatna","Chikmagalur","Chikodi","Chitradurga","Chittoor","Cuddapah","Darbhanga","Darrang","Dehra Gopipur","Delhi","Dhanbad","Dharamsala","Dharwad","Dibrugarh","Durg","East Champaran","Eluru","Ernakulam","Faridabad","Gadag","Gandhinagar","Gaya","Giridih","Goalpara","Gokak","Gondal","Gudivada","Gudur","Gulbarga","Guntur","Gurgaon","Guwahati","Hamirpur","Hanamkonda","Hassan","Haveri","Hazaribagh","Hindupur","Hissar","Hyderabad","Idukki","Irinjalakuda","Jammu","Jamnagar","Junagadh","Kakinada","Karimnagar","Karnal","Karwar","Kasaragod","Khammam","Kheda","Kodagu","Kolar","Kottayam","Kurnool","Kurukshetra","Kutch","Lakshadweep","Leh","Machilipatnam","Madhubani","Mahabubnagar","Mahesana","Mandi","Mandya","Mangalore","Manjeri","Medak","Monghyr","Muzaffarpur","Mysore","Nagaon","Nalanda","Nalbari","Nalgonda","Nandyal","Nanjangud","Narasaraopet","Navsari","Nawadha","Nellore","New Delhi","New Delhi Central","Nizamabad","Ottapalam","Palamau","Palghat","Panchmahals","Parvathipuram","Patan","Patna","Peddapalli","Porbandar","Prakasam","Proddatur","Purnea","Puttur","Raichur","Raigarh","Raipur","Rajahmundry","Rajkot","Rajouri","Rampur Bushahr","Ranchi","Rohtak","Rohtas","Sabarkantha","Saharsa","Samastipur","Sangareddy","Santhal Parganas","Saran","Secunderabad","Shimla","Shimoga","Sibsagar","Singhbhum","Sirsi","Sitamarhi","Siwan","Solan","Sonepat","Srikakulam","Srinagar","Surat","Surendranagar","Suryapet","Tadepalligudem","Tenali","Thalassery","Tinsukia","Tirupati","Tirur","Tumkur","Udhampur","Udupi","Una","Vadakara","Vadodara","Vaishali","Valsad","Vijayawada","Visakhapatnam","Vizianagaram","Wanaparthy","Warangal"));
        public static ArrayList<String> list_of_Localities;

        public static ArrayList<String> getListOfCities() {
//                list_of_Cities = new ArrayList<String>();
//                list_of_Cities.add("Hyderabad");
//                list_of_Cities.add("Secunderabad");
//                list_of_Cities.add("Mohali");
//                list_of_Cities.add("Bangalore");
//                list_of_Cities.add("Mumbai");
//                list_of_Cities.add("Delhi");
//                list_of_Cities.add("Bhubaneswar");
//
//                Collections.sort(list_of_Cities);

                return list_of_Cities;

        }


        public static String getMonthName(int month){
                if(month == 1)
                        return "Jan";
                else if(month == 2){
                        return "Feb";
                }else if(month == 3){
                        return "Mar";
                }else if(month == 4){
                        return "Apr";
                }else if(month == 5){
                        return "May";
                }else if(month == 6){
                        return "June";
                }else if(month == 7){
                        return "July";
                }else if(month == 8){
                        return "Aug";
                }else if(month == 9){
                        return "Sep";
                }else if(month == 10){
                        return "Oct";
                }else if(month == 11){
                        return "Nov";
                }else{
                        return "Dec";
                }
        }

        public static String convertDate(String sqlDate){
                SimpleDateFormat formatSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date date = null;
                try {
                        date = formatSql.parse(sqlDate);
                } catch (ParseException e) {
                        e.printStackTrace();
                }

                formatSql.format(date);

                Date cureentDate = new Date();
                if(cureentDate.getDate() == date.getDate() && date.getMonth() == cureentDate.getMonth() && date.getYear() == cureentDate.getYear()){
                        if(date.getHours() == cureentDate.getHours()){
                                return (cureentDate.getMinutes() - date.getMinutes() ) + " min";
                        }else {
                                return (cureentDate.getHours() - date.getHours() ) + " hr " + (cureentDate.getMinutes() - date.getMinutes() ) + " min";
                        }
                }
                else if(new Date().getYear() == date.getYear()){
                        return date.getDate()+" "+CommonResources.getMonthName(date.getMonth()+1);
                }

                return date.getDate()+" "+CommonResources.getMonthName(date.getMonth()+1)+ " "+ (date.getYear()+1900);

        }

        public static ArrayList<String> getListOfLocalities(String city) {
                list_of_Localities = new ArrayList<String>();
                if(city.equalsIgnoreCase("Hyderabad"))
                {
                        list_of_Localities.add("HyderabadA");
                        list_of_Localities.add("HyderabadB");
                        list_of_Localities.add("HyderabadC");
                        list_of_Localities.add("HyderabadD");
                }else if(city.equalsIgnoreCase("Secunderabad"))
                {
                        list_of_Localities.add("SecunderabadA");
                        list_of_Localities.add("SecunderabadB");
                        list_of_Localities.add("SecunderabadC");
                        list_of_Localities.add("SecunderabadD");
                }else if(city.equalsIgnoreCase("Mohali"))
                {
                        list_of_Localities.add("MohaliA");
                        list_of_Localities.add("MohaliB");
                        list_of_Localities.add("MohaliC");
                        list_of_Localities.add("MohaliD");

                }else if(city.equalsIgnoreCase("Bangalore"))
                {
                        list_of_Localities.add("BangaloreA");
                        list_of_Localities.add("BangaloreB");
                        list_of_Localities.add("BangaloreC");
                        list_of_Localities.add("BangaloreD");
                }else if(city.equalsIgnoreCase("Mumbai"))
                {
                        list_of_Localities.add("MumbaiA");
                        list_of_Localities.add("MumbaiB");
                        list_of_Localities.add("MumbaiC");
                        list_of_Localities.add("MumbaiD");
                }else if(city.equalsIgnoreCase("Bhubaneswar"))
                {
                        list_of_Localities.add("BhubaneswarA");
                        list_of_Localities.add("BhubaneswarB");
                        list_of_Localities.add("BhubaneswarC");
                        list_of_Localities.add("BhubaneswarD");

                }else if(city.equalsIgnoreCase("Delhi"))
                {
                        list_of_Localities.add("DelhiA");
                        list_of_Localities.add("DelhiB");
                        list_of_Localities.add("DelhiC");
                        list_of_Localities.add("DelhiD");
                }

                Collections.sort(list_of_Localities);

                return list_of_Localities;

        }

    public static final Categories categories[] = {
            new Categories("Books",R.drawable.search),
            new Categories("Electronics",R.drawable.search),
            new Categories("Sports",R.drawable.search),
            new Categories("Gadgets",R.drawable.search),
            new Categories("Pets",R.drawable.search),
            new Categories("Fashion",R.drawable.search),
            new Categories("Child Care",R.drawable.search),
            new Categories("School or Office",R.drawable.search),
            new Categories("School or Office",R.drawable.search),
            new Categories("School or Office",R.drawable.search),
            new Categories("School or Office",R.drawable.search),
            new Categories("Others",R.drawable.search)
    };


        public static ArrayList<String> getSubCategories(String categoryName){
                ArrayList<String> subCategories = new ArrayList<String>();
                if(categoryName.equals("Books")){
                        subCategories.add("All Posts in Books");
                        subCategories.add("Literature & Fiction");
                        subCategories.add("Academic & Professional");
                        subCategories.add("Children & Teens");
                        subCategories.add("Business & Management");
                        subCategories.add("Religion & Spirituality");
                }else if(categoryName.equals("Electronics")){
                        subCategories.add("All Posts in Electronics");
                        subCategories.add("Computers & Accessories");
                        subCategories.add("Storage");
                        subCategories.add("Camera & Accessories");
                        subCategories.add("Home Appliances");
                        subCategories.add("Personal Grooming");
                }else if(categoryName.equals("Sports")){
                        subCategories.add("All Posts in Sports");
                        subCategories.add("Fitness Equipment");
                        subCategories.add("Outdoor Gear");
                        subCategories.add("Health Devices");

                }else if(categoryName.equals("Gadgets")){
                        subCategories.add("All Posts in Gadgets");
                        subCategories.add("Mobiles & Tablets");
                        subCategories.add("Camera & Accessories");
                        subCategories.add("Earphones & Headphones");

                }else if(categoryName.equals("Pets")){
                        subCategories.add("All Posts in Pets");
                        subCategories.add("Dogs");
                        subCategories.add("Cats");
                        subCategories.add("Pet Food");
                        subCategories.add("Others");
                }else if(categoryName.equals("Fashion")){
                        subCategories.add("All Posts in Fashion");
                        subCategories.add("Hand Bags");
                        subCategories.add("Watch");
                        subCategories.add("Shoes");
                        subCategories.add("Clothing");

                }else if(categoryName.equals("Child Care")){
                        subCategories.add("All Posts in Child Care");
                        subCategories.add("Toys and Games");
                        subCategories.add("Prams, Strollers & Walkers");

                }else if(categoryName.equals("School or Office")){
                        subCategories.add("All Posts in School or Office");
                        subCategories.add("Office Stationary");
                        subCategories.add("Tables & Chairs");
                        subCategories.add("Bags");

                }else if(categoryName.equals("Others")){

                }

                return subCategories;
        }



        SharedPreferences prefs;
        public String readLocation(){
                Object locationFromPrefs = loadFromSharedPrefs("location");
                if(locationFromPrefs == null){
                        //logic for reading current location should go here
                        return GlobalHome.location;
                }else{
                        return (String)locationFromPrefs;
                }
        }

        public void setUserDetailsInSharedPref(Map<String,String> mapUserDetails){
                Set<String> keys = mapUserDetails.keySet();
                for(String key: keys){
                        String value = mapUserDetails.get(key);
                        if("uniqueid".equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setUserid(value);
                        }else if("personname".equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setPersonName("null".equalsIgnoreCase(value) ? null : value);
                        }else if("gender".equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setGender(value);
                        }else if("email".equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setEmail(value);
                        }else if("mobilenum".equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setMobilenum("null".equalsIgnoreCase(value) ? null : value);
                        }else if("ismobileverified".equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setMob_verified(value);
                        }
                        saveToSharedPrefs(key,mapUserDetails.get(key));
                }
        }
        Object obj;
        public  Object loadFromSharedPrefs(String key){
                prefs  = PreferenceManager.getDefaultSharedPreferences(context);
                Gson gson = new Gson();
                obj = null;
                if(prefs != null) {
                        String json = prefs.getString(key, "");
                        obj = gson.fromJson(json, String.class);
                }
                return obj;
        }

        public void saveToSharedPrefs(String key, Object objToSave){
                prefs  = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(objToSave, String.class);
                editor.putString(key, json);
                editor.commit();
        }


        public void navigateToPostAd(FragmentManager fragmentManager, String cameFrom) {
                // update the main content by replacing fragments


                Fragment fragment = null;
                fragment = new PostAdA(cameFrom);


                if (fragment != null) {

                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.setCustomAnimations(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out,R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
                                ft.add(R.id.frame_container, fragment).addToBackStack("post_ad").commit();


                } else {
                        // error in creating fragment
                        Log.e("MainActivity", "Error in creating fragment");
                }
        }



        public void navigateToPostDetails(FragmentManager fragmentManager, Post post, String calledFor) {
                // update the main content by replacing fragments


                Fragment fragment = null;
                fragment = new PostDetailsFragment(post, calledFor);


                if (fragment != null) {

//                        fragmentManager.beginTransaction()
//                                .replace(R.id.frame_container, fragment).addToBackStack("post_details").commit();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.setCustomAnimations(R.anim.zoomin,R.anim.zoomout,R.anim.zoomin,R.anim.zoomout);
                        ft.add(R.id.frame_container, fragment).addToBackStack("post_details").commit();


                } else {
                        // error in creating fragment
                        Log.e("MainActivity", "Error in creating fragment");
                }
        }
        public void clearBackStack(FragmentManager manager) {
                //FragmentManager manager = getSupportFragmentManager();
                if (manager.getBackStackEntryCount() > 0) {
                        FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
                        manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
        }


        public static String getURL(String phpName)
        {
                StringBuilder URL = new StringBuilder();

                URL.append(getStaticURL()).append(phpName).append(".php");
                return URL.toString();
        }
        public static String getStaticURL()
        {
                String staticURL = "http://192.168.0.106:80/" ; //URL
                //String staticURL = "http://49.205.72.242:80/" ;

                return staticURL;
        }

        //Hides the keyboard
        public void hideKeyboard( Activity activity) {
                // Check if no view has focus:
                if(activity != null) {
                        View view = activity.getCurrentFocus();
                        if (view != null) {
                                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                }
        }

        public void showProgressWheel(ProgressDialog pDialog, String message){

                if(message !=null && !message.equalsIgnoreCase(""))
                        pDialog.setMessage(message);
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();

        }

        public void dismissProgressWheel(ProgressDialog pDialog){
                pDialog.dismiss();
        }

        public void hideKeyBoard(Activity activity, View view){
                InputMethodManager in = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }

        public  Object loadListFromSharedPrefs( Type type, String key){
                prefs  = PreferenceManager.getDefaultSharedPreferences(context);
                Gson gson = new Gson();
                obj = null;
                if(prefs != null) {
                        String json = prefs.getString(key, "");

                        obj = gson.fromJson(json, type);
                        //obj = gson.fromJson(json, Object.class);

                }
                return obj;
        }
        public void saveListToSharedPrefs(Type type,Object objToSave, String key){
                prefs  = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(objToSave, type);
                editor.putString(key, json);
                editor.commit();
        }

        public static boolean isNetworkAvailable(Activity activity) {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) (activity.getSystemService(Context.CONNECTIVITY_SERVICE));
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        public void logoutUserFromAllAccounts(){
                LoginDetails.getInstance().resetDetails();
                saveToSharedPrefs("isLoggedIn", "false");
                saveToSharedPrefs("uniqueid",null);
                saveToSharedPrefs("personname", null);
                saveToSharedPrefs("gender", null);
                saveToSharedPrefs("email", null);
                saveToSharedPrefs("username", null);
                saveToSharedPrefs("mobilenum", null);
                saveToSharedPrefs("ismobileverified", null);


                //resources.setUserDetailsInSharedPref(mapUserDetails);
        }

        Type mapType;

        public int getToolBarHeight() {
                return toolBarHeight;
        }

        private int toolBarHeight;

        public void setToolBarHeight(int toolBarHeight) {
                this.toolBarHeight = toolBarHeight;
        }
}
