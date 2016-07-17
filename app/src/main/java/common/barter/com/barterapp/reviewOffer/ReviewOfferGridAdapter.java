package common.barter.com.barterapp.reviewOffer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import common.barter.com.barterapp.data.domain.NewOffer;
import common.barter.com.barterapp.data.domain.NewPost;

public class ReviewOfferGridAdapter extends RecyclerView.Adapter<ViewHolder>{
    //String [] result;
    private Context context;
    private int [] imageId;
    private ArrayList<NewPost> posts;
    private Long postId;
    private String image;
    private JSONParser jsonParser = new JSONParser();
    private String mode;
    private int sizePostsMine;
    private int sizePostsHis;
    private int emptyCells;
    private int itemType;

    private static LayoutInflater inflater=null;

    public ReviewOfferGridAdapter(Context context, ArrayList<NewPost> posts , String mode) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.posts=posts;
        if(0 != this.sizePostsMine%3)
            this.emptyCells = 3 - (this.sizePostsMine%3);
        this.mode=mode;
    }

    @Override
    public long getItemId(int position) {
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
        itemType = i;
        switch (i){
            case 0:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.headers_offer_review, viewGroup, false);
                break;
            case 1:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_items_review_offer, viewGroup, false);
                break;
            case 2:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.headers_offer_review, viewGroup, false);
                break;
        }
        ViewHolder viewHolder = new ViewHolder(v,i);
        Log.d("", posts.toString());
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (itemType == 0) {
            viewHolder.tvPostsMessageReview.setText("My Posts");
       //     return;
        } else if (itemType == 2) {
                      viewHolder.tvPostsMessageReview.setText("His Posts");
            //return;
        } else {
            if (i < sizePostsMine + 1 + emptyCells) {
                if (posts.get(i - 1) != null) {
                    viewHolder.ivPrimaryImage.setTag(posts.get(i - 1).getId());
                    viewHolder.ivPrimaryImage.setImageBitmap(null); // Added for flickering issue
                    viewHolder.tvTitle.setText(listOfPostsAll.get(i - 1).getTitle());
                    postId = listOfPostsAll.get(i - 1).getId();
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
                viewHolder.ivPrimaryImage.setTag(listOfPostsAll.get(i - 2).getId());
                viewHolder.ivPrimaryImage.setImageBitmap(null); // Added for flickering issue
                viewHolder.tvTitle.setText(listOfPostsAll.get(i - 2).getTitle());
                postId = listOfPostsAll.get(i - 2).getId();
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

    public boolean isHeaderHis(int position) {
        return 1+(sizePostsMine+emptyCells-1)+1 == position;//position == 0;
    }
    public boolean isHeaderMine(int position) {
        return position == 0;
    }

}