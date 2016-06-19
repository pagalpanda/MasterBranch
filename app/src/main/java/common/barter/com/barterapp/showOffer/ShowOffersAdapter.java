package common.barter.com.barterapp.showOffer;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import common.barter.com.barterapp.AdapterOnClickListener;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.Image;
import common.barter.com.barterapp.JSONParser;
import common.barter.com.barterapp.Offer;
import common.barter.com.barterapp.posts.PostDetailsDialogOffer;
import common.barter.com.barterapp.R;

public class ShowOffersAdapter extends RecyclerView.Adapter<Holder>{
    private Context context;
    private String mode;
    private Holder holder;
    private ArrayList<Offer> listOfOffers;
    private AdapterOnClickListener listener;
    private static final int TYPE_FEW_ITEMS = 0;
    private static final int TYPE_ITEMS = 1;

    private static LayoutInflater inflater=null;

    public ShowOffersAdapter(Context context, ArrayList<Offer> listOfOffers, String mode, AdapterOnClickListener listener) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.listOfOffers = listOfOffers;
        this.mode = mode;
        this.listener = listener;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if ((listOfOffers.get(position)).getMySelectedPosts().size() + (listOfOffers.get(position)).getHisSelectedPosts().size() > 4)
            return TYPE_ITEMS;

