package common.barter.com.barterapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ReviewOfferGridAdapter extends RecyclerView.Adapter<ReviewOfferGridAdapter.ViewHolder>{
    //String [] result;
    Context context;
    int [] imageId;
    ArrayList<Post> listOfPostsMine;
    ArrayList<Post> listOfPostsHis;
    ArrayList<Post> listOfPostsAll;
    String postId;
    String image;
    JSONParser jsonParser = new JSONParser();

    private static LayoutInflater inflater=null;





    String mode;
    public ReviewOfferGridAdapter(Context context, ArrayList<Post> listOfPostsMine,ArrayList<Post> listOfPostsHis , String mode) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.listOfPostsMine = listOfPostsMine;
        this.listOfPostsHis = listOfPostsHis;
        this.sizePostsMine = listOfPostsMine.size();
        this.sizePostsHis = listOfPostsHis.size();
        if(0 != this.sizePostsMine%3)
            this.emptyCells = 3 - (this.sizePostsMine%3);
        this.mode=mode;

        listOfPostsAll = new ArrayList<Post>();
        listOfPostsAll.addAll(listOfPostsMine);

        for(int j = 0; j<emptyCells; j++){
            listOfPostsAll.add(null);
        }
        listOfPostsAll.addAll(listOfPostsHis);



    }


    int emptyCells;

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemCount() {
        return 1+sizePostsMine+sizePostsHis+emptyCells+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderMine(position))
            return 0;
        else if(isHeaderHis(position))
            return 2;
        return 1;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = null;

        if( i == 1 ) {

            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_items_review_offer, viewGroup, false);

        }else if(i == 0){

            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.headers_offer_review, viewGroup, false);
        }else if(i == 2){

            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.headers_offer_review, viewGroup, false);
        }

        ViewHolder viewHolder = new ViewHolder(v,i);
        Log.d("", listOfPostsAll.toString());
        return viewHolder;
    }

    int sizePostsMine;
    int sizePostsHis;
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        if (viewHolder.holderId == 0) {
            viewHolder.tvPostsMessageReview.setText("My Posts");
       //     return;
        } else if (viewHolder.holderId == 2) {
                      viewHolder.tvPostsMessageReview.setText("His Posts");
            //return;
        } else {
            if (i < sizePostsMine + 1 + emptyCells) {
                if (listOfPostsAll.get(i - 1) != null) {
                    viewHolder.ivPrimaryImage.setTag(listOfPostsAll.get(i - 1).getPostId());
                    viewHolder.ivPrimaryImage.setImageBitmap(null); // Added for flickering issue
                    viewHolder.tvTitle.setText(listOfPostsAll.get(i - 1).getTitle());
                    postId = listOfPostsAll.get(i - 1).getPostId();
                    Image image = new Image();
                    image.setImg(viewHolder.ivPrimaryImage);
                    //viewHolder.holderId = i - 1;
                    String url = CommonResources.getStaticURL() + "uploadedimages/" + postId + "_1";

                    Picasso.with(context).load(url).fit().into(viewHolder.ivPrimaryImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {


                        }
                    });
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                } else {

                    viewHolder.itemView.setVisibility(View.GONE);
                }
            } else {
                viewHolder.itemView.setVisibility(View.VISIBLE);
                viewHolder.ivPrimaryImage.setTag(listOfPostsAll.get(i - 2).getPostId());
                viewHolder.ivPrimaryImage.setImageBitmap(null); // Added for flickering issue
                viewHolder.tvTitle.setText(listOfPostsAll.get(i - 2).getTitle());
                postId = listOfPostsAll.get(i - 2).getPostId();
                Image image = new Image();
                image.setImg(viewHolder.ivPrimaryImage);
                //viewHolder.holderId = i - 2;
                String url = CommonResources.getStaticURL() + "uploadedimages/" + postId + "_1";

                Picasso.with(context).load(url).fit().into(viewHolder.ivPrimaryImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {


                    }
                });
            }
        }

    }




    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        ImageView ivPrimaryImage;
        int holderId;
        TextView tvPostsMessageReview;

        public ViewHolder(View itemView, int i) {
            super(itemView);
            if(i == 0){
                tvPostsMessageReview = (TextView)itemView.findViewById(R.id.tvPostsMessageReview);
                holderId = 0;
            }else if( i == 1) {
                ivPrimaryImage = (ImageView) itemView.findViewById(R.id.ivPrimaryImageReview);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitleNamePostReview);
                holderId = 1;
            }else{
                tvPostsMessageReview = (TextView)itemView.findViewById(R.id.tvPostsMessageReview);
                holderId = 2;
            }


        }



    }

    public boolean isHeaderHis(int position) {
        return 1+(sizePostsMine+emptyCells-1)+1 == position;//position == 0;
    }
    public boolean isHeaderMine(int position) {
        return position == 0;
    }

}