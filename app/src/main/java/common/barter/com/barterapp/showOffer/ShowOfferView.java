package common.barter.com.barterapp.showOffer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by vikram on 23/06/16.
 */
public abstract class ShowOfferView extends RecyclerView.ViewHolder{
    @NonNull
    private ShowOfferRelativeLayout showOfferRelativeLayout;
    private int viewType;
    private int numOfImages = 10;
    private ImageButton[] ibPrimaryImageOffer;

    public ShowOfferView(View view, ShowOfferRelativeLayout showOfferRelativeLayout, int viewType, ImageButton[] ibPrimaryImageOffer) {
        super(view);
        this.showOfferRelativeLayout = showOfferRelativeLayout;
        this.viewType=viewType;
        this.ibPrimaryImageOffer=ibPrimaryImageOffer;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public ShowOfferRelativeLayout getShowOfferRelativeLayout() {
        return showOfferRelativeLayout;
    }

    public void setShowOfferRelativeLayout(ShowOfferRelativeLayout showOfferRelativeLayout) {
        this.showOfferRelativeLayout = showOfferRelativeLayout;
    }
    public ImageButton[] getIbPrimaryImageOffer() {
        return ibPrimaryImageOffer;
    }

    public void setIbPrimaryImageOffer(ImageButton[] ibPrimaryImageOffer) {
        this.ibPrimaryImageOffer = ibPrimaryImageOffer;
    }
    public int getNumOfImages() {
        return numOfImages;
    }

    public void setNumOfImages(int numOfImages) {
        this.numOfImages = numOfImages;
    }

}
