package common.barter.com.barterapp.data.constants;

import android.graphics.Color;
import android.support.annotation.ColorRes;

import common.barter.com.barterapp.MessagesString;

/**
 * Created by vikram on 20/06/16.
 */
public enum OfferStatus{
    PENDING(0, MessagesString.PENDING,Color.GRAY),
    ACCEPTED(1,MessagesString.ACCEPTED,Color.GREEN),
    REJECTED(2,MessagesString.REJECTED,Color.RED),
    COUNTER_OFFERED(3,MessagesString.COUNTER_OFFERED,Color.BLUE);

    private final int statusId;
    private final String statusName;
    private final int color;

    OfferStatus(int statusId,String statusName, @ColorRes int color){
        this.statusId=statusId;
        this.statusName=statusName;
        this.color=color;
    }
    public String getName(){
        return this.statusName;
    }

    public int getStatusId(){
        return this.statusId;
    }

    public int getColor(){
        return this.color;
    }
    public static OfferStatus getEnum(int statusId){
        for(OfferStatus e: OfferStatus.values()) {
            if(e.statusId == statusId) {
                return e;
            }
        }
        return null;
    }
}
