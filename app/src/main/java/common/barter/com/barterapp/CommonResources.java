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
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.postad.PostAdA;

/**
 * Created by amitpa on 8/20/2015.
 */
public class CommonResources {


        static String flowForOffers;
        Context context;
        public CommonResources(Context context){
                this.context = context;
        }
        public static ArrayList<String> list_of_Cities= new ArrayList<String>( Arrays.asList("Adilabad","Hanamkonda","Karimnagar","Mahabubnagar","Medak","Nalgonda","Nizamabad","Peddapalli","Sangareddy","Suryapet","Wanaparthy","Warangal","Hyderabad","Secunderabad","Anantapur","Chittoor","Cuddapah","Hindupur","Kurnool","Nandyal","Proddatur","Tirupati","Bhimavaram","Eluru","Gudivada","Gudur","Guntur","Khammam","Machilipatnam","Narasaraopet","Nellore","Prakasam","Tadepalligudem","Tenali","Vijayawada","Amalapuram","Anakapalle","Kakinada","Parvathipuram","Rajahmundry","Srikakulam","Visakhapatnam","Vizianagaram","Dibrugarh","Nagaon","Sibsagar","Tinsukia","Cachar","Darrang","Goalpara","Guwahati","Nalbari","Begusarai","Darbhanga","East Champaran","Madhubani","Muzaffarpur","Purnea","Saharsa","Samastipur","Saran","Sitamarhi","Siwan","West Champaran","Aurangabad(Bihar)","Bhagalpur","Bhojpur","Gaya","Monghyr","Nalanda","Nawadha","Patna","Rohtas","Vaishali","Bastar","Bilaspur","Durg","Raigarh","Raipur","Delhi","New Delhi Central","New Delhi","Ahmedabad","Banasanktha","Gandhinagar","Mahesana","Patan","Sabarkantha","Amreli","Bhavnagar","Gondal","Jamnagar","Junagadh","Kutch","Porbandar","Rajkot","Surendranagar","Anand","Bardoli","Bharuch","Kheda","Navsari","Panchmahals","Surat","Vadodara","Valsad","Ambala","Bhiwani","Faridabad","Gurgaon","Hissar","Karnal","Kurukshetra","Rohtak","Sonepat","Chamba","Dehra Gopipur","Dharamsala","Hamirpur","Mandi","Rampur Bushahr","Shimla","Solan","Una","Baramulla","Jammu","Leh","Rajouri","Srinagar","Udhampur","Dhanbad","Giridih","Hazaribagh","Palamau","Ranchi","Santhal Parganas","Singhbhum","Bengaluru","Channapatna","Bagalkot","Belgaum","Bellary","Bidar","Bijapur","Chikodi","Dharwad","Gadag","Gokak","Gulbarga","Haveri","Karwar","Raichur","Sirsi","Chikmagalur","Chitradurga","Hassan","Kodagu","Kolar","Mandya","Mangalore","Mysore","Nanjangud","Puttur","Shimoga","Tumkur","Udupi","Calicut","Cannanore","Kasaragod","Manjeri","Ottapalam","Palghat","Thalassery","Tirur","Vadakara","Alleppey","Alwaye","Changanacherry","Ernakulam","Idukki","Irinjalakuda","Kottayam","Lakshadweep","Mavelikara","Trichur","Pathanamthitta","Quilon","Tiruvalla","Trivandrum","Balaghat","Bhopal","Chhatarpur","Chhindwara","Hoshangabad","Rewa","Sagar","Shahdol","Vidisha","Guna","Gwalior","Morena","Indore City","Indore Moffusil","Jabalpur","Khandwa","Mandsaur","Ratlam","Sehore","Ujjain","Aurangabad(Maharashtra)","Beed","Bhusaval","Dhule","Jalgaon","Malegaon","Nanded","Nasik","Osmanabad","Pharbhani","Goa","Kolhapur","Ratnagiri","Sangli","Sindhudurg","Mumbai","New Mumbai","Raigad","Thane","Akola","Amaravati","Buldana","Chandrapur","Nagpur City","Nagpur Moffusil","Wardha","Yeotmal","Ahmednagar","Pandharpur","Pune City","Pune Moffusil","Satara","Shrirampur","Solapur","Manipur","Mizoram","Nagaland","Agartala","Arunachal Pradesh","Dharmanagar","Meghalaya","Aska","Berhampur","Kalahandi","Koraput","Phulbani","Balasore","Bhadrak","Bhubaneswar","Cuttack","Mayurbhanj","Puri","Balangir","Dhenkanal","Keonjhar","Sambalpur","Sundargarh","Chandigarh","Ludhiana City","Ludhiana Moffusil","Patiala","Sangrur","Amritsar","Bhatinda","Faridkot","Ferozpur","Gurdaspur","Hoshiarpur","Jalandhar","Kapurthala","Ajmer","Beawar","Bhilwara","Chittorgarh","Dungarpur","Kota","Tonk","Udaipur","Alwar","Bharatpur","Dholpur","Jaipur City","Jaipur Moffusil","Sawaimadhopur","Barmer","Bikaner","Churu","Jhunjhunu","Jodhpur","Nagaur","Pali","Sikar","Sirohi","Sriganganagar","Anna Road","Arakkonam","Chengalpattu","Chennai","Kanchipuram","Pondicherry","Tambaram","Tiruvannamalai","Vellore","Coimbatore","Dharmapuri","Erode","Krishnagiri","Namakkal","Nilgiris","Pollachi","Salem","Tirupattur","Tirupur","Dindigul","Kanniyakumari","Karaikudi","Kovilpatti","Madurai","Ramanathapuram","Sivaganga","Theni","Tirunelveli","Tuticorin","Virudhunagar","Cuddalore","Karur","Kumbakonam","Mayiladuthurai","Nagapattinam","Pattukottai","Pudukkottai","Srirangam","Thanjavur","Tiruchirapalli","Vriddhachalam","Agra","Aligarh","Bulandshahar","Etah","Etawah","Jhansi","Mainpuri","Mathura","Allahabad","Ghazipur","Jaunpur","Mirzapur","Pratapgarh","Varanasi","Bareilly","Bijnor","Budaun","Hardoi","Kheri","Meerut","Moradabad","Muzaffarnagar","Saharanpur","Shahjahanpur","Azamgarh","Bahraich","Ballia","Basti","Deoria","Gonda","Gorakhpur","Banda","Fatehgarh","Fatehpur","Kanpur City","Kanpur Moffusil","Barabanki","Faizabad","Ghaziabad","Lucknow","Lucknow GPO","Rae Bareilly","Sitapur","Sultanpur","Almora","Chamoli","Dehradun","Nainital","Pauri","Pithoragarh","Tehri","Alipore","Barabazaar","Barasat","Birbhum","Calcutta South","Kolkata Central","Kolkata East","Kolkata GPO","Kolkata North","Murshidabad","Nadia North","Nadia South","North Presidency","South Presidency","A - N Islands","Cooch Behar","Darjeeling","Jalpaiguri","Malda","Sikkim","West Dinajpur","Asansol","Bankura","Burdwan","Contai","Hooghly","Howrah","Midnapore","Purulia","Tamluk"));
        public static ArrayList<String> list_of_Localities;
        public static Stack<String> headerStack = new Stack<String>();

