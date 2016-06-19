package common.barter.com.barterapp.showOffer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.Post;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.ReviewFragment;

/**
 * Created by vikram on 04/06/16.
 */
class GetUserPosts extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
//        super.onPreExecute();
//        ProgressDialog pDialog = new ProgressDialog(getContext());
//        pDialog.setMessage("Fetching Posts..");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(true);
//        pDialog.show();
    }

    /**
     * Creating product
     * */
    protected String doInBackground(String... args) {
//
//        // Building Parameters
//        HashMap<String,String> params = new HashMap <String,String>();
//        String uniqueid = (String)new CommonResources(getContext()).loadFromSharedPrefs("uniqueid");
//        String hisUniqueId = args[0];
//        JSONObject json = new JSONObject();
//
//
//        params.put("myuniqueid", uniqueid);
//        params.put("hisuniqueid", hisUniqueId);
//        json = jsonParser.makeHttpRequest(CommonResources.getURL("get_user_posts_for_offers"),
//                params);
//
//
//
//
//        // check log cat fro response
//        if(json == null){
//            //latch.countDown();
//            return null;
//        }
//        Log.d("Fetching Posts", json.toString());
//
//        // check for success tag
//        String TAG_SUCCESS = "success";
//        try {
//            int success = json.getInt(TAG_SUCCESS);
//
//            if (success == 0) {
//                myListOfPostsInOffer = new ArrayList<>();
//                hisListOfPostsInOffer = new ArrayList<>();
//                myposts = json.getJSONArray("myposts");
//                if(myposts.length() == 0) return "empty";
//                for (int i = 0; i < myposts.length(); i++) {
//                    JSONObject c = myposts.getJSONObject(i);
//                    myListOfPostsInOffer.add(new Post(c.getString("uniqueid"), c.getString("title"), c.getString("createddate"), c.getString("locality"), c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city"), "null".equalsIgnoreCase(c.getString("isaddedtowishlist"))?false:true));
//                }
//
//                hisposts = json.getJSONArray("hisposts");
//                if(hisposts.length() == 0) return "empty";
//                for (int i = 0; i < hisposts.length(); i++) {
//                    JSONObject c = hisposts.getJSONObject(i);
//                    hisListOfPostsInOffer.add(new Post(c.getString("uniqueid"), c.getString("title"), c.getString("createddate"), c.getString("locality"), c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city"), "null".equalsIgnoreCase(c.getString("isaddedtowishlist"))?false:true));
//                }
//            } else if(success == 1){
//                // failed to create product
//            }else{
//                //Invalid input
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
        return null;

    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String isEmpty) {
//        // dismiss the dialog once done
//        pDialog.dismiss();
//
//        if("empty".equalsIgnoreCase(isEmpty))
//            Toast.makeText(getContext(), "There are not posts in your city in this sub-category", Toast.LENGTH_LONG).show();
//        navigateToReview();
//
    }

    private void navigateToReview() {

//        ArrayList<Post> listSelectedMine = new ArrayList<>();
//        ArrayList<Post> listSelectedHis = new ArrayList<>();
//        for(Post post : myListOfPostsInOffer){
//
//            if( (listOfOffers.get(selectedPosition).getMySelectedPosts()).contains(post.getPostId()) ) {
//                post.setIsSelected(true);
//                listSelectedMine.add(post);
//            }
//        }
//        for(Post post : hisListOfPostsInOffer){
//            if( (listOfOffers.get(selectedPosition).getHisSelectedPosts() ).contains(post.getPostId()) ) {
//                post.setIsSelected(true);
//                listSelectedHis.add(post);
//            }
//        }
//
//
//        Fragment fragment = null;
//        fragment = new ReviewFragment(listSelectedMine,listSelectedHis,"view",myListOfPostsInOffer,hisListOfPostsInOffer, status, dateUpdated, currentOfferId);
//
//
//        if (fragment != null) {
//            getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
//        } else {
//            Log.e("MainActivity", "Error in creating fragment");
//        }
//
//
    }

}
