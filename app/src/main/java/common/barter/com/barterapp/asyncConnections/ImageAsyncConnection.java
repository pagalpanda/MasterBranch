package common.barter.com.barterapp.asyncConnections;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.Image;
import common.barter.com.barterapp.MessagesString;

/**
 * Created by vikramb on 15/07/16.
 */
public abstract class ImageAsyncConnection extends AsyncTask<String, Void, Bitmap> {
    private Context context;
    private String url;

    public ImageAsyncConnection(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public abstract void receiveImage(Bitmap bitmap);

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            Bitmap myBitmap = Image.loadImage(context,url);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap == null){
            CommonUtil.flash(context, MessagesString.CHECK_NETWORK_CONNECTIVITY);
            return;
        }
        receiveImage(bitmap);
    }
}
