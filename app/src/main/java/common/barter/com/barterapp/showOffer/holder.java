package common.barter.com.barterapp.showOffer;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import common.barter.com.barterapp.R;

/**
 * Created by vikram on 04/06/16.
 */
public class Holder extends RecyclerView.ViewHolder
{
    private  int TYPE_ITEMS; // Added on 07/06/2016
    TextView tvOfferTitle;
    TextView tvOfferDateOffered;
    ImageView ivPrimaryImageOffer1;
    ImageView ivPrimaryImageOffer2;
    ImageView ivPrimaryImageOffer3;
    ImageView ivPrimaryImageOffer4;
    ImageView ivPrimaryImageOffer5;
    ImageView ivPrimaryImageOffer6;
    ImageView ivPrimaryImageOffer7;
    ImageView ivPrimaryImageOffer8;
    ImageView ivPrimaryImageOffer9;
    ImageView ivPrimaryImageOffer10;
    TextView tvExtraPostsCount;
    TextView tvExtraPostsCountMine;
    TextView tvStatus;

    TextView tvOfferTitleFew;
    TextView tvOfferDateOfferedFew;
    ImageButton ivPrimaryImageOfferFew1;
    ImageButton ivPrimaryImageOfferFew2;
    ImageButton ivPrimaryImageOfferFew3;
    ImageButton ivPrimaryImageOfferFew4;
    ImageButton ivPrimaryImageOfferFew5;
    ImageButton ivPrimaryImageOfferFew6;


    TextView tvStatusOfOfferFew;

    int position;
    CardView cardView;

    public Holder(View rowView, int i) {
        super(rowView);


        if(i == TYPE_ITEMS) {
            tvOfferTitle = (TextView) rowView.findViewById(R.id.tvOfferTitle);
            ivPrimaryImageOffer1 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer1);
            ivPrimaryImageOffer2 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer2);
            ivPrimaryImageOffer3 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer3);
            ivPrimaryImageOffer4 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer4);
            ivPrimaryImageOffer5 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer5);
            tvOfferDateOffered = (TextView) rowView.findViewById(R.id.tvOfferDateOffered);
            tvExtraPostsCount = (TextView) rowView.findViewById(R.id.tvExtraPostsCount);
            tvStatus = (TextView) rowView.findViewById(R.id.tvStatusOfOffer);

            cardView = (CardView) rowView.findViewById(R.id.cvOfferItems);

            ivPrimaryImageOffer6 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer6);
            ivPrimaryImageOffer7 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer7);
            ivPrimaryImageOffer8 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer8);
            ivPrimaryImageOffer9 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer9);
            ivPrimaryImageOffer10 = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer10);

            tvExtraPostsCountMine = (TextView) rowView.findViewById(R.id.tvExtraPostsCountMine);

        }else {
            tvOfferTitleFew = (TextView) rowView.findViewById(R.id.tvOfferTitleFew);
            tvOfferDateOfferedFew = (TextView) rowView.findViewById(R.id.tvOfferDateOfferedFew);
            ivPrimaryImageOfferFew1 = (ImageButton) rowView.findViewById(R.id.ivPrimaryImageOfferFew1);
            ivPrimaryImageOfferFew2 = (ImageButton) rowView.findViewById(R.id.ivPrimaryImageOfferFew2);
            ivPrimaryImageOfferFew3 = (ImageButton) rowView.findViewById(R.id.ivPrimaryImageOfferFew3);
            ivPrimaryImageOfferFew4 = (ImageButton) rowView.findViewById(R.id.ivPrimaryImageOfferFew4);
            ivPrimaryImageOfferFew5 = (ImageButton) rowView.findViewById(R.id.ivPrimaryImageOfferFew5);
            ivPrimaryImageOfferFew6 = (ImageButton) rowView.findViewById(R.id.ivPrimaryImageOfferFew6);
            tvStatusOfOfferFew = (TextView) rowView.findViewById(R.id.tvStatusOfOfferFew);
            cardView = (CardView) rowView.findViewById(R.id.cvOfferItemsFew);
        }

    }
}