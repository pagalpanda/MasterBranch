package common.barter.com.barterapp.showOffer;

import org.json.JSONObject;

import common.barter.com.barterapp.AdapterOnClickListener;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;

/**
 * Created by vikram on 04/06/16.
 */
public class ShowOffersPresenter implements ModelCallBackListener<JSONObject>{
    private ShowOffersFragment showOffersFragment;
    private ShowOffersModel showOffersModel;
    private ShowOfferListener showOfferListener;
    private ShowOffersHolderAdapter showOffersHolderAdapter;

    public ShowOffersPresenter() {

    }

    public ShowOffersFragment getShowOffersFragment() {
        return showOffersFragment;
    }

    public ShowOffersModel getShowOffersModel() {
        if (showOffersModel==null){
            showOffersModel = new ShowOffersModel(this,this.getShowOffersFragment().getContext());
        }
        return showOffersModel;
    }

    public ShowOfferListener getShowOfferListener() {
        if (showOfferListener==null) {
            showOfferListener = new ShowOfferListener(null);
        }
        return showOfferListener;
    }

    public ShowOffersHolderAdapter getShowOffersHolderAdapter() {
        return showOffersHolderAdapter;
    }

    public void setShowOffersHolderAdapter(ShowOffersHolderAdapter showOffersHolderAdapter) {
        this.showOffersHolderAdapter = showOffersHolderAdapter;
    }

    public void setShowOffersFragment(ShowOffersFragment showOffersFragment) {
        this.showOffersFragment = showOffersFragment;
    }

    public AdapterOnClickListener getAdapterOnClickListener(){
        return this.getShowOfferListener().getAdapterOnClickListener() ;
    }

    @Override
    public void onSuccess(JSONObject json) {
        if (json.has(MessagesString.OFFERS)){
            saveAllOffers(json);
        } else if (json.has(MessagesString.SENDER_POSTS) && json.has(MessagesString.RECEIVER_POSTS)){
            saveUserPosts(json);
        }

    }