        return TYPE_FEW_ITEMS;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(viewType == TYPE_ITEMS){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list__item_offers, parent, false);
        }else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list__item_offers_few_items, parent, false);
        }
        Holder viewHolder = new Holder(v,viewType);

        return viewHolder;

    }


    @Override
    public void onBindViewHolder(Holder holder,final int position) {
        this.holder = holder;

        int numOfMyPosts = (listOfOffers.get(position)).getMySelectedPosts().size();
        int numOfHisPosts = (listOfOffers.get(position)).getHisSelectedPosts().size();

        holder.cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });

        if(numOfHisPosts+numOfMyPosts >4) {
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


            final String postId1 = (listOfOffers.get(position)).getHisSelectedPosts().get(0);
            String url = CommonResources.getStaticURL() + "uploadedimages/" + postId1 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer1);
            holder.ivPrimaryImageOffer1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId1);
                    dialog.show();
                }
            });

            final String postId6 = (listOfOffers.get(position)).getMySelectedPosts().get(0);
            String urlMine = CommonResources.getStaticURL() + "uploadedimages/" + postId6 + "_1";
            Picasso.with(context).load(urlMine).fit().into(holder.ivPrimaryImageOffer6);

            holder.ivPrimaryImageOffer6.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId6);
                    dialog.show();
                }
            });



            if (numOfHisPosts > 1) {
                holder.ivPrimaryImageOffer2.setVisibility(View.VISIBLE);
                Image image2 = new Image();
                image2.setImg(holder.ivPrimaryImageOffer2);
                holder.position = position;
                final String postId2 = (listOfOffers.get(position)).getHisSelectedPosts().get(1);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId2 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer2);
                holder.ivPrimaryImageOffer2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId2);
                        dialog.show();
                    }
                });
            } else {
                holder.ivPrimaryImageOffer2.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer3.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer4.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
            }
            if (numOfHisPosts > 2) {
                holder.ivPrimaryImageOffer3.setVisibility(View.VISIBLE);
                Image image3 = new Image();
                image3.setImg(holder.ivPrimaryImageOffer3);
                holder.position = position;
                final String postId3 = (listOfOffers.get(position)).getHisSelectedPosts().get(2);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId3 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer3);

                holder.ivPrimaryImageOffer3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId3);
                        dialog.show();
                    }
                });
            } else {
                holder.ivPrimaryImageOffer3.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer4.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
            }

            if (numOfHisPosts > 3) {
                holder.ivPrimaryImageOffer4.setVisibility(View.VISIBLE);
                Image image4 = new Image();
                image4.setImg(holder.ivPrimaryImageOffer4);
                holder.position = position;
                final String postId4 = (listOfOffers.get(position)).getHisSelectedPosts().get(3);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId4 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer4);

                holder.ivPrimaryImageOffer4.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId4);
                        dialog.show();
                    }
                });
            } else {
                holder.ivPrimaryImageOffer4.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
            }
            if (numOfHisPosts > 4) {
                holder.ivPrimaryImageOffer5.setVisibility(View.VISIBLE);
                Image image5 = new Image();
                image5.setImg(holder.ivPrimaryImageOffer5);
                holder.position = position;
                final String postId5 = (listOfOffers.get(position)).getHisSelectedPosts().get(4);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId5 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer5);

                holder.ivPrimaryImageOffer5.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId5);
                        dialog.show();
                    }
                });
            } else {

                holder.ivPrimaryImageOffer5.setVisibility(View.INVISIBLE);
            }
            if (holder.position == position) {
                if (numOfHisPosts > 5) {
                    //Remaining images couldn't be shown: numOfHisPosts-3
                    holder.tvExtraPostsCount.setVisibility(View.VISIBLE);
                    holder.tvExtraPostsCount.setText("+" + (numOfHisPosts - 5));
                } else {
                    holder.tvExtraPostsCount.setVisibility(View.INVISIBLE);
                }
            }


            if (numOfMyPosts > 1) {
                holder.ivPrimaryImageOffer7.setVisibility(View.VISIBLE);
                Image image2 = new Image();
                image2.setImg(holder.ivPrimaryImageOffer7);
                holder.position = position;
                final String postId7 = (listOfOffers.get(position)).getMySelectedPosts().get(1);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId7 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer7);
                holder.ivPrimaryImageOffer7.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId7);
                        dialog.show();
                    }
                });
            } else {
                holder.ivPrimaryImageOffer7.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer8.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer9.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
            }
            if (numOfMyPosts > 2) {
                holder.ivPrimaryImageOffer8.setVisibility(View.VISIBLE);
                Image image3 = new Image();
                image3.setImg(holder.ivPrimaryImageOffer8);
                holder.position = position;
                final String postId8 = (listOfOffers.get(position)).getMySelectedPosts().get(2);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId8 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer8);

                holder.ivPrimaryImageOffer8.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId8);
                        dialog.show();
                    }
                });
            } else {
                holder.ivPrimaryImageOffer8.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer9.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
            }

            if (numOfMyPosts > 3) {
                holder.ivPrimaryImageOffer9.setVisibility(View.VISIBLE);
                Image image4 = new Image();
                image4.setImg(holder.ivPrimaryImageOffer9);
                holder.position = position;
                final String postId9 = (listOfOffers.get(position)).getMySelectedPosts().get(3);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId9 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer9);

                holder.ivPrimaryImageOffer9.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId9);
                        dialog.show();
                    }
                });
            } else {
                holder.ivPrimaryImageOffer9.setVisibility(View.INVISIBLE);
                holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
            }
            if (numOfMyPosts > 4) {
                holder.ivPrimaryImageOffer10.setVisibility(View.VISIBLE);
                Image image5 = new Image();
                image5.setImg(holder.ivPrimaryImageOffer10);
                holder.position = position;
                final String postId10 = (listOfOffers.get(position)).getMySelectedPosts().get(4);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId10 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOffer10);
                holder.ivPrimaryImageOffer10.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId10);
                        dialog.show();
                    }
                });
            } else {

                holder.ivPrimaryImageOffer10.setVisibility(View.INVISIBLE);
            }
            if (holder.position == position) {
                if (numOfMyPosts > 5) {
                    //Remaining images couldn't be shown: numOfHisPosts-3
                    holder.tvExtraPostsCountMine.setVisibility(View.VISIBLE);
                    holder.tvExtraPostsCountMine.setText("+" + (numOfMyPosts - 5));
                } else {
                    holder.tvExtraPostsCountMine.setVisibility(View.INVISIBLE);
                }
            }
        }else{
            if("myoffers".equalsIgnoreCase(mode)) {
                holder.tvOfferTitleFew.setText("Offer to " + listOfOffers.get(position).getUserNameHis());
            }else{
                holder.tvOfferTitleFew.setText("Offer from "+listOfOffers.get(position).getUserNameHis());
            }
            holder.tvOfferDateOfferedFew.setText(CommonResources.convertDate(listOfOffers.get(position).getDateOffered()));
            if(listOfOffers.get(position).getStatus() == 0) {
                holder.tvStatusOfOfferFew.setText("Pending");
                holder.tvStatusOfOfferFew.setTextColor(Color.GRAY);
            }else  if(listOfOffers.get(position).getStatus() == 1){
                holder.tvStatusOfOfferFew.setText("Accepted");
                holder.tvStatusOfOfferFew.setTextColor(Color.GREEN);
            }else  if(listOfOffers.get(position).getStatus() == 2){
                holder.tvStatusOfOfferFew.setText("Rejected");
                holder.tvStatusOfOfferFew.setTextColor(Color.RED);
            }else {
                holder.tvStatusOfOfferFew.setText("Counter Offered");
                holder.tvStatusOfOfferFew.setTextColor(Color.BLUE);
            }

            holder.ivPrimaryImageOfferFew2.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOfferFew3.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOfferFew5.setVisibility(View.INVISIBLE);
            holder.ivPrimaryImageOfferFew6.setVisibility(View.INVISIBLE);

            final String postId1 = (listOfOffers.get(position)).getHisSelectedPosts().get(0);
            String url = CommonResources.getStaticURL() + "uploadedimages/" + postId1 + "_1";

            Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOfferFew1);
            holder.ivPrimaryImageOfferFew1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId1);
                    dialog.show();
                }
            });

            final String postId6 = (listOfOffers.get(position)).getMySelectedPosts().get(0);
            String urlMine = CommonResources.getStaticURL() + "uploadedimages/" + postId6 + "_1";

            Picasso.with(context).load(urlMine).fit().into(holder.ivPrimaryImageOfferFew4);
            holder.ivPrimaryImageOfferFew4.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId6);
                    dialog.show();
                }
            });


            if (numOfHisPosts > 1) {
                holder.ivPrimaryImageOfferFew2.setVisibility(View.VISIBLE);
                Image image2 = new Image();
                image2.setImg(holder.ivPrimaryImageOfferFew2);
                holder.position = position;
                final String postId2 = (listOfOffers.get(position)).getHisSelectedPosts().get(1);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId2 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOfferFew2);
                holder.ivPrimaryImageOfferFew2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId2);
                        dialog.show();
                    }
                });
            } else {
                holder.ivPrimaryImageOfferFew3.setVisibility(View.INVISIBLE);

            }
            if (numOfHisPosts > 2) {
                holder.ivPrimaryImageOfferFew3.setVisibility(View.VISIBLE);
                Image image3 = new Image();
                image3.setImg(holder.ivPrimaryImageOfferFew3);
                holder.position = position;
                final String postId3 = (listOfOffers.get(position)).getHisSelectedPosts().get(2);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId3 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOfferFew3);
                holder.ivPrimaryImageOfferFew3.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId3);
                        dialog.show();
                    }
                });
            }

            if (numOfMyPosts > 1) {
                holder.ivPrimaryImageOfferFew5.setVisibility(View.VISIBLE);
                Image image2 = new Image();
                image2.setImg(holder.ivPrimaryImageOfferFew5);
                holder.position = position;
                final String postId2 = (listOfOffers.get(position)).getMySelectedPosts().get(1);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId2 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOfferFew5);
                holder.ivPrimaryImageOfferFew5.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId2);
                        dialog.show();
                    }
                });
            } else {
                holder.ivPrimaryImageOfferFew6.setVisibility(View.INVISIBLE);

            }
            if (numOfMyPosts > 2) {
                holder.ivPrimaryImageOfferFew6.setVisibility(View.VISIBLE);
                Image image3 = new Image();
                image3.setImg(holder.ivPrimaryImageOfferFew6);
                holder.position = position;
                final String postId3 = (listOfOffers.get(position)).getMySelectedPosts().get(2);
                url = CommonResources.getStaticURL() + "uploadedimages/" + postId3 + "_1";

                Picasso.with(context).load(url).fit().into(holder.ivPrimaryImageOfferFew6);
                holder.ivPrimaryImageOfferFew6.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final PostDetailsDialogOffer dialog = new PostDetailsDialogOffer(context, postId3);
                        dialog.show();
                    }
                });
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
}