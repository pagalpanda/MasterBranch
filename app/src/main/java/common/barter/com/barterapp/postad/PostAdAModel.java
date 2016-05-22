package common.barter.com.barterapp.postad;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import common.barter.com.barterapp.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.MessagesString;

/**
 * Created by Panda on 17-05-2016.
 */
public class PostAdAModel {
    PostAdNetworkCallsListener listener;
    public PostAdAModel(PostAdNetworkCallsListener listener) {
        this.listener = listener;

    }

    void getListOfLocalitiesForLocation(Context context, String location, HashMap<String, String> params) {

        new AsyncConnection(context, CommonResources.getURL("city_jsons/" + location.replace(" ", "%20") + ".json").replace(".php",""), "POST", params, true, "Fetching Localities") {
            @Override
            public void receiveData(JSONObject json) {
                ArrayList<String> list = new ArrayList<String>();
                try {
                    JSONArray array = json.getJSONArray("locality");
                    for(int i = 0; i<array.length(); i++){
                        list.add(array.getString(i));
                    }
                }catch (Exception e){
                    Log.d("Exception in locality", e.getMessage());
                }

                listener.onLocalitiesFetchSuccess(list);
            }
        }.execute();
    }

    public void createPost(Context context, HashMap<String, String> params, String phpName) {
        AsyncConnection as = new AsyncConnection(context, phpName,"POST",params,true, "Creating Post"){
            public void receiveData(JSONObject json){
                try {
                    int success = json.getInt(MessagesString.TAG_SUCCESS);
                    listener.onAdPostSuccess(success);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        as.execute();
    }
}
