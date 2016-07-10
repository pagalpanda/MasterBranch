package common.barter.com.barterapp.showOffer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import common.barter.com.barterapp.R;
import common.barter.com.barterapp.RecyclerViewLoaderAdapter;
import common.barter.com.barterapp.data.domain.NewOffer;

/**
 * Created by vikram on 23/06/16.
 */
public class NewShowOfferHolderAdapter extends RecyclerViewLoaderAdapter<ShowOfferView> {
    private NewShowOfferHolderUtil showOfferHolderUtil;
    private common.barter.com.barterapp.showOffer.showOfferListener showOfferListener;
    private ShowOfferView viewHolder;
    private Context context;

    public NewShowOfferHolderAdapter(Context context, common.barter.com.barterapp.showOffer.showOfferListener showOfferListener) {
        super(null);
        this.context=context;
        this.showOfferListener=showOfferListener;
    }

    public Context getContext() {
        return context;
    }

    public common.barter.com.barterapp.showOffer.showOfferListener getShowOfferListener() {
        return showOfferListener;
    }

    public NewShowOfferHolderUtil getShowOfferHolderUtil() {
        return showOfferHolderUtil;
    }

    @Override
    public ShowOfferView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.show_offers_header, parent, false);
        setPresenter();
        viewHolder = showOfferHolderUtil.getInitialView(v, viewType);
        return viewHolder;
    }
    private NewShowOfferHolderUtil getPresenter() {
        return showOfferHolderUtil;
    }
    private void setPresenter() {
        if (showOfferHolderUtil==null){
            showOfferHolderUtil = new NewShowOfferHolderUtil();
        }
        showOfferHolderUtil.setAdapter(this);
    }

    @Override
    public void onBindViewHolder(ShowOfferView holder, List<NewOffer> offers) {
        if (offers!=null && holder!=null){
            int totalNumOfPosts=0;
            for (NewOffer offer:offers){
                totalNumOfPosts=offer.getNumOfReceiverPosts()+offer.getNumOfSenderPosts();
                holder.setNumOfImages(totalNumOfPosts);
                this.getPresenter().setImagesButtonArray(totalNumOfPosts);
                this.getPresenter().setTextViews(holder, offer);
                this.getPresenter().setPostImages(holder, offer);
            }
        }
    }

    public void loadData(List<NewOffer> offers) {
        this.onBindViewHolder(viewHolder, offers);
    }
}
