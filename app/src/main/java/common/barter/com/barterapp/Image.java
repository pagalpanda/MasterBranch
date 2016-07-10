package common.barter.com.barterapp;

/**
 * Created by amitpa on 9/1/2015.
 */
        import android.content.Context;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ProgressBar;

        import com.squareup.picasso.Picasso;

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
}