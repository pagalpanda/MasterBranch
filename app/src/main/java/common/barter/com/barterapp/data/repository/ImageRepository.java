package common.barter.com.barterapp.data.repository;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by vikramb on 15/07/16.
 */
public interface ImageRepository {
    ArrayList<Bitmap> getAllImagesForPost(final Context context,final long postId, final long numOfImages) throws InterruptedException;
}
