package common.barter.com.barterapp.reviewOffer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.Home.HomeFragment;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.Post;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.showOffer.ChooseOptionsOffersFragment;

/**
 * Created by vikramb on 13/07/16.
 */

public class MakeOffer extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Fetching Posts..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     * Creating product
     * */
    protected String doInBackground(String... args) {

        // Building Parameters
        HashMap<String,String> params = new HashMap <String,String>();
        JSONObject json = new JSONObject();
        if("create".equalsIgnoreCase(args[0])) {
            String uniqueid = LoginDetails.getInstance().getUserid();//(String) new CommonResources(getContext()).loadFromSharedPrefs("uniqueid");

            if (uniqueid == null || "".equalsIgnoreCase(uniqueid)) {//user not logged in
                return "NOT_LOGGED_IN";
            } else { // user logged in
                params.put("senderid", uniqueid);
            }

            params.put("receiverid", listSelectedHis.get(0).getUniqueId());
            params.put("numofsenderitems", listSelectedMine.size() + "");
            params.put("numofreceiveritems", listSelectedHis.size() + "");


            String postsMine = new String();
            for (Post i : listSelectedMine) {
                if (postsMine == null || "".equalsIgnoreCase(postsMine)) {
                    postsMine = i.getPostId();
                } else {
                    postsMine = postsMine + ":" + i.getPostId();
                }

            }
            Log.d("Hello", postsMine);
            params.put("postsmine", postsMine);

            String postsHis = new String();
            for (Post i : listSelectedHis) {
                if (postsHis == null || "".equalsIgnoreCase(postsHis)) {
                    postsHis = i.getPostId();
                } else {
                    postsHis = postsHis + ":" + i.getPostId();
                }
            }

            params.put("postshis", postsHis);

            Log.d("Hello", postsHis);
            json = jsonParser.makeHttpRequest(CommonResources.getURL("submit_offer"),
                    params);
        }else if("delete".equalsIgnoreCase(args[0])){
            params.put("offerid",currentOfferId );
            json = jsonParser.makeHttpRequest(CommonResources.getURL("delete_offer"),
                    params);
        }else if("accept".equalsIgnoreCase(args[0])){
            params.put("offerid",currentOfferId );
            json = jsonParser.makeHttpRequest(CommonResources.getURL("accept_offer"),
                    params);
        }else if("reject".equalsIgnoreCase(args[0])){
            params.put("offerid",currentOfferId );
            json = jsonParser.makeHttpRequest(CommonResources.getURL("reject_offer"),
                    params);
        }else if("counter".equalsIgnoreCase(args[0])) {
            String uniqueid = (String) new CommonResources(getContext()).loadFromSharedPrefs("uniqueid");

            if (uniqueid == null || "".equalsIgnoreCase(uniqueid)) {//user not logged in
                return "NOT_LOGGED_IN";
            } else { // user logged in
                params.put("senderid", uniqueid);
            }

            params.put("receiverid", listSelectedHis.get(0).getUniqueId());
            params.put("numofsenderitems", listSelectedMine.size() + "");
            params.put("numofreceiveritems", listSelectedHis.size() + "");
            params.put("prevofferid", args[1]);


            String postsMine = new String();
            for (Post i : listSelectedMine) {
                if (postsMine == null || "".equalsIgnoreCase(postsMine)) {
                    postsMine = i.getPostId();
                } else {
                    postsMine = postsMine + ":" + i.getPostId();
                }

            }
            Log.d("Hello", postsMine);
            params.put("postsmine", postsMine);

            String postsHis = new String();
            for (Post i : listSelectedHis) {
                if (postsHis == null || "".equalsIgnoreCase(postsHis)) {
                    postsHis = i.getPostId();
                } else {
                    postsHis = postsHis + ":" + i.getPostId();
                }
            }

            params.put("postshis", postsHis);

            Log.d("Hello", postsHis);
            json = jsonParser.makeHttpRequest(CommonResources.getURL("submit_counter_offer"),
                    params);
        }


        // check log cat fro response
        if(json == null){
            //latch.countDown();
            return null;
        }
        Log.d("Fetching Posts", json.toString());

        // check for success tag
        String TAG_SUCCESS = "success";
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 0) {



            } else if(success == 1){
                // failed to create product
            }else{
                //Invalid input
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String isEmpty) {
        // dismiss the dialog once done
        pDialog.dismiss();
        adapter.notifyDataSetChanged();
        if("empty".equalsIgnoreCase(isEmpty))
            Toast.makeText(getContext(),"There are not posts in your city in this sub-category",Toast.LENGTH_LONG).show();
        if("details".equalsIgnoreCase(cameFrom)) {
            getFragmentManager().popBackStack();
            getFragmentManager().popBackStack();
        }else if("offers".equalsIgnoreCase(cameFrom)){
            //navigateToHome();
            navigateToOffers();
        }
    }

    public void navigateToHome() {
        Fragment fragment = null;
        fragment=new HomeFragment();
        if(fragment!=null)
        {
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
        else
        {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void navigateToOffers() {
        Fragment fragment = null;
        fragment=new ChooseOptionsOffersFragment();
        if(fragment!=null)
        {
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        }
        else
        {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

}
