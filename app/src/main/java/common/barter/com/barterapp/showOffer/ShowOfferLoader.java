package common.barter.com.barterapp.showOffer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.HttpConnectionParameters;
import common.barter.com.barterapp.JSONParser;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.data.domain.NewOffer;
import common.barter.com.barterapp.data.domain.OfferPost;

/**
 * Created by vikram on 23/06/16.
 */
public class ShowOfferLoader  extends AsyncTaskLoader<List<NewOffer>> {
    private List<NewOffer> offers;
    @NonNull
    private HttpConnectionParameters connectionParameters;

    public ShowOfferLoader(Context context, HttpConnectionParameters connectionParameters) {
        super(context);
        this.connectionParameters = connectionParameters;
    }

    @Override
    public List<NewOffer> loadInBackground() {
        HttpURLConnection conn;JSONObject json;

        JSONParser jsonParser= new JSONParser();
        try{
            conn = jsonParser.getHttpConnectionFromUrl(connectionParameters.getUrl(), connectionParameters.getMethod());
            json = jsonParser.makeHttpRequest(conn,
                    connectionParameters.getParams());
        } catch (IOException e) {
            json=null;
            e.printStackTrace();
        }

        if(null == json){
            return null;
        }
        offers = processJsonAndGetOfferList(json);
        return offers;
    }

    private List<NewOffer> processJsonAndGetOfferList(@NonNull JSONObject json) {
        List<NewOffer> offerList = null;
        try{
            int success = json.getInt(MessagesString.TAG_SUCCESS);
            if (success == 0) {
                offerList= getOfferListFromJson(json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return offerList;
    }

    private List<NewOffer> getOfferListFromJson(@NonNull JSONObject json) {
        List<NewOffer> offerList = new ArrayList<>();
        try {
            JSONArray offersArray = json.getJSONArray(MessagesString.OFFERS);
            for (int i=0; i<offersArray.length(); i++) {
                JSONObject jsonOffer = offersArray.getJSONObject(i);
                offerList.add(NewOffer.getOfferFromJson(jsonOffer));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            offerList=null;
        }
        return offerList;
    }

    @Override
    public void deliverResult(List<NewOffer> offers) {
        if (isReset()) {
            releaseResources(offers);
            return;
        }
        List<NewOffer> oldOffers = offers;
        this.offers = offers;
        if (isStarted()) {
            super.deliverResult(offers);
        }
        if (oldOffers != null && oldOffers != offers) {
            releaseResources(oldOffers);
        }
    }

    @Override
    protected void onStartLoading() {
        if (offers != null) {
            deliverResult(offers);
        }
        if (takeContentChanged() || offers == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if (offers != null) {
            releaseResources(offers);
            offers = null;
        }
    }

    @Override
    public void onCanceled(List<NewOffer> offers) {
        super.onCanceled(offers);
        releaseResources(offers);
    }

    private void releaseResources(List<NewOffer> offers) {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
    }

}
