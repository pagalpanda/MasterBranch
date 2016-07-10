package common.barter.com.barterapp.data.domain;

import android.support.annotation.NonNull;

/**
 * Created by vikram on 26/06/16.
 */
public class BaseEntity {
    private long id;

    @NonNull
    private int isDeleted;
    @NonNull
    private String createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
    @NonNull
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(@NonNull String createDate) {
        this.createDate = createDate;
    }

}
