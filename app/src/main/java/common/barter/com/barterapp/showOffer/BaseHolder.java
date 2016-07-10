package common.barter.com.barterapp.showOffer;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

import common.barter.com.barterapp.R;

/**
 * Created by vikram on 20/06/16.
 */
public class BaseHolder   extends RecyclerView.ViewHolder  {
    private final int numOfPrimaryImages;
    private final int typeItems;
    private TextView tvOfferTitle;
    private TextView tvOfferDateOffered;
    private TextView tvStatus;
    private CardView cardView;
    private int itemPosition;
    private View.OnClickListener onClickListener;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public int getNumOfPrimaryImages() {
        return numOfPrimaryImages;
    }

    public int getTypeItems() {
        return typeItems;
    }

    public BaseHolder(View itemView,int typeItems,int numOfPrimaryImages,View.OnClickListener onClickListener) {
        super(itemView);
        this.numOfPrimaryImages=numOfPrimaryImages;
        this.typeItems=typeItems;
        this.onClickListener=onClickListener;
        initializeWidgets(itemView);
    }

    private void initializeWidgets(View itemView) {
        tvStatus = (TextView) itemView.findViewById(R.id.tvStatusOfOffer);
        tvOfferTitle = (TextView) itemView.findViewById(R.id.tvOfferTitle);
        tvOfferDateOffered = (TextView) itemView.findViewById(R.id.tvOfferDateOffered);
    }
    protected int getViewId(String fieldName) {
        int id = 0;
        try{
            Class clazz = R.id.class;
            Field f = clazz.getField(fieldName);
            id = f.getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return id;
    }
}
