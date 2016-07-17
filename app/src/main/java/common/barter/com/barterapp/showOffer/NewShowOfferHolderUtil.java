package common.barter.com.barterapp.showOffer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import java.util.ArrayList;
import java.util.List;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.Image;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.Offer;
import common.barter.com.barterapp.data.constants.OfferStatus;
import common.barter.com.barterapp.data.domain.NewOffer;
import common.barter.com.barterapp.data.domain.OfferPost;

/**
 * Created by vikram on 25/06/16.
 */
public class NewShowOfferHolderUtil {
    private NewShowOfferHolderAdapter showOfferHolderAdapter;
    private ShowOfferViewFactory showOfferViewFactory;
    private ShowOfferViewCreator showOfferViewCreator;

    public ShowOfferViewCreator getShowOfferViewCreator() {
        if (showOfferViewCreator==null){
            showOfferViewCreator = new ShowOfferViewCreator(getContext());
        }
        return showOfferViewCreator;
    }

    public NewShowOfferHolderAdapter getAdapter() {
        return showOfferHolderAdapter;
    }

    public ShowOfferViewFactory getShowOfferViewFactory() {
        if (showOfferViewFactory==null){
            showOfferViewFactory = new ShowOfferViewFactory(this.getShowOfferViewCreator());
        }
        return showOfferViewFactory;
    }
    public ShowOfferView getInitialView(@NonNull View view, int viewType) {
        return this.getShowOfferViewFactory().getInitialView(view, viewType);
    }
    public ShowOfferView getSmallView(@NonNull View view,int numOfPosts, int viewType) {
        return this.getShowOfferViewFactory().getSmallView(view, numOfPosts, viewType);
    }
    public ShowOfferView getBigView(@NonNull View view,int numOfPosts, int viewType) {
        return this.getShowOfferViewFactory().getBigView(view, numOfPosts, viewType);
    }

    public void setAdapter(NewShowOfferHolderAdapter showOfferHolderAdapter) {
        this.showOfferHolderAdapter=showOfferHolderAdapter;
    }

    public void setOfferTitle(@NonNull ShowOfferView holder,@NonNull Long senderId,@NonNull Long receiverId) {
        if(LoginDetails.getInstance().getUserid().equals(senderId)) {
            holder.getShowOfferRelativeLayout().setTextOnTvOfferTitle(MessagesString.OFFER_TO + receiverId);
        }else{
            holder.getShowOfferRelativeLayout().setTextOnTvOfferTitle(MessagesString.OFFER_FROM + receiverId);
        }
    }
    private void setOfferDate(@NonNull ShowOfferView holder, @NonNull String strDate) {
        String date= CommonResources.convertDate(strDate);
        holder.getShowOfferRelativeLayout().setTextOnTvOfferDateOffered(date);
    }
    private void setOfferStatus(@NonNull ShowOfferView holder, int status) {
        OfferStatus offerStatus = OfferStatus.getEnum(status);
        holder.getShowOfferRelativeLayout().setTextOnTvStatus(offerStatus.getName());
        holder.getShowOfferRelativeLayout().setTextColorOnTvStatus(offerStatus.getColor());
    }

    public void setTextViews(@NonNull ShowOfferView holder, @NonNull NewOffer offer) {
        this.setOfferTitle(holder,offer.getSenderId(), offer.getReceiverId());
        setOfferDate(holder, offer.getCreateDate());
        setOfferStatus(holder, offer.getStatus());
    }

    public void setPostImages(@NonNull ShowOfferView holder, @NonNull NewOffer offer) {
        if (offer!=null && holder!=null){
            List<OfferPost> offerPosts = new ArrayList<>();
            for (OfferPost post:offer.getSenderPosts()){
                offerPosts.add(post);
            }
            for (OfferPost post:offer.getReceiverPosts()){
                offerPosts.add(post);
            }
            setPostImages(holder, offerPosts);
        }
    }

    public void setPostImages(@NonNull ShowOfferView holder, @NonNull List<OfferPost> offerPosts) {
        ImageButton[] imageButtons = holder.getIbPrimaryImageOffer();
        int i=0;
        for (OfferPost offerPost:offerPosts){
            String url = CommonResources.getPrimaryImageURL(offerPost.getPostId());
            Image.loadAndFitImageOnImageButton(getContext(), url, imageButtons[i]);
            imageButtons[i].setVisibility(View.VISIBLE);
            imageButtons[i].setOnClickListener(this.getListener(String.valueOf(offerPost.getPostId())));
            i++;
        }

    }

    public View.OnClickListener getListener(@NonNull String postId){
        return this.getAdapter().getShowOfferListener().getPrimaryOfferImageOnClickListener(postId);
    }

    public Context getContext(){
        return this.getAdapter().getContext();
    }

    public void setImagesButtonArray(int totalNumOfPosts) {
        this.getShowOfferViewFactory().getImagesButtonArray(totalNumOfPosts);
    }

    public List<NewOffer> getSentOffersFromAllOffers(@NonNull List<NewOffer> offerList) {
        List<NewOffer> sentOfferList = new ArrayList<>();
        for(NewOffer offer:offerList){
            if(offer.getSenderId().equals(LoginDetails.getInstance().getUserid())){
                sentOfferList.add(offer);
            }
        }
        return sentOfferList;
    }

    public List<NewOffer> getReceivedOffersFromAllOffers(@NonNull List<NewOffer> offerList) {
        List<NewOffer> receivedOfferList = new ArrayList<>();
        for(NewOffer offer:offerList){
            if(offer.getReceiverId().equals(LoginDetails.getInstance().getUserid())){
                receivedOfferList.add(offer);
            }
        }
        return receivedOfferList;
    }

}
