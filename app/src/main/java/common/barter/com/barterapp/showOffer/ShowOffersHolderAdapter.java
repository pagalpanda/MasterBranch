package common.barter.com.barterapp.showOffer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.Image;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.Offer;
import common.barter.com.barterapp.data.constants.OfferStatus;
import common.barter.com.barterapp.R;

public class ShowOffersHolderAdapter extends RecyclerView.Adapter<common.barter.com.barterapp.showOffer.holder>{
    private Context context;
    private String mode;
    private common.barter.com.barterapp.showOffer.holder holder;
    private ArrayList<Offer> listOfOffers;
    private static final int TYPE_FEW_ITEMS = 0;
    private static final int TYPE_ITEMS = 1;
    private ShowOffersHolderPresenter showOffersHolderPresenter;

    private static LayoutInflater inflater=null;

    public ShowOffersHolderAdapter(Context context, ArrayList<Offer> listOfOffers, String mode, ShowOffersHolderPresenter showOffersHolderPresenter) {
        this.context=context;
        this.listOfOffers = listOfOffers;
        this.mode = mode;
        this.showOffersHolderPresenter = showOffersHolderPresenter;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if ((listOfOffers.get(position)).getMySelectedPosts().size() + (listOfOffers.get(position)).getHisSelectedPosts().size() > 4)
            return TYPE_ITEMS;
        return TYPE_FEW_ITEMS;
    }

