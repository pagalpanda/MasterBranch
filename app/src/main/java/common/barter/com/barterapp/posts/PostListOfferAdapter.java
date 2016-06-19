package common.barter.com.barterapp.posts;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.Image;
import common.barter.com.barterapp.JSONParser;
import common.barter.com.barterapp.Post;
import common.barter.com.barterapp.R;

public class PostListOfferAdapter extends RecyclerView.Adapter<PostListOfferAdapter.Holder>{
    //String [] result;
    Context context;
    int [] imageId;
    ArrayList<Post> listOfPosts;
    String postId;
    String image;
    JSONParser jsonParser = new JSONParser();

    private static LayoutInflater inflater=null;


    public PostListOfferAdapter(Context context, ArrayList<Post> listOfPosts) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.listOfPosts = listOfPosts;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }

    boolean isListMine;
    public PostListOfferAdapter(Context context, ArrayList<Post> listOfPosts, boolean isListMine) {
        // TODO Auto-generated constructor stub
        this(context,listOfPosts);
        this.context=context;
        this.listOfPosts = listOfPosts;
        this.isListMine = isListMine;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }
    String mode;
    public PostListOfferAdapter(Context context, ArrayList<Post> listOfPosts, String mode) {
        // TODO Auto-generated constructor stub
        this(context,listOfPosts);
        this.mode=mode;



    }
//    @Override
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return listOfPosts.size();
//    }

//    @Override
//    public Object getItem(int position) {
//        // TODO Auto-generated method stub
//        return position;
//    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_post_items_offer, parent, false);

        Holder viewHolder = new Holder(v,viewType);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(Holder holder,final int position) {
        holder.ivPrimaryImage.setId(position);
        holder.ivPrimaryImage.setTag(listOfPosts.get(position).getPostId());
        holder.ivPrimaryImage.setImageBitmap(null); // Added for flickering issue
        holder.tvTitle.setText(listOfPosts.get(position).getTitle());
        holder.tvLocality.setText(listOfPosts.get(position).getLocality());
        holder.tvDateCreated.setText(CommonResources.convertDate(listOfPosts.get(position).getCreatedDate()));
        postId = listOfPosts.get(position).getPostId();
        Image image = new Image();
        image.setImg(holder.ivPrimaryImage);
        holder.position = position;
        String url = CommonResources.getStaticURL() + "uploadedimages/" + postId + "_1";

        Picasso.with(context).load(url).fit().into(holder.ivPrimaryImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {


            }
        });

        if(!"review".equalsIgnoreCase(mode)) {

            holder.cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //rowView.setBackgroundColor(Color.GRAY);
                        if (isListMine)
                            PostsOfferFragment.sListOfPostsMine.get(position).setIsSelected(true);
                        else PostsOfferFragment.sListOfPostsHis.get(position).setIsSelected(true);
                    } else {
                        //rowView.setBackgroundColor(Color.WHITE);
                        if (isListMine)
                            PostsOfferFragment.sListOfPostsMine.get(position).setIsSelected(false);
                        else PostsOfferFragment.sListOfPostsHis.get(position).setIsSelected(false);
                    }
                }
            });
            if (listOfPosts.get(position).isSelected()) {

                //rowView.setBackgroundColor(Color.GRAY);
                holder.cbSelected.setChecked(true);
            } else {
                //rowView.setBackgroundColor(Color.WHITE);
                holder.cbSelected.setChecked(false);
            }
        }else {
            //rowView.setBackgroundColor(Color.WHITE);
            //holder.cbSelected.setVisibility(View.GONE);
        }

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

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView tvTitle;
        ImageView ivPrimaryImage;
        TextView tvLocality;
        TextView tvDateCreated;
        int position;
        CheckBox cbSelected;
        CardView cardView;

        public Holder(View rowView, int i) {
            super(rowView);
            tvTitle = (TextView) rowView.findViewById(R.id.tvTitleNamePostOffer);
            ivPrimaryImage = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer);
            tvLocality = (TextView) rowView.findViewById(R.id.tvLocalityPostsOffer);
            tvDateCreated = (TextView) rowView.findViewById(R.id.tvDateCreatedOffer);
            cbSelected = (CheckBox) rowView.findViewById(R.id.cbPostSelected);
            cardView = (CardView)rowView.findViewById(R.id.cvPostOfferItems);
        }
    }


}