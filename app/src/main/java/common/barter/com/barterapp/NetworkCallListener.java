package common.barter.com.barterapp;

/**
 * Created by Panda on 17-05-2016.
 */
public interface NetworkCallListener {
    public void onNetworkCallSuccess(int returnCode);
    public void onNetworkCallFailure();
}
