package common.barter.com.barterapp;

/**
 * Created by vikram on 26/05/16.
 */
public interface ModelCallBackListener<T> {
        void onSuccess(T t);
        void onFailure(String errorMessage);
}
