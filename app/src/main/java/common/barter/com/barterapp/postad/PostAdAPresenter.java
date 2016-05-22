package common.barter.com.barterapp.postad;

import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Base64;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import common.barter.com.barterapp.AbstractFragment;
import common.barter.com.barterapp.Categories;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.DeviceStoreUtil;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.globalhome.GlobalHome;

/**
 * Created by Panda on 21-05-2016.
 */
public class PostAdAPresenter implements PostAdNetworkCallsListener {
    private AbstractFragment view;
    private PostAdAModel model;
    private String postIdForEditedPhoto;
    private int numOfPhotos;


    public void onLoadView(AbstractFragment view) {
        this.view = view;
        model = new PostAdAModel(this);
    }


    public void fillDetailsBasedOnMode(String mode) {
        if(isInEditMode(mode)){
            ((PostAdA)view).setDetailsForEditMode();
        }else {
            ((PostAdA)view).setDetailsForPostMode();
        }
    }

    private boolean isInEditMode(String mode) {
        return "edit".equalsIgnoreCase(mode);
    }


    private boolean isLocationSet(String textOnSelectLocButton) {
        return !MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(textOnSelectLocButton);
    }


    @Override
    public void onLocalitiesFetchSuccess(Object listOfLocalities) {
        CommonResources.setListOfLocalities((ArrayList<String>) listOfLocalities);
    }

    @Override
    public void onAdPostSuccess(int success) {
        if (success == 0) {
            PostAdA.isEdited = true;

            for(int i=0; i<numOfPhotos; i++){
                String url = CommonResources.getStaticURL()+"uploadedimages/"+postIdForEditedPhoto+"_"+(i+1);
                Picasso.with(view.getContext()).invalidate(url);
            }

            CommonUtil.flash(view.getContext(), "Ad was successfully posted.");
            //getFragmentManager().popBackStackImmediate();
            ((PostAdA)view).navigate();
            // successfully created product
//                    Toast.makeText(getApplicationContext(),"Created",Toast.LENGTH_LONG).show();
            //finish();
        } else if (success == 1) {
            CommonUtil.flash(view.getContext(), "Please try again");
            // failed to create product
        } else if (success == 2) {
            //Invalid input
            CommonUtil.flash(view.getContext(), "Please try again later");
        }
    }

    @Override
    public void onNetworkCallFailure() {

    }

    public void getLocalitiesBasedOnLocation(String location) {
        if(!isLocationSet(location)){
            ((PostAdA)view).hideLocalityButton();

        }else {
            getLocalitiesForLocation(location);
        }
    }

    private void getLocalitiesForLocation(String location) {
        getListOfLocalitiesForLocation(location);
        ((PostAdA)view).showLocalityButton();


    }

    private void getListOfLocalitiesForLocation(final String location) {
        HashMap param = new HashMap();
        param.put("", "");
        model.getListOfLocalitiesForLocation(view.getContext(), location, param);
    }

    public void onCameraIconClick() {
        ((PostAdA)view).showPhotoSelectionOptions();
    }

    public void onRemovePhotoClick(int position, int numOfPhotos ) {
        ((PostAdA)view).removePhoto(position);
        if(--numOfPhotos == 0){
            ((PostAdA)view).setVisibilityOfRemovePhotosText(false);
        }
    }

    public void onLocationSelect(String cityName) {
        DeviceStoreUtil.saveToSharedPrefs(PreferenceManager.getDefaultSharedPreferences(view.getContext()), "location", cityName);
        GlobalHome.location = cityName;
        ((PostAdA)view).setTextToLocationButton(cityName);
        ((PostAdA)view).showLocalityButton();

        getListOfLocalitiesForLocation(cityName);


    }

    public ArrayList getListOfCategories() {
        ArrayList<String> listOfCategories = new ArrayList<>();
        for(Categories c : CommonResources.categories){
            listOfCategories.add(c.getName());
        }

        return listOfCategories;
    }

    public ArrayList<String> getSubCategories(String selectedCategory) {
        return  CommonResources.getSubCategories(selectedCategory);
    }

    public void onPostAdClick(String title, String description, String category, String subCategory, String city, String locality, ArrayList<Bitmap> photos, String postId, String mode) {
        this.postIdForEditedPhoto = postId;
        this.numOfPhotos = photos.size();
        if(isUserLoggedIn()) {
            if (validateInput(title, description,category, subCategory, city,locality)) {
                createPost(title, description, category, subCategory, city, locality, photos, postId, mode);
            } else {
                CommonUtil.flash(view.getContext(),"All fields are mandatory");
            }

        }else {
            CommonUtil.flash(view.getContext(),"Please Log in to Continue");
        }
    }

    private boolean validateInput(String title, String description, String category, String subCategory, String city, String locality) {

        if( "".equals(title) || "".equals(description) || MessagesString.HINT_SUBCATEGORY.equalsIgnoreCase(subCategory)
                || MessagesString.HINT_SUBCATEGORY.equalsIgnoreCase(subCategory) || MessagesString.HINT_CITY.equalsIgnoreCase(city)
                || MessagesString.HINT_LOCALITY.equalsIgnoreCase(locality)){
            return false;
        }
        return true;
    }

    private boolean isUserLoggedIn() {
        return "true".equals (DeviceStoreUtil.loadFromSharedPrefs(PreferenceManager.getDefaultSharedPreferences(view.getContext()), "isLoggedIn") );
    }

    private void createPost(String title, String description, String category, String subCategory, String city, String locality, final ArrayList<Bitmap> photos, final String postId, String mode) {
        HashMap <String,String> params = new HashMap <String,String>();

        params.put("title", title);
        params.put("description", description);
        params.put("category", category);
        params.put("subcategory", subCategory);
        params.put("city", city);
        params.put("locality", locality);
        params.put("uniqueid", (String)(DeviceStoreUtil.loadFromSharedPrefs(PreferenceManager.getDefaultSharedPreferences(view.getContext()), "uniqueid")));


        params.put("numofimages", photos.size() + "");


        ByteArrayOutputStream byteArrayBitmapStream;
        int i = 0;
        for (Bitmap image : photos) {
            byteArrayBitmapStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
            byte[] b = byteArrayBitmapStream.toByteArray();
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            params.put("image_" + i, encodedImage);
            i++;
            try {
                byteArrayBitmapStream.close();
                byteArrayBitmapStream = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();


            }
        }
        String phpName = null;
        if (isInEditMode(mode)) {
            params.put("postid",postId);
            phpName = CommonResources.getURL("edit_my_post");
        } else{
            phpName = CommonResources.getURL("create_post");
        }
        model.createPost(view.getContext(), params, phpName);
    }



}
