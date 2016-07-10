package common.barter.com.barterapp.showOffer;

import android.content.Context;

import common.barter.com.barterapp.posts.PostDetailsDialogOffer;

/**
 * Created by vikram on 20/06/16.
 */
public class ShowOffersHolderPresenter {
    private ShowOfferHolderListener showOfferHolderListener;
    private NewShowOfferHolderAdapter showOfferAdapter;
    private Context context;

    public NewShowOfferHolderAdapter getShowOfferAdapter() {
        return showOfferAdapter;
    }

    public Context getContext() {
        return context;
    }

    public ShowOfferHolderListener getShowOfferHolderListener() {
        return showOfferHolderListener;
    }

    public void onPrimaryOfferImageClicked(String postId) {
        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId);
        dialog.show();
    }

    public void onHoldercardViewOnClickListener(int position) {

    }
}