    @Override
    public common.barter.com.barterapp.showOffer.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(viewType == TYPE_ITEMS){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list__item_offers, parent, false);
        }else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list__item_offers_few_items, parent, false);
        }
        common.barter.com.barterapp.showOffer.holder viewHolder = new holder(v,viewType);

        return viewHolder;

    }

    public ShowOffersHolderPresenter getShowOffersHolderPresenter() {
        return showOffersHolderPresenter;
    }

    private ShowOfferHolderListener getListener() {
        return this.getShowOffersHolderPresenter().getShowOfferHolderListener();
    }

    @Override
    public void onBindViewHolder(common.barter.com.barterapp.showOffer.holder holder,final int position) {
        this.holder = holder;
        int numOfMyPosts = (listOfOffers.get(position)).getMySelectedPosts().size();
        int numOfHisPosts = (listOfOffers.get(position)).getHisSelectedPosts().size();
        holder.cardView.setOnClickListener(this.getListener().getHoldercardViewOnClickListener(position));

        if(numOfHisPosts+numOfMyPosts >4) {
            setLayoutForNumOfPostsGreaterThanFour(holder, position, numOfMyPosts, numOfHisPosts);
        }else{
            setLayoutForNumOfPostsLessThanFour(holder, position, numOfMyPosts, numOfHisPosts);
        }
    }

    private void setLayoutForNumOfPostsLessThanFour(common.barter.com.barterapp.showOffer.holder holder, int position, int numOfMyPosts, int numOfHisPosts) {
        this.setOfferTitleFew(holder, position);
        this.setOfferDateFew(holder, position);
        this.setOfferStatusFew(holder, position);
        this.setRedundantImagesInvisibleFew(holder);

        this.setHisPrimaryImageFew(holder, position);
        this.setMyPrimaryImageFew(holder, position);


        if (numOfHisPosts > 1) {
            this.addImageFew(position, holder.ivPrimaryImageOfferFew2, 0, 1);
        } else {
            holder.ivPrimaryImageOfferFew3.setVisibility(View.INVISIBLE);

        }
        if (numOfHisPosts > 2) {
            this.addImageFew(position, holder.ivPrimaryImageOfferFew3, 0, 2);
        }

        if (numOfMyPosts > 1) {
            this.addImageFew(position, holder.ivPrimaryImageOfferFew5, 1, 1);
        } else {
            holder.ivPrimaryImageOfferFew6.setVisibility(View.INVISIBLE);

        }
        if (numOfMyPosts > 2) {
            this.addImageFew(position, holder.ivPrimaryImageOfferFew6, 1, 2);
        }
    }

    private void setMyPrimaryImageFew(common.barter.com.barterapp.showOffer.holder holder, int position) {
        final String postId6 = (listOfOffers.get(position)).getMySelectedPosts().get(0);
        String urlMine = CommonResources.getStaticURL() + "uploadedimages/" + postId6 + "_1";

        Picasso.with(context).load(urlMine).fit().into(holder.ivPrimaryImageOfferFew4);
        holder.ivPrimaryImageOfferFew4.setOnClickListener(this.getListener().getPrimaryOfferImageOnClickListener(postId6));
    }

    private void setHisPrimaryImageFew(common.barter.com.barterapp.showOffer.holder holder, int position) {
        final String postId1 = (listOfOffers.get(position)).getHisSelectedPosts().get(0);
        String url = CommonResources.getStaticURL() + "uploadedimages/" + postId1 + "_1";

        Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOfferFew1);
        holder.ivPrimaryImageOfferFew1.setOnClickListener(this.getListener().getPrimaryOfferImageOnClickListener(postId1));
    }

    private void setOfferDateFew(common.barter.com.barterapp.showOffer.holder holder, int position) {
        holder.tvOfferDateOfferedFew.setText(CommonResources.convertDate(listOfOffers.get(position).getDateOffered()));
    }

    private void setOfferTitleFew(common.barter.com.barterapp.showOffer.holder holder, int position) {
        if(MessagesString.MY_OFFERS.equalsIgnoreCase(mode)) {
            holder.tvOfferTitleFew.setText(MessagesString.OFFER_TO + " "+ listOfOffers.get(position).getUserNameHis());
        }else{
            holder.tvOfferTitleFew.setText(MessagesString.OFFER_FROM+" "+listOfOffers.get(position).getUserNameHis());
        }
    }

    private void setRedundantImagesInvisibleFew(common.barter.com.barterapp.showOffer.holder holder) {
        holder.ivPrimaryImageOfferFew2.setVisibility(View.INVISIBLE);
        holder.ivPrimaryImageOfferFew3.setVisibility(View.INVISIBLE);
        holder.ivPrimaryImageOfferFew5.setVisibility(View.INVISIBLE);
        holder.ivPrimaryImageOfferFew6.setVisibility(View.INVISIBLE);
    }

    private void setOfferStatusFew(common.barter.com.barterapp.showOffer.holder holder, int position) {
        OfferStatus offerStatus = OfferStatus.getEnum(listOfOffers.get(position).getStatus());
        holder.tvStatusOfOfferFew.setText(offerStatus.getName());
        holder.tvStatusOfOfferFew.setTextColor(offerStatus.getColor());
    }

    private void addImageFew(int position, ImageButton ivPrimaryImageOffer,int hisOrMine,int id){
        String postId;
        ivPrimaryImageOffer.setVisibility(View.VISIBLE);
        Image image2 = new Image();
        image2.setImg(ivPrimaryImageOffer);
        holder.position = position;
        if(hisOrMine==0){
            postId = (listOfOffers.get(position)).getHisSelectedPosts().get(id);
        }
        else{
            postId = (listOfOffers.get(position)).getMySelectedPosts().get(id);
        }
        String url = CommonResources.getStaticURL() + "uploadedimages/" + postId + "_1";
        Picasso.with(context).load(url).fit().into(ivPrimaryImageOffer);
        ivPrimaryImageOffer.setOnClickListener(this.getListener().getPrimaryOfferImageOnClickListener(postId));
    }

    private void setOfferStatus(common.barter.com.barterapp.showOffer.holder holder, int position) {
        OfferStatus offerStatus = OfferStatus.getEnum(listOfOffers.get(position).getStatus());
        holder.tvStatus.setText(offerStatus.getName());
        holder.tvStatus.setTextColor(offerStatus.getColor());
    }
    private void setMyPrimaryImage(common.barter.com.barterapp.showOffer.holder holder, int position) {
        final String postId6 = (listOfOffers.get(position)).getMySelectedPosts().get(0);
        String urlMine = CommonResources.getStaticURL() + "uploadedimages/" + postId6 + "_1";

        Picasso.with(context).load(urlMine).fit().into(holder.ivPrimaryImageOffer6);
        holder.ivPrimaryImageOffer6.setOnClickListener(this.getListener().getPrimaryOfferImageOnClickListener(postId6));
    }

    private void setHisPrimaryImage(common.barter.com.barterapp.showOffer.holder holder, int position) {
        final String postId1 = (listOfOffers.get(position)).getHisSelectedPosts().get(0);
        String url = CommonResources.getStaticURL() + "uploadedimages/" + postId1 + "_1";

        Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer1);
        holder.ivPrimaryImageOffer1.setOnClickListener(this.getListener().getPrimaryOfferImageOnClickListener(postId1));
    }
    private void setOfferDate(common.barter.com.barterapp.showOffer.holder holder, int position) {
        holder.tvOfferDateOffered.setText(CommonResources.convertDate(listOfOffers.get(position).getDateOffered()));
    }

    private void addImage(int position, ImageView ivPrimaryImageOffer,int hisOrMine,int id){
        String postId;
        ivPrimaryImageOffer.setVisibility(View.VISIBLE);
        Image image2 = new Image();
        image2.setImg(ivPrimaryImageOffer);
        holder.position = position;
        if(hisOrMine==0){
            postId = (listOfOffers.get(position)).getHisSelectedPosts().get(id);
        }
        else{
            postId = (listOfOffers.get(position)).getMySelectedPosts().get(id);
        }
        String url = CommonResources.getStaticURL() + "uploadedimages/" + postId + "_1";
        Picasso.with(context).load(url).fit().into(ivPrimaryImageOffer);
        ivPrimaryImageOffer.setOnClickListener(this.getListener().getPrimaryOfferImageOnClickListener(postId));
    }

    private void setLayoutForNumOfPostsGreaterThanFour(common.barter.com.barterapp.showOffer.holder holder, int position, int numOfMyPosts, int numOfHisPosts) {
        this.setOfferDate(holder, position);
        this.setOfferTitle(holder, position);
        this.setOfferDate(holder, position);
        this.setOfferStatus(holder, position);

        Image image = new Image();
        image.setImg(holder.ivPrimaryImageOffer1);
        holder.position = position;

        this.setHisPrimaryImage(holder, position);
        this.setMyPrimaryImage(holder, position);


        if (numOfHisPosts > 1) {
            this.addImage(position, holder.ivPrimaryImageOffer2, 0, 1);
        } else {
            holder.ivPrimaryImageOffer2.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer3.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer4.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
        }
        if (numOfHisPosts > 2) {
            this.addImage(position,holder.ivPrimaryImageOffer3,0,2);
        } else {
            holder.ivPrimaryImageOffer3.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer4.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
        }

        if (numOfHisPosts > 3) {
            this.addImage(position, holder.ivPrimaryImageOffer4, 0, 3);
        } else {
            holder.ivPrimaryImageOffer4.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
        }
        if (numOfHisPosts > 4) {
            this.addImage(position, holder.ivPrimaryImageOffer5, 0, 4);
        } else {
            holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
        }
        if (holder.position == position) {
            if (numOfHisPosts > 5) {
                //Remaining images couldn't be shown: numOfHisPosts-3
                holder.tvExtraPostsCount.setVisibility(View.VISIBLE);
                holder.tvExtraPostsCount.setText("+" + (numOfHisPosts - 5));
            } else {
                holder.tvExtraPostsCount.setVisibility(View.INVISIBLE);
            }
        }


        if (numOfMyPosts > 1) {
            this.addImage(position, holder.ivPrimaryImageOffer7, 1, 1);
        } else {
            holder.ivPrimaryImageOffer7.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer8.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer9.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
        }
        if (numOfMyPosts > 2) {
            this.addImage(position, holder.ivPrimaryImageOffer8, 1, 2);
        } else {
            holder.ivPrimaryImageOffer8.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer9.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
        }

        if (numOfMyPosts > 3) {
            this.addImage(position, holder.ivPrimaryImageOffer9, 1, 3);
        } else {
            holder.ivPrimaryImageOffer9.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
        }
        if (numOfMyPosts > 4) {
            this.addImage(position, holder.ivPrimaryImageOffer10,1,4);
        } else {

            holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
        }
        if (holder.position == position) {
            if (numOfMyPosts > 5) {
                //Remaining images couldn't be shown: numOfHisPosts-3
                holder.tvExtraPostsCountMine.setVisibility(View.VISIBLE);
                holder.tvExtraPostsCountMine.setText("+" + (numOfMyPosts - 5));
            } else {
                holder.tvExtraPostsCountMine.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setOfferTitle(common.barter.com.barterapp.showOffer.holder holder, int position) {
        if(MessagesString.MY_OFFERS.equalsIgnoreCase(mode)) {
            holder.tvOfferTitle.setText("Offer to "+listOfOffers.get(position).getUserNameHis());
        }else{
            holder.tvOfferTitle.setText("Offer from "+listOfOffers.get(position).getUserNameHis());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listOfOffers.size();
    }
}