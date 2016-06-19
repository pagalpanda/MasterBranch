package common.barter.com.barterapp.posts;

/**
 * Created by amitpa on 8/23/2015.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import common.barter.com.barterapp.AsyncConnection;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.R;


public class PostDetailsDialogOffer extends Dialog{


    private String postId;
    private ImageView ivPostPrimary;
    private PostDetailsDialogOffer thisDialog;
    private TextView tvTitle;
    Context context;

    public PostDetailsDialogOffer(Context context, String postId) {
        super(context);
        // TODO Auto-generated constructor stub

        this.thisDialog = this;
        this.postId = postId;
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        thisDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);// Hides the are for title bar
        setContentView(R.layout.dialog_post_detail);

//        getWindow().setLayout(android.view.ViewGroup.LayoutParams.FILL_PARENT,
//                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        ivPostPrimary = (ImageView)findViewById(R.id.ivPostDetailOffer);
        tvTitle = (TextView)findViewById(R.id.titlePostTitleOffer);

        String urlMine = CommonResources.getStaticURL() + "uploadedimages/" + postId + "_1";

        Picasso.with(context).load(urlMine).fit().into(ivPostPrimary);


        HashMap<String, String> params = new HashMap<String,String>();
        params.put("postid", postId);
        final AsyncConnection as = new AsyncConnection(context, CommonResources.getURL("get_post_title"),"POST",params, false, null){
            @Override
            public void receiveData(JSONObject json) {
                if(json != null){

                    try {
                        tvTitle.setText(json.getString("posttitle"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        as.execute();

        thisDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                as.cancel(true);
            }
        });
    }



    }

