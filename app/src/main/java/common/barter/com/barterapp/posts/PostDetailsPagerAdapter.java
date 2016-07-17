package common.barter.com.barterapp.posts;

/**
 * Created by amitpa on 9/4/2015.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.R;


public class PostDetailsPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    String postId;
    int numOfImages;
    LayoutInflater inflater;
    ArrayList<Bitmap> photos;

    public PostDetailsPagerAdapter(Context context, long postId, int numOfImages) {
        this.context = context;
        this.postId = postId;
        this.numOfImages=numOfImages;

    }

    @Override
    public int getCount() {
        return numOfImages;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables

        ImageView imgflag;
       // photos = new ArrayList<Bitmap>();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_pager_item, container,false);

        imgflag = (ImageView)itemView.findViewById(R.id.ivImagePostDetails);

        String url = CommonResources.getStaticURL()+"uploadedimages/"+postId+"_"+(position+1);
        Picasso.with(context).load(url).fit().into(imgflag, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {


            }
        });

        //photos.add(bitmap);



        (container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }


}