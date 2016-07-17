package common.barter.com.barterapp.posts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.RecyclerItemClickListener;

/**
 * Created by vikramb on 14/07/16.
 */
public class PostsListener {
    private PostsPresenter postsPresenter;

    public PostsListener(PostsPresenter postsPresenter) {
        this.postsPresenter=postsPresenter;
    }

    @NonNull
    public RecyclerItemClickListener getOnItemTouchListener(Context context) {
        return new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                postsPresenter.onItemClicked(view,position);
            }
        });
    }
}
