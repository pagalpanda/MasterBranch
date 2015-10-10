package common.barter.com.barterapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PostsListAdapter extends RecyclerView.Adapter<PostsListAdapter.Holder>{
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
    public PostsListAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_posts_item, parent, false);

        Holder viewHolder = new Holder(v,viewType);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(PostsListAdapter.Holder holder, int position) {
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
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemCount() {
       return listOfPosts.size();
    }


    //Holder holder;

//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        // TODO Auto-generated method stub
//
//        View rowView = null;
//
//        if(convertView == null) {
//            rowView = inflater.inflate(R.layout.list_posts_item, null);
//            final Holder holder=new Holder();
//            holder.tvTitle=(TextView) rowView.findViewById(R.id.tvTitleNamePost);
//            holder.ivPrimaryImage=(ImageView) rowView.findViewById(R.id.ivPrimaryImage);
//            holder.tvLocality=(TextView) rowView.findViewById(R.id.tvLocalityPosts);
//            holder.tvDateCreated=(TextView) rowView.findViewById(R.id.tvDateCreated);
//            rowView.setTag(holder);
//        }else {
//            rowView=convertView;
//        }
//
//            Holder holder = (Holder) rowView.getTag();
//        holder.ivPrimaryImage.setId(position);
//        holder.ivPrimaryImage.setTag(listOfPosts.get(position).getPostId());
//        holder.ivPrimaryImage.setImageBitmap(null); // Added for flickering issue
//        holder.tvTitle.setText(listOfPosts.get(position).getTitle());
//            holder.tvLocality.setText(listOfPosts.get(position).getLocality());
//        //String sqlDate = listOfPosts.get(position).getCreatedDate();
//
//        holder.tvDateCreated.setText( CommonResources.convertDate(listOfPosts.get(position).getCreatedDate() ));
//        postId = listOfPosts.get(position).getPostId();
//        Image image = new Image();
//        image.setImg(holder.ivPrimaryImage);
//        holder.position = position;
//        String url = CommonResources.getStaticURL()+"uploadedimages/"+postId+"_1";
//
//        Picasso.with(context).load(url).fit().into(holder.ivPrimaryImage, new Callback() {
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onError() {
//
//
//            }
//        });
//
//        return rowView;
//    }


    public class Holder extends RecyclerView.ViewHolder
    {
        TextView tvTitle;
        ImageView ivPrimaryImage;
        TextView tvLocality;
        TextView tvDateCreated;
        int position;
        CardView cardView;

        public Holder(View rowView, int i) {
            super(rowView);
            tvTitle=(TextView) rowView.findViewById(R.id.tvTitleNamePost);
            ivPrimaryImage=(ImageView) rowView.findViewById(R.id.ivPrimaryImage);
            tvLocality=(TextView) rowView.findViewById(R.id.tvLocalityPosts);
            tvDateCreated=(TextView) rowView.findViewById(R.id.tvDateCreated);

            cardView = (CardView)rowView.findViewById(R.id.cvPostItems);
        }
    }

}