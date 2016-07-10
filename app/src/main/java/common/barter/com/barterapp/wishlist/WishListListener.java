package common.barter.com.barterapp.wishlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.RecyclerItemClickListener;
import common.barter.com.barterapp.SwipeableRecyclerViewTouchListener;

/**
 * Created by vikram on 05/07/16.
 */
public class WishListListener {
    private Context context;
    private WishListPresenter wishListPresenter;

    public WishListListener(Context context, WishListPresenter wishListPresenter) {
        this.context = context;
        this.wishListPresenter = wishListPresenter;
    }

    public Context getContext() {
        return context;
    }

    public WishListPresenter getWishListPresenter() {
        return wishListPresenter;
    }

    @NonNull
    public RecyclerItemClickListener getOnItemTouchListener() {
        return new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getWishListPresenter().onItemTouched(position);
            }
        });
    }

    @NonNull
    public SwipeableRecyclerViewTouchListener getSwipeableRecyclerViewTouchListener(RecyclerView recyclerView) {
        return new SwipeableRecyclerViewTouchListener(recyclerView,
                new SwipeableRecyclerViewTouchListener.SwipeListener() {
                    @Override
                    public boolean canSwipe(int position) {
                        return getWishListPresenter().canSwipe(position);
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        getWishListPresenter().onDismissedBySwipeLeft(reverseSortedPositions);
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        getWishListPresenter().onDismissedBySwipeRight(reverseSortedPositions);
                    }
                });
    }

}
