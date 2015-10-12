package common.barter.com.barterapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowOffersAdapter extends RecyclerView.Adapter<ShowOffersAdapter.Holder>{
    //String [] result;
    Context context;
    String mode;
    ArrayList<Offer> listOfOffers;

    JSONParser jsonParser = new JSONParser();

    private static LayoutInflater inflater=null;

    public ShowOffersAdapter(Context context, ArrayList<Offer> listOfOffers, String mode) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.listOfOffers = listOfOffers;
        this.mode = mode;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }



    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list__item_offers, parent, false);

        Holder viewHolder = new Holder(v,viewType);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if("myoffers".equalsIgnoreCase(mode)) {
            holder.tvOfferTitle.setText("Offer to "+listOfOffers.get(position).getUserNameHis());
        }else{
            holder.tvOfferTitle.setText("Offer from "+listOfOffers.get(position).getUserNameHis());
        }
        holder.tvOfferDateOffered.setText(CommonResources.convertDate(listOfOffers.get(position).getDateOffered()));
        if(listOfOffers.get(position).getStatus() == 0) {
            holder.tvStatus.setText("Pending");
            holder.tvStatus.setTextColor(Color.GRAY);
        }else  if(listOfOffers.get(position).getStatus() == 1){
            holder.tvStatus.setText("Accepted");
            holder.tvStatus.setTextColor(Color.GREEN);
        }else  if(listOfOffers.get(position).getStatus() == 2){
            holder.tvStatus.setText("Rejected");
            holder.tvStatus.setTextColor(Color.RED);
        }else {
            holder.tvStatus.setText("Counter Offered");
            holder.tvStatus.setTextColor(Color.BLUE);
        }

        Image image = new Image();
        image.setImg(holder.ivPrimaryImageOffer1);
        holder.position = position;

        int numOfHisPosts = (listOfOffers.get(position)).getHisSelectedPosts().size();

        String postId1 = (listOfOffers.get(position)).getHisSelectedPosts().get(0);
        String url = CommonResources.getStaticURL()+"uploadedimages/"+postId1+"_1";

        Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer1);

        int numOfMyPosts = (listOfOffers.get(position)).getMySelectedPosts().size();

        String postId6 = (listOfOffers.get(position)).getMySelectedPosts().get(0);
        String urlMine = CommonResources.getStaticURL()+"uploadedimages/"+postId6+"_1";

        Picasso.with(context).load(urlMine).fit().into(holder.ivPrimaryImageOffer6);

        if(numOfHisPosts > 1) {
            holder.ivPrimaryImageOffer2.setVisibility(View.VISIBLE);
            Image image2 = new Image();
            image2.setImg(holder.ivPrimaryImageOffer2);
            holder.position = position;
            String postId2 = (listOfOffers.get(position)).getHisSelectedPosts().get(1);
            url = CommonResources.getStaticURL() + "uploadedimages/" + postId2 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer2);
        }else{
            holder.ivPrimaryImageOffer2.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer3.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer4.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
        }
        if(numOfHisPosts > 2) {
            holder.ivPrimaryImageOffer3.setVisibility(View.VISIBLE);
            Image image3 = new Image();
            image3.setImg(holder.ivPrimaryImageOffer3);
            holder.position = position;
            String postId3 = (listOfOffers.get(position)).getHisSelectedPosts().get(2);
            url = CommonResources.getStaticURL() + "uploadedimages/" + postId3 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer3);
        }else{
            holder.ivPrimaryImageOffer3.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer4.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
        }

        if(numOfHisPosts > 3) {
            holder.ivPrimaryImageOffer4.setVisibility(View.VISIBLE);
            Image image4 = new Image();
            image4.setImg(holder.ivPrimaryImageOffer4);
            holder.position = position;
            String postId4 = (listOfOffers.get(position)).getHisSelectedPosts().get(3);
            url = CommonResources.getStaticURL() + "uploadedimages/" + postId4 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer4);
        }else{
            holder.ivPrimaryImageOffer4.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
        }
        if(numOfHisPosts > 4) {
            holder.ivPrimaryImageOffer5.setVisibility(View.VISIBLE);
            Image image5 = new Image();
            image5.setImg(holder.ivPrimaryImageOffer5);
            holder.position = position;
            String postId5 = (listOfOffers.get(position)).getHisSelectedPosts().get(4);
            url = CommonResources.getStaticURL() + "uploadedimages/" + postId5 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer5);
        }else{

            holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
        }
        if(holder.position == position) {
            if (numOfHisPosts > 5) {
                //Remaining images couldn't be shown: numOfHisPosts-3
                holder.tvExtraPostsCount.setVisibility(View.VISIBLE);
                holder.tvExtraPostsCount.setText("+" + (numOfHisPosts - 5));
            }else{
                holder.tvExtraPostsCount.setVisibility(View.INVISIBLE);
            }
        }



        if(numOfMyPosts > 1) {
            holder.ivPrimaryImageOffer7.setVisibility(View.VISIBLE);
            Image image2 = new Image();
            image2.setImg(holder.ivPrimaryImageOffer7);
            holder.position = position;
            String postId7 = (listOfOffers.get(position)).getMySelectedPosts().get(1);
            url = CommonResources.getStaticURL() + "uploadedimages/" + postId7 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer7);
        }else{
            holder.ivPrimaryImageOffer7.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer8.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer9.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
        }
        if(numOfMyPosts > 2) {
            holder.ivPrimaryImageOffer8.setVisibility(View.VISIBLE);
            Image image3 = new Image();
            image3.setImg(holder.ivPrimaryImageOffer8);
            holder.position = position;
            String postId8 = (listOfOffers.get(position)).getMySelectedPosts().get(2);
            url = CommonResources.getStaticURL() + "uploadedimages/" + postId8 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer8);
        }else{
            holder.ivPrimaryImageOffer8.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer9.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
        }

        if(numOfMyPosts > 3) {
            holder.ivPrimaryImageOffer9.setVisibility(View.VISIBLE);
            Image image4 = new Image();
            image4.setImg(holder.ivPrimaryImageOffer9);
            holder.position = position;
            String postId9 = (listOfOffers.get(position)).getMySelectedPosts().get(3);
            url = CommonResources.getStaticURL() + "uploadedimages/" + postId9 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer9);
        }else{
            holder.ivPrimaryImageOffer9.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
        }
        if(numOfMyPosts > 4) {
            holder.ivPrimaryImageOffer10.setVisibility(View.VISIBLE);
            Image image5 = new Image();
            image5.setImg(holder.ivPrimaryImageOffer10);
            holder.position = position;
            String postId10 = (listOfOffers.get(position)).getMySelectedPosts().get(4);
            url = CommonResources.getStaticURL() + "uploadedimages/" + postId10 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer10);
        }else{

            holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
        }
        if(holder.position == position) {
            if (numOfMyPosts > 5) {
                //Remaining images couldn't be shown: numOfHisPosts-3
                holder.tvExtraPostsCountMine.setVisibility(View.VISIBLE);
                holder.tvExtraPostsCountMine.setText("+" + (numOfMyPosts - 5));
            }else{
                holder.tvExtraPostsCountMine.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemCount() {
        return listOfOffers.size();
    }
    public class Holder extends RecyclerView.ViewHolder
    {
        TextView tvOfferTitle;
        TextView tvOfferDateOffered;
        ImageView ivPrimaryImageOffer1;
        ImageView ivPrimaryImageOffer2;
        ImageView ivPrimaryImageOffer3;
        ImageView ivPrimaryImageOffer4;
        ImageView ivPrimaryImageOffer5;
        ImageView ivPrimaryImageOffer6;
        ImageView ivPrimaryImageOffer7;
        ImageView ivPrimaryImageOffer8;
        ImageView ivPrimaryImageOffer9;
        ImageView ivPrimaryImageOffer10;
        TextView tvExtraPostsCount;
        TextView tvExtraPostsCountMine;
        TextView tvStatus;

        int position;
        CardView cardView;

        public Holder(View rowView, int i) {
            super(rowView);
            tvOfferTitle=(TextView) rowView.findViewById(R.id.tvOfferTitle);
            ivPrimaryImageOffer1=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer1);
            ivPrimaryImageOffer2=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer2);
            ivPrimaryImageOffer3=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer3);
            ivPrimaryImageOffer4=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer4);
            ivPrimaryImageOffer5=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer5);
            tvOfferDateOffered=(TextView) rowView.findViewById(R.id.tvOfferDateOffered);
            tvExtraPostsCount = (TextView) rowView.findViewById(R.id.tvExtraPostsCount);
            tvStatus = (TextView) rowView.findViewById(R.id.tvStatusOfOffer);

            cardView = (CardView)rowView.findViewById(R.id.cvOfferItems);

            ivPrimaryImageOffer6=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer6);
            ivPrimaryImageOffer7=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer7);
            ivPrimaryImageOffer8=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer8);
            ivPrimaryImageOffer9=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer9);
            ivPrimaryImageOffer10=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer10);

            tvExtraPostsCountMine = (TextView) rowView.findViewById(R.id.tvExtraPostsCountMine);
        }
    }

}