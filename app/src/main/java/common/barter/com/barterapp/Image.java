package common.barter.com.barterapp;

/**
 * Created by amitpa on 9/1/2015.
 */
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.util.Base64;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ProgressBar;

        import com.squareup.picasso.Picasso;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;

public class Image {

    private ImageView img;

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public static void loadAndFitImageOnImageButton(Context context, String url, ImageButton imageButton) {
        Picasso.with(context).load(url).fit().into(imageButton);
    }

    public static void loadAndFitImageOnImageView(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).fit().into(imageView);
    }

    public static Bitmap loadImage(Context context, String url) throws IOException {
        return Picasso.with(context).load(url).get();
    }

    public static String getBase64EncodedImage(Bitmap image) throws IOException {
        ByteArrayOutputStream byteArrayBitmapStream= new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        try {
            byteArrayBitmapStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encodedImage;
    }

    public static void invalidateUrl(Context context,int numOfPhotos,long postId) {
        for(int i=0; i<numOfPhotos; i++){
            String url = CommonResources.getStaticURL()+"uploadedimages/"+postId+"_"+(i+1);
            Picasso.with(context).invalidate(url);
        }
    }

}