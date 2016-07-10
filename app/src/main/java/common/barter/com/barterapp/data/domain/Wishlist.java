package common.barter.com.barterapp.data.domain;

import android.support.annotation.NonNull;

import common.barter.com.barterapp.LoginDetails;

/**
 * Created by vikram on 05/07/16.
 */
public class Wishlist extends BaseEntity {

    @NonNull
    private Long senderId;
    @NonNull
    private Long postId;
    @NonNull
    private String lastUpdateDate;

    @NonNull
    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(@NonNull Long senderId) {
        this.senderId = senderId;
    }

    @NonNull
    public Long getPostId() {
        return postId;
    }

    public void setPostId(@NonNull Long postId) {
        this.postId = postId;
    }

    @NonNull
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(@NonNull String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

}
