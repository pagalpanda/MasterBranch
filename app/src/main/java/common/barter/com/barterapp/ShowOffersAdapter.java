package common.barter.com.barterapp;

import android.content.Context;
import android.graphics.Color;
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

public class ShowOffersAdapter extends BaseAdapter{
    //String [] result;
    Context context;

    ArrayList<Offer> listOfOffers;

    JSONParser jsonParser = new JSONParser();

    private static LayoutInflater inflater=null;

    public ShowOffersAdapter(Context context, ArrayList<Offer> listOfOffers) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.listOfOffers = listOfOffers;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listOfOffers.size();
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
        TextView tvOfferTitle;
        TextView tvOfferDateOffered;
        ImageView ivPrimaryImageOffer1;
        ImageView ivPrimaryImageOffer2;
        ImageView ivPrimaryImageOffer3;
        TextView tvExtraPostsCount;
        TextView tvStatus;

        int position;
    }
    //Holder holder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub

        View rowView = null;

        if(convertView == null) {
            rowView = inflater.inflate(R.layout.list__item_offers, null);
            final Holder holder=new Holder();
            holder.tvOfferTitle=(TextView) rowView.findViewById(R.id.tvOfferTitle);
            holder.ivPrimaryImageOffer1=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer1);
            holder.ivPrimaryImageOffer2=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer2);
            holder.ivPrimaryImageOffer3=(ImageView) rowView.findViewById(R.id.ivPrimaryImageOffer3);
            holder.tvOfferDateOffered=(TextView) rowView.findViewById(R.id.tvOfferDateOffered);
            holder.tvExtraPostsCount = (TextView) rowView.findViewById(R.id.tvExtraPostsCount);
            holder.tvStatus = (TextView) rowView.findViewById(R.id.tvStatusOfOffer);
            rowView.setTag(holder);
        }else {
            rowView=convertView;
        }

        Holder holder = (Holder) rowView.getTag();
        holder.tvOfferTitle.setText( "Offer Id: "+(listOfOffers.get(position).getOfferId()) +  "User Id: "+ listOfOffers.get(position).getUserIdHis());
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
        }
        if(holder.position == position) {
            if (numOfHisPosts > 3) {
                //Remaining images couldn't be shown: numOfHisPosts-3
                holder.tvExtraPostsCount.setVisibility(View.VISIBLE);
                holder.tvExtraPostsCount.setText("+" + (numOfHisPosts - 3));
            }else{
                holder.tvExtraPostsCount.setVisibility(View.GONE);
            }
        }
        return rowView;
    }


}