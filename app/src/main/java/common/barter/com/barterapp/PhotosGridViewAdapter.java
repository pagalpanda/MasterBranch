package common.barter.com.barterapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by amitpa on 8/20/2015.
 */
public class PhotosGridViewAdapter extends BaseAdapter{

    private static LayoutInflater inflater=null;
    Context context;
    ArrayList<Bitmap> photos;
    String mode;

    public PhotosGridViewAdapter(Context context, ArrayList<Bitmap> photos) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.photos = photos;
        this.mode = mode;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class Holder
    {

        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.photos_grid, null);

        holder.img=(ImageView) rowView.findViewById(R.id.ivPhoto);


        holder.img.setImageBitmap(photos.get(position));



        return rowView;
    }

}
