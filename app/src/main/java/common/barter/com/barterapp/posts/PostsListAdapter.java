package common.barter.com.barterapp.posts;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.data.domain.NewPost;

public class PostsListAdapter extends RecyclerView.Adapter<PostListHolder>{
    private Context context;
    private ArrayList<NewPost> listOfPosts;
    private long postId;
    private static LayoutInflater inflater=null;

    public PostsListAdapter(Context context, ArrayList<NewPost> listOfPosts) {
        this.context=context;
        this.listOfPosts = listOfPosts;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PostListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_posts_item, parent, false);
        PostListHolder viewHolder = new PostListHolder(v,viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostListHolder holder, int position) {
        NewPost post=listOfPosts.get(position);
        setTitleDateAndLocality(holder, post);
        setPrimaryImage(holder,post, position);
    }

    public void setTitleDateAndLocality(PostListHolder holder, NewPost post) {
        holder.setTitle(post.getTitle());
        holder.setLocality(post.getLocality());
        holder.setCreateDate(CommonResources.convertDate(post.getCreateDate()));
    }

    public void setPrimaryImage(PostListHolder holder, NewPost post, int position) {
        holder.setPrimaryImage(context,post,position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
       return listOfPosts.size();
    }

}