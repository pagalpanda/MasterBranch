package common.barter.com.barterapp.data.domain;

import android.support.annotation.NonNull;

/**
 * Created by vikram on 26/06/16.
 */
public class OfferPost extends BaseEntity {
    @NonNull
    private long offerId;
    @NonNull
    private long postId;
    @NonNull
    private int originatingFromSender;

    public OfferPost(@NonNull long offerId, @NonNull long postId, @NonNull int originatingFromSender) {
        this.offerId = offerId;
        this.postId = postId;
        this.originatingFromSender = originatingFromSender;
    }

    public long getOfferId() {
        return offerId;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public int getOriginatingFromSender() {
        return originatingFromSender;
    }

    public void setOriginatingFromSender(int originatingFromSender) {
        this.originatingFromSender = originatingFromSender;
    }

}
