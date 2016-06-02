package common.barter.com.barterapp.postad;

/**
 * Created by Panda on 17-05-2016.
 */
public interface PostAdNetworkCallsListener<T> {
    public void onLocalitiesFetchSuccess(T t);
    public void onAdPostSuccess(int returnCode);
    public void onNetworkCallFailure();
}
