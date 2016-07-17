package common.barter.com.barterapp.asyncConnections;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.JSONParser;
import common.barter.com.barterapp.MessagesString;

/**
 * Created by vikramb on 16/07/16.
 */
public abstract class LocalitiesAsyncConnection  extends AsyncTask<String, String, JSONObject> {
    private Context context;
    private String location;
    private JSONParser jsonParser;
    private HttpURLConnection conn;
    private JSONObject json;

    public LocalitiesAsyncConnection(Context context, String location) {
        this.context = context;
        this.location = location;
    }

    public abstract void populateLocalities(ArrayList<String> json);

    @Override
    protected JSONObject doInBackground(String... urls) {
        try{
            String url = CommonResources.getLocalitiesURL(location);
            conn = jsonParser.getHttpConnectionFromUrl(url, MessagesString.POST);
            json = jsonParser.makeHttpRequest(conn, (HashMap<String, String>) Collections.EMPTY_MAP);
        } catch (IOException e) {
            json=null;
            e.printStackTrace();
        }

        if(null != json)
            Log.d("Create Response", json.toString());

        return null;

    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        if(json == null){
            CommonUtil.flash(context, MessagesString.CHECK_NETWORK_CONNECTIVITY);
            return;
        }
        ArrayList<String> list=getListFromJson(json);
        populateLocalities(list);
    }

    private ArrayList<String> getListFromJson(JSONObject json) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            JSONArray array = json.getJSONArray(MessagesString.LOCALITY);
            for(int i = 0; i<array.length(); i++){
                list.add(array.getString(i));
            }
        }catch (Exception e){
            Log.d("Exception in locality", e.getMessage());
        }
        return list;
    }
}

