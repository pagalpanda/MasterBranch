package common.barter.com.barterapp.reviewOffer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.Image;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.data.domain.NewPost;

/**
 * Created by vikramb on 14/07/16.
 */
public class NewPostListOfferHolder extends RecyclerView.ViewHolder
{
    private TextView tvTitle;
    private ImageView ivPrimaryImage;
    private TextView tvLocality;
    private TextView tvDateCreated;
    private CheckBox cbSelected;
    private CardView cardView;

    public NewPostListOfferHolder(View rowView, int i) {
        super(rowView);
        tvTitle = (TextView) rowView.findViewById(R.id.tvTitleNamePostOffer);
        ivPrimaryImage = (ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer);
        tvLocality = (TextView) rowView.findViewById(R.id.tvLocalityPostsOffer);
        tvDateCreated = (TextView) rowView.findViewById(R.id.tvDateCreatedOffer);
        cbSelected = (CheckBox) rowView.findViewById(R.id.cbPostSelected);
        cardView = (CardView)rowView.findViewById(R.id.cvPostOfferItems);
    }

    public void setTitle(String title) {
        this.tvTitle.setText(title);
    }
    public void setLocality(String locality) {
        this.tvLocality.setText(locality);
    }
    public void setCreateDate(String createDate) {
        this.tvDateCreated.setText(CommonResources.convertDate(createDate));
    }
    public void setPrimaryImage(Context context,NewPost post, int position) {
        this.ivPrimaryImage.setId(position);
        this.ivPrimaryImage.setTag(post.getId());
        this.ivPrimaryImage.setImageBitmap(null);
        String url = CommonResources.getPrimaryImageURL(post.getId());
        Image.loadAndFitImageOnImageView(context, url, this.ivPrimaryImage);
    }

    public void setIsSelected(Boolean flag) {
        this.cbSelected.setChecked(flag);
    }
    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener){
        this.cbSelected.setOnCheckedChangeListener(onCheckedChangeListener);
    }

}
