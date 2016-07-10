package common.barter.com.barterapp.data.domain;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.Offer;
import common.barter.com.barterapp.data.constants.OfferStatus;

/**
 * Created by vikram on 26/06/16.
 */
public class NewOffer extends BaseEntity {

    @NonNull
    private Long senderId;
    @NonNull
    private Long receiverId;
    @NonNull
    private String senderName;
    @NonNull
    private String receiverName;

    @NonNull
    private long originatorOfferId;

    @NonNull
    private int numOfSenderPosts;

    @NonNull
    private int numOfReceiverPosts;

    private String interestedIn;

    @NonNull
    private int status;

    @NonNull
    private List<OfferPost> senderPosts;

    @NonNull
    private List<OfferPost> receiverPosts;

    @NonNull
    private String lastUpdateDate;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public long getOriginatorOfferId() {
        return originatorOfferId;
    }

    public void setOriginatorOfferId(long originatorOfferId) {
        this.originatorOfferId = originatorOfferId;
    }

    public int getNumOfSenderPosts() {
        return numOfSenderPosts;
    }

    public void setNumOfSenderPosts(int numOfSenderPosts) {
        this.numOfSenderPosts = numOfSenderPosts;
    }

    public int getNumOfReceiverPosts() {
        return numOfReceiverPosts;
    }

    public void setNumOfReceiverPosts(int numOfReceiverPosts) {
        this.numOfReceiverPosts = numOfReceiverPosts;
    }

    public String getInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(String interestedIn) {
        this.interestedIn = interestedIn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<OfferPost> getSenderPosts() {
        return senderPosts;
    }

    public void setSenderPosts(List<OfferPost> senderPosts) {
        this.senderPosts = senderPosts;
    }

    public List<OfferPost> getReceiverPosts() {
        return receiverPosts;
    }

    public void setReceiverPosts(List<OfferPost> receiverPosts) {
        this.receiverPosts = receiverPosts;
    }
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public static NewOffer getOfferFromJson(JSONObject json) throws JSONException {
        NewOffer offer = new NewOffer();
        offer.setId(json.getLong(MessagesString.OFFER_ID));
        offer.setSenderId(json.getLong(MessagesString.SENDER_ID));
        offer.setReceiverId(json.getLong(MessagesString.RECEIVER_ID));
        offer.setSenderName(json.getString(MessagesString.SENDER_NAME));
        offer.setReceiverName(json.getString(MessagesString.RECEIVER_NAME));
        offer.setStatus(json.getInt(MessagesString.STATUS));
        offer.setNumOfReceiverPosts(json.getInt(MessagesString.NUM_OF_RECEIVER_POSTS));
        offer.setNumOfSenderPosts(json.getInt(MessagesString.NUM_OF_SENDER_POSTS));
        offer.setCreateDate(json.getString(MessagesString.CREATE_DATE));
        offer.setLastUpdateDate(json.getString(MessagesString.LAST_UPDATE_DATE));
        offer.setSenderPosts(getOfferPostsFromJsonArray(json.getJSONArray(MessagesString.SENDER_POSTS), offer.getId(), 1));
        offer.setReceiverPosts(getOfferPostsFromJsonArray(json.getJSONArray(MessagesString.RECEIVER_POSTS),offer.getId(),0));
        return offer;
    }

    private static List<OfferPost> getOfferPostsFromJsonArray(JSONArray jsonArray,long offerid,int isOriganingFromSender) throws JSONException {
        List<OfferPost> posts = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            long postId = jsonArray.getLong(i);
            posts.add(new OfferPost(offerid,postId,isOriganingFromSender));
        }
        return posts;
    }

}

