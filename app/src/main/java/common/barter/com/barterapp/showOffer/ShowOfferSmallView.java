package common.barter.com.barterapp.showOffer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * Created by vikram on 23/06/16.
 */
public class ShowOfferSmallView   extends ShowOfferView{


    public ShowOfferSmallView(@NonNull View view,int viewType,@NonNull ShowOfferRelativeLayout showOfferRelativeLayout, ImageButton[] ibPrimaryImageOffer) {
        super(view, showOfferRelativeLayout, viewType, ibPrimaryImageOffer);
    }
}
