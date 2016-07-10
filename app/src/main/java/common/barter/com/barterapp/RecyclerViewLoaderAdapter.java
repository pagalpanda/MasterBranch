package common.barter.com.barterapp;

import android.support.v7.widget.RecyclerView;
import java.util.List;
import common.barter.com.barterapp.data.domain.NewOffer;

/**
 * Created by vikram on 25/06/16.
 */
public abstract class RecyclerViewLoaderAdapter <VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<NewOffer> offers;
    private boolean mDataValid;

    public abstract void onBindViewHolder(VH holder, List<NewOffer> offers);

    public RecyclerViewLoaderAdapter(List<NewOffer> offers) {
        setHasStableIds(true);
        swapLoader(offers);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, offers);
    }

    @Override
    public int getItemCount() {
//        if (mDataValid) {
//            return offers.size();
//        } else {
//            return 0;
//        }
        if (offers==null){
            return 2;
        }
        return offers.size();
    }

    @Override
    public long getItemId(int position) {
//        if (!mDataValid) {
//            throw new IllegalStateException("Cannot lookup item id when cursor is in invalid state.");
//        }
//        if (position<0 || position>offers.size()) {
//            throw new ArrayIndexOutOfBoundsException("");
//        }
        if (offers==null){
            return 0;
        }
        return offers.get(position).getId();
    }

    public void swapLoader(List<NewOffer> newOffers) {
        if (newOffers!=null && newOffers.equals(offers)) {
            return;
        }
        if (newOffers != null) {
            offers = newOffers;
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            notifyItemRangeRemoved(0, getItemCount());
            offers = null;
            mDataValid = false;
        }
    }
}
