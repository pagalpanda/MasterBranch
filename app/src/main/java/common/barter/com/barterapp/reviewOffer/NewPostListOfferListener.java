package common.barter.com.barterapp.reviewOffer;

import android.support.annotation.NonNull;
import android.widget.CompoundButton;

import common.barter.com.barterapp.makeOffer.NewPostListOfferAdapter;
import common.barter.com.barterapp.posts.PostsOfferFragment;

/**
 * Created by vikramb on 14/07/16.
 */
public class NewPostListOfferListener {
    private NewPostListOfferAdapter newPostListOfferAdapter;

    public NewPostListOfferListener(NewPostListOfferAdapter newPostListOfferAdapter) {
        this.newPostListOfferAdapter=newPostListOfferAdapter;
    }

    @NonNull
    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener(final int position) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                newPostListOfferAdapter.onCheckedChanged(position,isChecked);
            }
        };
    }

}
