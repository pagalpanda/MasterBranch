package common.barter.com.barterapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by amitpa on 8/20/2015.
 */
public class GridViewAdapter extends BaseAdapter{

    private static LayoutInflater inflater=null;
    Context context;
    Categories categories[];

    public GridViewAdapter(Context context, Categories categories[]) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.categories = categories;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return categories.length;
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
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.home_grid, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textViewCategoryName);
        holder.img=(ImageView) rowView.findViewById(R.id.imageViewCategoryIcon);

        holder.tv.setText(categories[position].getName());
        holder.img.setImageResource(categories[position].getIcon());



        return rowView;
    }

}
