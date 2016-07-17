package common.barter.com.barterapp.posts;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.Image;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.data.domain.NewPost;

/**
 * Created by vikramb on 15/07/16.
 */
public class PostListHolder extends RecyclerView.ViewHolder
{
    private TextView tvTitle;
    private ImageView ivPrimaryImage;
    private TextView tvLocality;
    private TextView tvDateCreated;
    private CardView cardView;

    public PostListHolder(View rowView, int i) {
        super(rowView);
        tvTitle=(TextView) rowView.findViewById(R.id.tvTitleNamePost);
        ivPrimaryImage=(ImageView) rowView.findViewById(R.id.ivPrimaryImage);
        tvLocality=(TextView) rowView.findViewById(R.id.tvLocalityPosts);
        tvDateCreated=(TextView) rowView.findViewById(R.id.tvDateCreated);
        cardView = (CardView)rowView.findViewById(R.id.cvPostItems);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }

    public void setLocality(String locality) {
        this.tvLocality.setText(locality);
    }

    public void setCreateDate(String date) {
        this.tvDateCreated.setText(date);
    }

    public void setPrimaryImage(Context context, NewPost post, int position) {
        this.ivPrimaryImage.setId(position);
        this.ivPrimaryImage.setTag(post.getId());
        this.ivPrimaryImage.setImageBitmap(null);
        String url = CommonResources.getPrimaryImageURL(post.getId());
        Image.loadAndFitImageOnImageView(context, url, this.ivPrimaryImage);
    }

}
