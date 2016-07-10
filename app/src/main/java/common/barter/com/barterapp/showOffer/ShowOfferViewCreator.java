package common.barter.com.barterapp.showOffer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;

/**
 * Created by vikram on 22/06/16.
 */
public class ShowOfferViewCreator {
    private Context context;
    private final int MAX_NUM_OF_POSTS =10;

    public ShowOfferViewCreator(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public  int getMaxNumOfPosts(){
        return MAX_NUM_OF_POSTS;
    }

    public  RecyclerView getRecyclerView(){
        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setBackgroundColor(CommonUtil.getColor(getContext(), R.color.colorBackGround));
        RecyclerView.LayoutParams imParams = new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.FILL_PARENT,RecyclerView.LayoutParams.FILL_PARENT);
        imParams.setMargins(0, 0, 0, 2);
        recyclerView.setLayoutParams(imParams);
        return recyclerView;
    }

    // To check if different methods need for HIS and MY offers
    public  ShowOfferRelativeLayout getRelativeLayout(View view){
        ShowOfferRelativeLayout relativeLayout = new ShowOfferRelativeLayout(getContext(),view);
        return relativeLayout;
    }

    public  RelativeLayout getRelativeLayoutForHisOffers(){
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams imParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        imParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        imParams.addRule(RelativeLayout.BELOW, R.id.layoutShowOffersHeader);
        relativeLayout.setLayoutParams(imParams);
        return relativeLayout;
    }

    public ImageView getImageViewObject(){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundColor(CommonUtil.getColor(getContext(), R.color.primary_dark_material_light));
        imageView.setImageResource(R.drawable.search);
        RelativeLayout.LayoutParams imParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        imParams.setMargins(2,0,0,0);
        imParams.width=CommonUtil.convertDpToPixel(getContext(),MessagesString.SHOW_OFFER_IMAGEBUTTON_WIDTH);
        imParams.height=CommonUtil.convertDpToPixel(getContext(), MessagesString.SHOW_OFFER_IMAGEBUTTON_HEIGHT);
        imageView.setLayoutParams(imParams);
        return imageView;
    }

    public ImageButton getImageButtonObject(){
        ImageButton imageButton = new ImageButton(getContext());
        imageButton.setBackgroundColor(CommonUtil.getColor(getContext(), R.color.primary_dark_material_light));
        imageButton.setImageResource(R.drawable.search);
        RelativeLayout.LayoutParams imParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        imParams.addRule(RelativeLayout.CENTER_VERTICAL);
        imParams.setMargins(2,0,0,0);
        imParams.width=CommonUtil.convertDpToPixel(getContext(), MessagesString.SHOW_OFFER_IMAGEBUTTON_WIDTH);
        imParams.height=CommonUtil.convertDpToPixel(getContext(), MessagesString.SHOW_OFFER_IMAGEBUTTON_HEIGHT);
        imageButton.setLayoutParams(imParams);
        return imageButton;
    }
}
