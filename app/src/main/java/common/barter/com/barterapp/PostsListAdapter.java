package common.barter.com.barterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PostsListAdapter extends BaseAdapter{
    //String [] result;
    Context context;
    int [] imageId;
    ArrayList<Post> listOfPosts;
    String postId;
    String image;
    JSONParser jsonParser = new JSONParser();

    private static LayoutInflater inflater=null;

   public PostsListAdapter(Context context, ArrayList<Post> listOfPosts) {
            // TODO Auto-generated constructor stub
            this.context=context;
        this.listOfPosts = listOfPosts;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listOfPosts.size();
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
        TextView tvTitle;
        ImageView ivPrimaryImage;
        TextView tvLocality;
        TextView tvDateCreated;
        int position;
    }
    //Holder holder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub

        View rowView = null;

        if(convertView == null) {
            rowView = inflater.inflate(R.layout.list_posts_item, null);
            final Holder holder=new Holder();
            holder.tvTitle=(TextView) rowView.findViewById(R.id.tvTitleNamePost);
            holder.ivPrimaryImage=(ImageView) rowView.findViewById(R.id.ivPrimaryImage);
            holder.tvLocality=(TextView) rowView.findViewById(R.id.tvLocalityPosts);
            holder.tvDateCreated=(TextView) rowView.findViewById(R.id.tvDateCreated);
            rowView.setTag(holder);
        }else {
            rowView=convertView;
        }

            Holder holder = (Holder) rowView.getTag();
        holder.ivPrimaryImage.setId(position);
        holder.ivPrimaryImage.setTag(listOfPosts.get(position).getPostId());
        holder.ivPrimaryImage.setImageBitmap(null); // Added for flickering issue
        holder.tvTitle.setText(listOfPosts.get(position).getTitle());
            holder.tvLocality.setText(listOfPosts.get(position).getLocality());
        //String sqlDate = listOfPosts.get(position).getCreatedDate();

        holder.tvDateCreated.setText( CommonResources.convertDate(listOfPosts.get(position).getCreatedDate() ));
        postId = listOfPosts.get(position).getPostId();
        Image image = new Image();
        image.setImg(holder.ivPrimaryImage);
        holder.position = position;
        String url = CommonResources.getStaticURL()+"uploadedimages/"+postId+"_1";

        Picasso.with(context).load(url).fit().into(holder.ivPrimaryImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {


            }
        });

        return rowView;
    }


}