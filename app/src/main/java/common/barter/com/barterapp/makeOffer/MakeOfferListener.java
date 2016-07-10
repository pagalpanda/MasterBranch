package common.barter.com.barterapp.makeOffer;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by vikram on 06/07/16.
 */
public class MakeOfferListener {
    private MakeOfferPresenter makeOfferPresenter;

    public MakeOfferListener(MakeOfferPresenter makeOfferPresenter) {
        this.makeOfferPresenter = makeOfferPresenter;
    }

    @NonNull
    public View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOfferPresenter.onReviewClicked();
            }
        };
    }

}
