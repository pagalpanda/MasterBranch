package common.barter.com.barterapp.showOffer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import java.util.List;

import common.barter.com.barterapp.HttpConnectionParameters;
import common.barter.com.barterapp.data.domain.NewOffer;

/**
 * Created by vikram on 23/06/16.
 */
public class ShowOfferLoaderManager implements LoaderManager.LoaderCallbacks<List<NewOffer>> {
    @NonNull
    private Context context;
    @NonNull
    private HttpConnectionParameters connectionParameters;
    private NewShowOfferHolderAdapter showOfferHolderAdapter;

    public ShowOfferLoaderManager(Context context,HttpConnectionParameters connectionParameters, NewShowOfferHolderAdapter showOfferHolderAdapter){
        this.context = context;
        this.connectionParameters = connectionParameters;
        this.showOfferHolderAdapter = showOfferHolderAdapter;
    }

    @Override
    public Loader<List<NewOffer>> onCreateLoader(int i, Bundle bundle) {
        return new ShowOfferLoader(context,connectionParameters);
    }

    @Override
    public void onLoadFinished(Loader<List<NewOffer>> loader , List<NewOffer> data) {
        showOfferHolderAdapter.swapLoader(data);
        showOfferHolderAdapter.notifyDataSetChanged();
        showOfferHolderAdapter.loadData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<NewOffer>> loader) {
        showOfferHolderAdapter.notifyDataSetChanged();
    }
}
