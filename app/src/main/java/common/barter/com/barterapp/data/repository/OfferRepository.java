package common.barter.com.barterapp.data.repository;

/**
 * Created by vikram on 26/06/16.
 */
public interface OfferRepository {
    void getAllOffers(long userId);
    void getAllSentOffers(long userId);
    void getAllReceivedOffers(long userId);
}
