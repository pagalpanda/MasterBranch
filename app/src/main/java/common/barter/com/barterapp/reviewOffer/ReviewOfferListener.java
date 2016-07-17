package common.barter.com.barterapp.reviewOffer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import common.barter.com.barterapp.R;
import common.barter.com.barterapp.RecyclerItemClickListener;
import common.barter.com.barterapp.makeOffer.MakeOfferFragment;
import common.barter.com.barterapp.makeOffer.NewPostDetailsFragment;
import common.barter.com.barterapp.posts.PostDetailsFragment;

/**
 * Created by vikramb on 13/07/16.
 */
public class ReviewOfferListener {
    private ReviewOfferPresenter reviewOfferPresenter;
    private Context context;

    public ReviewOfferListener(Context context,ReviewOfferPresenter reviewOfferPresenter) {
        this.context=context;
        this.reviewOfferPresenter=reviewOfferPresenter;
    }

    public Context getContext() {
        return context;
    }

    @NonNull
    public RecyclerItemClickListener getOnRecyclerItemClickListener() {
        return new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                reviewOfferPresenter.onRecyclerItemClicked(position);

            }
        });
    }

    @NonNull
    public View.OnClickListener getSubmitOfferOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewOfferPresenter.onSubmitOfferClicked();
            }
        };
    }

    @NonNull
    public View.OnClickListener getRejectOfferOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewOfferPresenter.onRejectOfferClicked();
            }
        };
    }

    @NonNull
    public View.OnClickListener getAcceptOfferOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewOfferPresenter.onAcceptOfferClicked();
            }
        };
    }

    @NonNull
    public View.OnClickListener getDeleteOfferOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewOfferPresenter.onAcceptOfferClicked();
            }
        };
    }

    @NonNull
    public View.OnClickListener getOnCounterOfferClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewOfferPresenter.onCounterOfferClicked();
            }
        };
    }

    @NonNull
    public View.OnClickListener getOnMakeOfferClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewOfferPresenter.onMakeOfferClicked();
            }
        };
    }
    @NonNull
    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return reviewOfferPresenter.getSpanSize(position);
            }
        };
    }

}
