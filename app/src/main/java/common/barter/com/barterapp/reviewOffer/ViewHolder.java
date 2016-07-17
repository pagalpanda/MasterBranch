package common.barter.com.barterapp.reviewOffer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import common.barter.com.barterapp.R;

/**
 * Created by vikramb on 14/07/16.
 */
public class ViewHolder extends RecyclerView.ViewHolder{
    private TextView tvTitle;
    private ImageView ivPrimaryImage;
    private TextView tvPostsMessageReview;

    public ViewHolder(View itemView, int i) {
        super(itemView);
        switch (i){
            case 0:
                tvPostsMessageReview = (TextView)itemView.findViewById(R.id.tvPostsMessageReview);
                break;
            case 1:
                ivPrimaryImage = (ImageView) itemView.findViewById(R.id.ivPrimaryImageReview);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitleNamePostReview);
                break;
            default:
                tvPostsMessageReview = (TextView)itemView.findViewById(R.id.tvPostsMessageReview);
                break;
        }
    }
}
