package common.barter.com.barterapp;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import common.barter.com.barterapp.globalhome.GlobalHome;

public class NavDrawerListAdapter extends RecyclerView.Adapter<NavDrawerListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;
    String location;


    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems, String location){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        this.location = location;


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        TextView tvTitle;
        TextView tvCounter;
        TextView tvLocationName;
        ImageView ivIcon;



        public ViewHolder(View itemView,int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);


            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if(ViewType == TYPE_ITEM) {
                tvTitle = (TextView) itemView.findViewById(R.id.title);
                tvCounter = (TextView)itemView.findViewById(R.id.counter);
                ivIcon = (ImageView)itemView.findViewById(R.id.ivNavBarIcon);
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else{
                tvLocationName = (TextView) itemView.findViewById(R.id.name);
                ivIcon = (ImageView)itemView.findViewById(R.id.imageViewLocation);// Creating Text View object from header.xml for name
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
        }


    }


    //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder

    @Override
    public NavDrawerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item,parent,false); //Inflating the layout

            ViewHolder vhItem = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view


            return vhItem; // Returning the created object

            //inflate your layout and pass it to view holder

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_location_panel,parent,false); //Inflating the layout

            ViewHolder vhHeader = new ViewHolder(v,viewType); //Creating ViewHolder and passing the object of type view

            return vhHeader; //returning the object created


        }

        return null;

    }

    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(NavDrawerListAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.tvTitle.setText(navDrawerItems.get(position-1).getTitle().toString());
            holder.tvCounter.setText(navDrawerItems.get(position-1).getCount().toString());
            if(position == 1){
                holder.ivIcon.setImageResource(R.drawable.home);
            }
            if(position == 2){
                holder.ivIcon.setImageResource(R.drawable.myposts);
            }
            if(position == 3){
                holder.ivIcon.setImageResource(R.drawable.notify);
            }
            if(position == 4){
                holder.ivIcon.setImageResource(R.drawable.fav);
            }
            if(position == 5){
                holder.ivIcon.setImageResource(R.drawable.profile);
            }
            if(position == 6){
                holder.ivIcon.setImageResource(R.drawable.support);
            }

        }
        else{
            //holder.itemView.setSelected(focusedItem == position);
                       // Similarly we set the resources for header view

            holder.tvLocationName.setText(GlobalHome.location);
            holder.ivIcon.setImageResource(R.drawable.location);

        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return navDrawerItems.size()+1; // the number of items in the list will be +1 the titles including the header view.
    }


    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }



}
