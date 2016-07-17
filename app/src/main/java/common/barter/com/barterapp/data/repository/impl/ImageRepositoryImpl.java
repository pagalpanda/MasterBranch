package common.barter.com.barterapp.data.repository.impl;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.asyncConnections.ImageAsyncConnection;
import common.barter.com.barterapp.data.repository.ImageRepository;

/**
 * Created by vikramb on 15/07/16.
 */
public class ImageRepositoryImpl implements ImageRepository {
    private volatile ArrayList<Bitmap> bitmaps;
    private CountDownLatch latch;

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }
    public synchronized void addImageToList(Bitmap bitmap) {
        this.bitmaps.add(bitmap);
    }

    @Override
    public ArrayList<Bitmap> getAllImagesForPost(Context context,long postId, long numOfImages) throws InterruptedException {
        bitmaps = new ArrayList<>();
        latch = new CountDownLatch(((int) numOfImages));
        for (int i=0;i<numOfImages;i++){
            String url = CommonResources.getImageURL(postId,i+1);
            this.sendHttpRequestForImage(context,url);
        }
        latch.await(60,TimeUnit.SECONDS);
        return bitmaps;
    }

    private void sendHttpRequestForImage(Context context,String url){
        ImageAsyncConnection asyncConnection = getImageAsyncConnection(context,url);
        asyncConnection.execute();
    }

    private ImageAsyncConnection getImageAsyncConnection(Context context,String url) {
        return new  ImageAsyncConnection(context, url)  {
            @Override
            public void receiveImage(Bitmap bitmap) {
                addImageToList(bitmap);
                latch.countDown();
            }
        };
    }

}
