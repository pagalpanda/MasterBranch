package common.barter.com.barterapp.postad;

import android.content.Context;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.widget.AdapterView;

import org.json.JSONObject;

import java.util.ArrayList;

import common.barter.com.barterapp.Categories;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.DeviceStoreUtil;
import common.barter.com.barterapp.Image;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.posts.NavigationMode;

/**
 * Created by Panda on 21-05-2016.
 */
public class PostAdAPresenter implements ModelCallBackListener<JSONObject> {
    private PostAdAModel model;
    private PhotosGridViewAdapter photosGridViewAdapter;
    private PostAdA postAdA;
    private PostAdListener listener;

    public PostAdListener getListener() {
        if (listener==null){
            listener=new PostAdListener(this);
        }
        return listener;
    }

    public PhotosGridViewAdapter getPhotosGridViewAdapter() {
        if(photosGridViewAdapter==null){
            photosGridViewAdapter = new PhotosGridViewAdapter(getContext(),this.getFragment().getPhotos());
        }
        return photosGridViewAdapter;
    }

    public PostAdAModel getModel() {
        if (model==null){
            model = new PostAdAModel(getContext(),this);
        }
        return model;
    }

    private Context getContext() {
        return this.getFragment().getContext();
    }

    public PostAdA getFragment() {
        return postAdA;
    }

    public void setFragment(PostAdA postAdA) {
        this.postAdA = postAdA;
    }

    public void setDetailsBasedOnMode(String mode) {
        if(isInEditMode(mode)){
            this.getFragment().setDetailsForEditMode();
        }else {
            this.getFragment().setDetailsForPostMode();
        }
    }

    private boolean isInEditMode(String mode) {
        return NavigationMode.EDIT.name().equalsIgnoreCase(mode);
    }

    private boolean isLocationSet(String textOnSelectLocButton) {
        return !MessagesString.LOCATION_SET_MANUALLY.equalsIgnoreCase(textOnSelectLocButton);
    }

    public void getLocalitiesBasedOnLocation(String location) {
        if(!isLocationSet(location)){
            this.getFragment().hideLocalityButton();

        }else {
            getLocalitiesForLocation(location);
        }
    }

    private void getLocalitiesForLocation(String location) {
        this.getModel().getLocalitiesForLocation(location);
        this.getFragment().showLocalityButton();
    }

    public void onCameraIconClick() {
        this.getFragment().showPhotoSelectionOptions();
    }

    public void onRemovePhotoClick(int position) {
        this.getFragment().removePhoto(position);
    }

    public void onLocationSelect(String cityName) {
        DeviceStoreUtil.saveToSharedPrefs(PreferenceManager.getDefaultSharedPreferences(getContext()),MessagesString.LOCATION, cityName);
        GlobalHome.location = cityName;
        this.getFragment().onLocationSelect(cityName);
        this.getLocalitiesBasedOnLocation(cityName);
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
        if(isUserLoggedIn()) {
            if (validateInput(title, description,category, subCategory, city,locality)) {
                NewPost post=this.getModel().createPostObject(title, description, category, subCategory, city, locality, photos, postId);
                this.getModel().createOrEditPost(post,mode);
            } else {
                CommonUtil.flash(getContext(),MessagesString.ALL_FIELDS_ARE_MANDATORY);
            }

        }else {
            CommonUtil.flash(getContext(),MessagesString.PLEASE_LOGIN_TO_CONTINUE);
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
        return "true".equals (DeviceStoreUtil.loadFromSharedPrefs(PreferenceManager.getDefaultSharedPreferences(getContext()), "isLoggedIn") );
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        Image.invalidateUrl(getContext(),this.getFragment().getPhotos().size(),this.getFragment().getPostId());
        CommonUtil.flash(getContext(), "Ad was successfully posted.");
        this.getFragment().navigate();
    }

    @Override
    public void onFailure(String errorMessage) {
        CommonUtil.flash(getContext(),errorMessage);
    }

    public void onSelectCityClicked() {
        this.getFragment().createAndShowLocationDialogForCity();
    }

    public void onLoctionSelected(AdapterView<?> parent, int position) {
        String cityName = (String) parent.getItemAtPosition(position);
        this.onLocationSelect(cityName);
    }

    public void selectOrClickPhoto(int request) {
        this.getFragment().selectOrClickPhoto(request);
    }

    public void onLocalitySelectClicked() {
        this.getFragment().createAndShowLocationDialogForLocality();
    }

    public void onLocalitySelection(AdapterView<?> parent, int position) {
        String localityName = (String) parent.getItemAtPosition(position);
        this.getFragment().onLocalitySelect(localityName);
    }

    public void onPostAdClicked() {
        this.getFragment().onPostAdClicked();
    }

    public void onSubCategorySelected() {
        this.getFragment().createAndShowLocationDialogForSubCategory();
    }

    public void onSubCategoryItemSelected(AdapterView<?> parent, int position) {
        String subCategory = (String) parent.getItemAtPosition(position);
        this.getFragment().onSubCategorySelect(subCategory);
    }

    public void onCategorySelected() {
        this.getFragment().createAndShowLocationDialogForCategory();
    }

    public void onCategoryItemSelected(AdapterView<?> parent, int position) {
        String category =(String) parent.getItemAtPosition(position);
        this.getFragment().onCategorySelect(category);
    }

    public void notifyDataSetChanged() {
        this.getPhotosGridViewAdapter().notifyDataSetChanged();
    }
}