        public static ArrayList<String> getListOfCities() {
//
                Collections.sort(list_of_Cities);
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


        public static void setListOfLocalities(ArrayList<String> localities){
                CommonResources.list_of_Localities = localities;
        }

        public static ArrayList<String> getListOfLocalities() {
                return list_of_Localities;

        }

    public static final Categories categories[] = {
            new Categories("Books",R.drawable.search),
            new Categories("Entertainment",R.drawable.search),
            new Categories("Electronics",R.drawable.search),
            new Categories("Gadgets",R.drawable.search),
            new Categories("Home & Lifestyle",R.drawable.search),
            new Categories("Child Care",R.drawable.search),
            new Categories("Sports & Fitness",R.drawable.search),
            new Categories("Fashion",R.drawable.search),
            new Categories("Automobile",R.drawable.search),
            new Categories("Coupons/Vouchers",R.drawable.search),
            new Categories("Services",R.drawable.search),
            new Categories("Others",R.drawable.search)
    };


        public static ArrayList<String> getSubCategories(String categoryName){
                ArrayList<String> subCategories = new ArrayList<String>();
                if(categoryName.equals("Books")){
                        subCategories.add("All Posts in Books");
                        subCategories.add("Literature & Fiction");
                        subCategories.add("Non-Fiction");
                        subCategories.add("Others");
                }else if("Entertainment".equals(categoryName)){
                        subCategories.add("All Posts in Entertainment");
                        subCategories.add("Gaming");
                        subCategories.add("Movies & TV shows");
                        subCategories.add("Music");
                        subCategories.add("Posters");
                }
                else if(categoryName.equals("Electronics")){
                        subCategories.add("All Posts in Electronics");
                        subCategories.add("Computers & Accessories");
                        subCategories.add("Tools & Equipment");
                        subCategories.add("Large Appliances");
                        subCategories.add("Small Appliances");
                }else if(categoryName.equals("Gadgets")){
                        subCategories.add("All Posts in Gadgets");
                        subCategories.add("Mobiles & Tablets");
                        subCategories.add("Mobile Accessories");
                        subCategories.add("Cameras");
                        subCategories.add("Camera Accessories");
                        subCategories.add("Speakers & Headsets");
                        subCategories.add("Music Players");
                        subCategories.add("Storage Devices");
                }else if("Home & Lifestyle".equals(categoryName)){
                        subCategories.add("All Posts in Home & Lifestyle");
                        subCategories.add("Kitchen");
                        subCategories.add("Kitchen Appliances");
                        subCategories.add("Home Furnishing");
                        subCategories.add("Furniture");
                        subCategories.add("Home Décor");
                        subCategories.add("Housekeeping");
                        subCategories.add("Luggage & Travel");
                }else if(categoryName.equals("Child Care")){
                        subCategories.add("All Posts in Child Care");
                        subCategories.add("Toys");
                        subCategories.add("Childcare");
                        subCategories.add("Scooter & Rideons");
                        subCategories.add("Clothing & Footwear");
                        subCategories.add("Kid Sports");

                }
                else if(categoryName.equals("Sports & Fitness")){
                        subCategories.add("All Posts in Sports & Fitness");
                        subCategories.add("Cardio Equipments");
                        subCategories.add("Circuit Training");
                        subCategories.add("Sports");
                        subCategories.add("Accessories");

                }else if(categoryName.equals("Fashion")){
                        subCategories.add("All Posts in Fashion");
                        subCategories.add("Men");
                        subCategories.add("Women");

                }else if(categoryName.equals("Automobile")){
                        subCategories.add("All Posts in Automobile");
                        subCategories.add("Cars");
                        subCategories.add("Car Accessories");
                        subCategories.add("Two-Wheelers");
                        subCategories.add("Accessories");

                }else if("Coupons/Vouchers".equals(categoryName)){
                        subCategories.add("All Posts in Coupons & Vouchers");
                        subCategories.add("Movie Tickets");
                        subCategories.add("Concert Tickets");
                        subCategories.add("Discount Coupons");
                        subCategories.add("Gift Vouchers");
                        subCategories.add("Sports Entry Passes");

                }
                else if(categoryName.equals("Services")){
                        subCategories.add("All Posts in Services");
                        subCategories.add("Delivery");
                        subCategories.add("Repair");
                        subCategories.add("Cleaning");
                        subCategories.add("Consulting");
                        subCategories.add("Design");
                        subCategories.add("Sale");
                        subCategories.add("Miscellaneous");
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
                        if(MessagesString.SHARED_PREFS_UNIQUE_ID.equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setUserid(value);
                        }else if(MessagesString.SHARED_PREFS_PERSON_NAME.equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setPersonName("null".equalsIgnoreCase(value) ? null : value);
                        }else if(MessagesString.SHARED_PREFS_GENDER.equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setGender(value);
                        }else if(MessagesString.SHARED_PREFS_EMAIL.equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setEmail(value);
                        }else if(MessagesString.SHARED_PREFS_MOBILE.equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setMobilenum("null".equalsIgnoreCase(value) ? null : value);
                        }else if(MessagesString.SHARED_PREFS_IS_MOBILE_VERIFIED.equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setMob_verified(value);
                        }else if(MessagesString.SHARED_PREFS_LOGIN_MODE.equalsIgnoreCase(key)){
                                LoginDetails.getInstance().setLoginMethod(value);
                        }
                        saveToSharedPrefs(key,value);
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
                String staticURL = "http://192.168.0.105:80/" ; //URL
                //String staticURL = "http://49.205.67.62:80/" ;

                return staticURL;
        }

        //Hides the keyboard
        public static void hideKeyboard( Activity activity) {
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

        public static boolean isValidMobile(String mobilenum) {

                //String MOBILE_PATTERN = "^[1-9][0-9]{9}$";
                String MOBILE_PATTERN =MessagesString.VALID_MOBILE;
                Pattern pattern = Pattern.compile(MOBILE_PATTERN);
                Matcher matcher = pattern.matcher(mobilenum);
                return matcher.matches();
        }

        public static boolean isKeyBoardOn(Activity activity){
                InputMethodManager imm = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isAcceptingText()) {
                       return true;
                } else {
                        return false;
                }
        }
        public static boolean isValidEmail(String email) {
//                String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//                Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//                Matcher matcher = pattern.matcher(email);
//                return matcher.matches();
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }



}
