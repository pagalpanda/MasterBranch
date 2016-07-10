package common.barter.com.barterapp.showOffer;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by vikram on 23/06/16.
 */
public class ShowOfferBigView  extends ShowOfferView{
    private ImageView[] ivPrimaryImageOffer;

    public ShowOfferBigView(RecyclerView recyclerView,int viewType,ShowOfferRelativeLayout relativeLayout, ImageView[] ivPrimaryImageOffer) {
        super(recyclerView, relativeLayout,viewType, null);
        this.ivPrimaryImageOffer = ivPrimaryImageOffer;
    }

    public ImageView[] getIvPrimaryImageOffer() {

        return ivPrimaryImageOffer;
    }

    public void setIvPrimaryImageOffer(ImageView[] ivPrimaryImageOffer) {
        this.ivPrimaryImageOffer = ivPrimaryImageOffer;
    }

}
