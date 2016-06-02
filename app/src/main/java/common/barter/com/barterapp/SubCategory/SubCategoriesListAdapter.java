package common.barter.com.barterapp.SubCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import common.barter.com.barterapp.GlobalHome;
import common.barter.com.barterapp.R;

public class SubCategoriesListAdapter extends BaseAdapter{
    //String [] result;
    Context context;
    int [] imageId;
    ArrayList<String> listOfSubCategoris;

    private static LayoutInflater inflater=null;

    public SubCategoriesListAdapter(GlobalHome mainActivity, ArrayList<String> listOfSubCategoris) {
        // TODO Auto-generated constructor stub
        //result=prgmNameList;
        context=mainActivity;
        //imageId=prgmImages;
        this.listOfSubCategoris = listOfSubCategoris;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listOfSubCategoris.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tvSubCategoryName;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_subcategory_items, null);
        holder.tvSubCategoryName=(TextView) rowView.findViewById(R.id.tvSubCategoryName);
        //holder.img=(ImageView) rowView.findViewById(R.id.imageViewCategoryIcon);
        holder.tvSubCategoryName.setText(listOfSubCategoris.get(position));
        //holder.img.setImageResource(imageId[position]);
//        rowView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+listOfSubCategoris.get(position), Toast.LENGTH_LONG).show();
//            }
//        });
        return rowView;
    }

}