    private void saveUserPosts(JSONObject json) {
//        myListOfPostsInOffer = new ArrayList<>();
//        hisListOfPostsInOffer = new ArrayList<>();
//        try {
//            myposts = json.getJSONArray("myposts");
//            if(myposts.length() == 0) return "empty";
//            for (int i = 0; i < myposts.length(); i++) {
//                JSONObject c = myposts.getJSONObject(i);
//                myListOfPostsInOffer.add(new Post(c.getString("uniqueid"), c.getString("title"), c.getString("createddate"), c.getString("locality"), c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city"), "null".equalsIgnoreCase(c.getString("isaddedtowishlist"))?false:true));
//            }
//
//            hisposts = json.getJSONArray("hisposts");
//            if(hisposts.length() == 0) return "empty";
//            for (int i = 0; i < hisposts.length(); i++) {
//                JSONObject c = hisposts.getJSONObject(i);
//                hisListOfPostsInOffer.add(new Post(c.getString("uniqueid"), c.getString("title"), c.getString("createddate"), c.getString("locality"), c.getString("hasimage"), c.getString("postid"), c.getString("numofimages"), c.getString("description"), c.getString("subcategory"), c.getString("category"), c.getString("city"), "null".equalsIgnoreCase(c.getString("isaddedtowishlist"))?false:true));
//            }
//            navigateToReview();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void saveAllOffers(JSONObject json) {
//        JSONArray offers = null;
//        try {
//            offers = json.getJSONArray("offers");
//            if(offers.length() == 0) {
//                this.onFailure(MessagesString.NO_POSTS_IN_CITY_AND_CATEGORY);
//                return ;
//            }
//
//            for (int i = 0; i < offers.length(); i++) {
//                JSONObject c = offers.getJSONObject(i);
//                JSONArray myOffers = c.getJSONArray("myoffers");
//                JSONArray hisOffers = c.getJSONArray("hisoffers");
//
//                for (int j = 0; j < myOffers.length(); j++) {
//                    JSONObject myOffer = myOffers.getJSONObject(j);
//                    ArrayList<String> listOfMyPostsInOffer = new ArrayList<String>();
//                    ArrayList<String> listOfHisPostsInOffer = new ArrayList<String>();
//                    String offerID = myOffer.getString("offerid");
//                    String createdDate = myOffer.getString("createdate");
//                    String receiverId = myOffer.getString("receiverid");
//                    int status = myOffer.getInt("status");
//                    int isDeleted = myOffer.getInt("isdeleted");
//                    String lastUpdateDate = myOffer.getString("lastupdatedate");
//                    String hisUserName = myOffer.getString("receivername");
//                    JSONArray myPosts = myOffer.getJSONArray("myposts");
//                    if (isDeleted == 0) {
//                        for (int k = 0; k < myPosts.length(); k++) {
//                            listOfMyPostsInOffer.add(myPosts.getString(k));
//
//                        }
//
//                        JSONArray hisPosts = myOffer.getJSONArray("hisposts");
//                        for (int k = 0; k < hisPosts.length(); k++) {
//                            listOfHisPostsInOffer.add(hisPosts.getString(k));
//                        }
//
//                        listOfMyOffers.add(new Offer(offerID, listOfMyPostsInOffer, listOfHisPostsInOffer, receiverId, hisUserName, createdDate, lastUpdateDate, status));
//                        listOfOffers = listOfMyOffers;
//                    }
//                }
//
//                for (int j = 0; j < hisOffers.length(); j++) {
//                    JSONObject hisOffer = hisOffers.getJSONObject(j);
//                    String offerID = hisOffer.getString("offerid");
//                    String createdDate = hisOffer.getString("createdate");
//                    String senderId = hisOffer.getString("senderid");
//                    int status = hisOffer.getInt("status");
//                    String lastUpdateDate = hisOffer.getString("lastupdatedate");
//                    String hisUserName = hisOffer.getString("sendername");
//                    JSONArray myPosts = hisOffer.getJSONArray("myposts");
//                    ArrayList<String> listOfMyPostsInOffer = new ArrayList<String>();
//                    ArrayList<String> listOfHisPostsInOffer = new ArrayList<String>();
//                    for (int k = 0; k < myPosts.length(); k++) {
//                        listOfMyPostsInOffer.add(myPosts.getString(k));
//
//                    }
//
//                    JSONArray hisPosts = hisOffer.getJSONArray("hisposts");
//                    for (int k = 0; k < hisPosts.length(); k++) {
//                        listOfHisPostsInOffer.add(hisPosts.getString(k));
//                    }
//
//                    listOfHisOffers.add(new Offer(offerID, listOfMyPostsInOffer, listOfHisPostsInOffer, senderId, hisUserName, createdDate, lastUpdateDate, status));
//                }
//                setConfigafterReceivingOffers();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
    public void setConfigafterReceivingOffers(){
//        viewPager.destroyDrawingCache();
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String errorMessage) {
        setConfigafterReceivingOffers();
    }

    public void onAdapterClicked(int position) {
        showOffersFragment.getUserPosts(position);
    }
    private void navigateToReview() {
//
//        ArrayList<Post> listSelectedMine = new ArrayList<>();
//        ArrayList<Post> listSelectedHis = new ArrayList<>();
//        for(Post post : myListOfPostsInOffer){
//
//            if( (listOfOffers.get(selectedPosition).getMySelectedPosts()).contains(post.getPostId()) ) {
//                post.setIsSelected(true);
//                listSelectedMine.add(post);
//            }
//        }
//        for(Post post : hisListOfPostsInOffer){
//            if( (listOfOffers.get(selectedPosition).getHisSelectedPosts() ).contains(post.getPostId()) ) {
//                post.setIsSelected(true);
//                listSelectedHis.add(post);
//            }
//        }
//
//
//        Fragment fragment = null;
//        fragment = new ReviewFragment(listSelectedMine,listSelectedHis,"view",myListOfPostsInOffer,hisListOfPostsInOffer, status, dateUpdated, currentOfferId);
//
//
//        if (fragment != null) {
//            getFragmentManager().beginTransaction().add(R.id.frame_container, fragment).addToBackStack("post_details").commit();
//        } else {
//            Log.e("MainActivity", "Error in creating fragment");
//        }
    }

}
