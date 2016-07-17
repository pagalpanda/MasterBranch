package common.barter.com.barterapp.makeOffer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import common.barter.com.barterapp.JSONParser;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.data.domain.NewPost;
import common.barter.com.barterapp.reviewOffer.NewPostListOfferHolder;
import common.barter.com.barterapp.reviewOffer.NewPostListOfferListener;

/**
 * Created by vikramb on 14/07/16.
 */
public class NewPostListOfferAdapter extends RecyclerView.Adapter<NewPostListOfferHolder>{
    Context context;
    ArrayList<NewPost> listOfPosts;
    ArrayList<Long> selectedPosts;
    private static LayoutInflater inflater=null;
    boolean isListMine;
    String mode;
    private NewPostListOfferListener postListOfferListener;

    public NewPostListOfferAdapter(Context context, ArrayList<NewPost> listOfPosts,ArrayList<Long> selectedPosts) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.listOfPosts = listOfPosts;
        this.selectedPosts = selectedPosts;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public NewPostListOfferAdapter(Context context, ArrayList<NewPost> listOfPosts,ArrayList<Long> selectedPosts, boolean isListMine) {
        // TODO Auto-generated constructor stub
        this(context,listOfPosts,selectedPosts);
        this.context=context;
        this.listOfPosts = listOfPosts;
        this.isListMine = isListMine;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public NewPostListOfferAdapter(Context context, ArrayList<NewPost> listOfPosts,ArrayList<Long> selectedPosts, String mode) {
        // TODO Auto-generated constructor stub
        this(context,listOfPosts,selectedPosts);
        this.mode=mode;
    }
    public NewPostListOfferListener getListener(){
        if (postListOfferListener==null){
            postListOfferListener= new NewPostListOfferListener(this);
        }
        return postListOfferListener;
    }

    @Override
    public NewPostListOfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_post_items_offer, parent, false);
        NewPostListOfferHolder viewHolder = new NewPostListOfferHolder(v,viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewPostListOfferHolder newPostListOfferHolder, final int position) {
        NewPost post = listOfPosts.get(position);
        setTitleLocalityAndDate(newPostListOfferHolder, post);
        setPrimaryImage(newPostListOfferHolder, position);

        if(!"review".equalsIgnoreCase(mode)) {
            setOnCheckedChangeListener(newPostListOfferHolder, position);
            setIsSelected(newPostListOfferHolder, position);
        }else {
            //rowView.setBackgroundColor(Color.WHITE);
            //newPostListOfferHolder.cbSelected.setVisibility(View.GONE);
        }
    }

    public void setIsSelected(NewPostListOfferHolder newPostListOfferHolder, int position) {
        if (selectedPosts.contains(listOfPosts.get(position).getId())) {
            this.setCbSelected(newPostListOfferHolder, true);
        } else {
            this.setCbSelected(newPostListOfferHolder, false);
        }
    }

    public void setOnCheckedChangeListener(NewPostListOfferHolder newPostListOfferHolder, int position) {
        newPostListOfferHolder.setOnCheckedChangeListener(this.getListener().getOnCheckedChangeListener(position));
    }

    public void setCbSelected(NewPostListOfferHolder newPostListOfferHolder,Boolean flag) {
        newPostListOfferHolder.setIsSelected(flag);
    }
    public void setTitleLocalityAndDate(NewPostListOfferHolder newPostListOfferHolder, NewPost post) {
        newPostListOfferHolder.setTitle(post.getTitle());
        newPostListOfferHolder.setLocality(post.getLocality());
        newPostListOfferHolder.setCreateDate(post.getCreateDate());
    }
    public void setPrimaryImage(NewPostListOfferHolder newPostListOfferHolder, int position) {
        newPostListOfferHolder.setPrimaryImage(context, listOfPosts.get(position),position);
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


    public void onCheckedChanged(int position, boolean isChecked) {
        if (isChecked) {
            selectedPosts.add(listOfPosts.get(position).getId());
        } else {
            selectedPosts.remove(listOfPosts.get(position).getId());
        }
    }
}
