package common.barter.com.barterapp;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

import common.barter.com.barterapp.globalhome.GlobalHome;

/**
 * Created by Panda on 21-05-2016.
 */
public class PostAdAPresenter implements NetworkCallListener {
    private AbstractFragment view;


    public void onLoadView(AbstractFragment view) {
        this.view = view;
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
    public void onNetworkCallSuccess(int returnCode) {

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
        new AsyncConnection(view.getContext(), CommonResources.getURL("city_jsons/" + location.replace(" ", "%20") + ".json").replace(".php",""), "POST", param, true, "Fetching Localities") {
            @Override
            public void receiveData(JSONObject json) {
                ArrayList<String> list = new ArrayList<String>();
                try {
                    JSONArray array = json.getJSONArray("locality");

                    for(int i = 0; i<array.length();i++){
                        list.add(array.getString(i));
                    }
                }catch (Exception e){
                    Log.d("Exception in locality", e.getMessage());
                }
                CommonResources.setListOfLocalities(list);
            }
        }.execute();
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
        DeviceStoreUtil.saveToSharedPrefs(PreferenceManager.getDefaultSharedPreferences(view.getContext()),"location",cityName);
        GlobalHome.location = cityName;
        ((PostAdA)view).setTextToLocationButton(cityName);
        ((PostAdA)view).showLocalityButton();

        getListOfLocalitiesForLocation(cityName);


    }
}
