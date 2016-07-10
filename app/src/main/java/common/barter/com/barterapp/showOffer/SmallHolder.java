package common.barter.com.barterapp.showOffer;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;

/**
 * Created by vikram on 21/06/16.
 */
public class SmallHolder  extends BaseHolder {
    private ImageButton[] ibPrimaryImageOffer;
    private CardView cardView;

    public SmallHolder(View itemView, int typeItems, int numOfPrimaryImages,View.OnClickListener onClickListener) {
        super(itemView, typeItems, numOfPrimaryImages,onClickListener);
        initializeWidgets(itemView);
        savePrimaryImageArray(itemView);
    }

    private void initializeWidgets(View itemView) {
        cardView = (CardView) itemView.findViewById(R.id.cvOfferItemsFew);
    }
    private void savePrimaryImageArray(View itemView) {
        ibPrimaryImageOffer = new ImageButton[this.getNumOfPrimaryImages()];
        for (int i=0;i<this.getNumOfPrimaryImages();i++){
            String fieldName = MessagesString.IB_PRIMARY_IMAGE_OFFER+ String.valueOf(i);
            int id = this.getViewId(fieldName);
            ibPrimaryImageOffer[i]=(ImageButton) itemView.findViewById(id);
            ibPrimaryImageOffer[i].setOnClickListener(this.getOnClickListener());
        }
    }
}
