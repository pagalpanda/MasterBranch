package common.barter.com.barterapp.showOffer;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;

/**
 * Created by vikram on 20/06/16.
 */
public class BigHolder extends BaseHolder {

    private static final int TYPE_ITEMS = 1;
    private TextView tvExtraPostsCount;
    private TextView tvExtraPostsCountMine;
    private ImageView[] ivPrimaryImageOffer;
    private CardView cardView;

    public BigHolder(View itemView, int typeItems, int numOfPrimaryImages,View.OnClickListener onClickListener) {
        super(itemView,typeItems,numOfPrimaryImages,onClickListener);
        initializeWidgets(itemView);
        savePrimaryImageArray(itemView);
    }
    private void initializeWidgets(View itemView) {
        cardView = (CardView) itemView.findViewById(R.id.cvOfferItems);
    }

    private void savePrimaryImageArray(View itemView) {
        ivPrimaryImageOffer = new ImageView[this.getNumOfPrimaryImages()];
        for (int i=0;i<this.getNumOfPrimaryImages();i++){
            String fieldName = MessagesString.IV_PRIMARY_IMAGE_OFFER+ String.valueOf(i);
            int id = this.getViewId(fieldName);
            ivPrimaryImageOffer[i]=(ImageView) itemView.findViewById(id);
            ivPrimaryImageOffer[i].setOnClickListener(this.getOnClickListener());
        }
    }
}
