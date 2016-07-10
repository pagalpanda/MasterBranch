package common.barter.com.barterapp.showOffer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;

import common.barter.com.barterapp.R;

/**
 * Created by vikram on 29/06/16.
 */
public class ShowOfferRelativeLayout extends RelativeLayout {
    private CardView cardView;
    private TextView tvOfferTitle;
    private TextView tvOfferDateOffered;
    private TextView tvStatus;
    TextView tvExtraPostsCountMine;
    public ShowOfferRelativeLayout(Context context,View view) {
        super(context);
        initializeWidgets(view);
    }

    private void initializeWidgets(View view) {
        tvOfferTitle = (TextView) view.findViewById(R.id.tvOfferTitle);
        tvOfferDateOffered = (TextView) view.findViewById(R.id.tvOfferDateOffered);
        tvStatus = (TextView) view.findViewById(R.id.tvStatusOfOffer);
        cardView = (CardView) view.findViewById(R.id.cvOfferItems);
        tvExtraPostsCountMine = (TextView) view.findViewById(R.id.tvExtraPostsCountMine);
    }

    public TextView getTvOfferTitle() {
        return tvOfferTitle;
    }
    public TextView getTvOfferDateOffered() {
        return tvOfferDateOffered;
    }
    public TextView getTvStatus() {
        return tvStatus;
    }
    public CardView getCardView() {
        return cardView;
    }
    public void setTextOnTvOfferTitle(String text) {
        this.tvOfferTitle.setText(text);
    }

    public void setTextOnTvOfferDateOffered(String text) {
        this.tvOfferDateOffered.setText(text);
    }

    public void setTextOnTvStatus(String text) {
        this.tvStatus.setText(text);
    }

    public void setTextColorOnTvStatus(int color) {
        this.tvStatus.setTextColor(color);
    }


    public void bindTvOfferTitle(TextView tvOfferTitle) {
        this.tvOfferTitle = tvOfferTitle;
    }
    public void bindTvOfferDateOffered(TextView tvOfferDateOffered) {
        this.tvOfferDateOffered = tvOfferDateOffered;
    }
    public void bindTvStatus(TextView tvStatus) {
        this.tvStatus = tvStatus;
    }
    public void bindCardView(CardView cardView) {
        this.cardView = cardView;
    }

}
