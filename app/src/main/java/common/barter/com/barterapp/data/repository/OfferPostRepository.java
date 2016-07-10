package common.barter.com.barterapp.data.repository;

/**
 * Created by vikram on 26/06/16.
 */
public interface OfferPostRepository {
    void getOfferPostsForOffer(long offerId);
    void getOfferPostsForOfferAndUser(long offerId,long userId);
}
