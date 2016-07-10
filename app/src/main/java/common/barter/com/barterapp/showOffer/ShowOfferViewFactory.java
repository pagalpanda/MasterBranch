package common.barter.com.barterapp.showOffer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by vikram on 23/06/16.
 */
public class ShowOfferViewFactory {
    @NonNull
    private ShowOfferViewCreator showOfferViewCreator;

    public ShowOfferViewFactory(ShowOfferViewCreator showOfferViewCreator) {
        this.showOfferViewCreator = showOfferViewCreator;
    }

    public ShowOfferViewCreator getShowOfferViewCreator() {
        return showOfferViewCreator;
    }

    public ShowOfferView getBigView(@NonNull View view,int numOfPosts,int viewType){
        RecyclerView recyclerView=this.getShowOfferViewCreator().getRecyclerView();
        ShowOfferRelativeLayout relativeLayout =this.getShowOfferViewCreator().getRelativeLayout(view);
        ImageView[] imageViews = new ImageView[numOfPosts];
        for(int i=0;i<numOfPosts;i++){
            imageViews[i]=this.getShowOfferViewCreator().getImageViewObject();
        }
        return new ShowOfferBigView(recyclerView,viewType,relativeLayout,imageViews);
    }

    public ShowOfferView getSmallView(@NonNull View view,int numOfPosts,int viewType){
        RecyclerView recyclerView=this.getShowOfferViewCreator().getRecyclerView();
        ShowOfferRelativeLayout showOfferRelativeLayout =this.getShowOfferViewCreator().getRelativeLayout(view);
        ImageButton[] imageButtons = new ImageButton[numOfPosts];
        for(int i=0;i<numOfPosts;i++){
            imageButtons[i]=this.getShowOfferViewCreator().getImageButtonObject();
        }
        return new ShowOfferSmallView(recyclerView,viewType,showOfferRelativeLayout,imageButtons);
    }
    public ShowOfferView getInitialView(@NonNull View view,int viewType){
        ShowOfferRelativeLayout showOfferRelativeLayout =this.getShowOfferViewCreator().getRelativeLayout(view);
        return new ShowOfferSmallView(view,viewType,showOfferRelativeLayout,null);
    }
    public ImageButton[] getImagesButtonArray(int numOfPosts){
        ImageButton[] imageButtons = new ImageButton[numOfPosts];
        for(int i=0;i<numOfPosts;i++){
            imageButtons[i]=this.getShowOfferViewCreator().getImageButtonObject();
        }
        return imageButtons;
    }

}
