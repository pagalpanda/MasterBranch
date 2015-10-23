package common.barter.com.barterapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by amitpa on 8/31/2015.
 */
public class Post {

    private String title;
    private String createdDate;
    private String locality;
    private String image;
    private String hasImage;
    private String postId;
    private int numOfImages;
    private String description;
    private String subCategory;
    private String category;
    private String city;

    public boolean isAddedToWishList() {
        return isAddedToWishList;
    }

    public void setIsAddedToWishList(boolean isAddedToWishList) {
        this.isAddedToWishList = isAddedToWishList;
    }

    private boolean isAddedToWishList;

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    private boolean isSelected;
    public String getUniqueId() {
        return uniqueId;
    }

    private String uniqueId;
    CountDownLatch latch;

    public boolean isEdited() {
        return isEdited;
    }

    public void setIsEdited(boolean isEdited) {
        this.isEdited = isEdited;
    }

    private boolean isEdited;

    JSONParser jsonParser = new JSONParser();

    public String getPostId() {
        return postId;
    }



    public String getSubCategory() {
        return subCategory;
    }
    public String getCity() {
        return city;
    }
    public String getCategory() {
        return category;
    }
    public String getHasImage() {
        return hasImage;
    }
    public String getTitle() {
        return title;
    }
    public int getNumOfImages() {
        return numOfImages;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLocality() {
        return locality;
    }
    public String getDescription() {
        return description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Post(String uniqueId, String title, String createdDate, String locality, String hasImage, String postId, String numOfImages, String description, String subCategory, String category, String city, boolean isAddedToWishList) {
        this.title = title;
        this.createdDate = createdDate;
        this.locality = locality;
        //this.image = image;
        this.hasImage = hasImage;
        this.postId = postId;
        this.numOfImages = Integer.parseInt(numOfImages);
        this.description=description;
        this.category=category;
        this.subCategory=subCategory;
        this.city = city;
        this.uniqueId=uniqueId;
        this.isAddedToWishList = isAddedToWishList;
    }


    public Bitmap getImage() {
        latch = new CountDownLatch(1);
        downloadImage();
        try {
            latch.await();
        }catch (Exception e){

        }

        //needs to wait
        if("null".equalsIgnoreCase(image)){
            return null;
        }else{
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //image.setImageBitmap(decodedByte);
            return decodedByte;
        }

    }


    public void downloadImage() {
        new GetPrimaryImages().execute();
    }

    class GetPrimaryImages extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            HashMap<String,String> params = new HashMap <String,String>();
            params.put("postid",getPostId() );


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(CommonResources.getURL("get_primary_image"),
                    "POST", params);

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
                     setImage(json.getString("primaryimage"));

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
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            //notify
            //adapter.notifyDataSetChanged();
            latch.countDown();
        }

    }



}
