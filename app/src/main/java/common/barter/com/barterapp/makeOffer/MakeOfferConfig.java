package common.barter.com.barterapp.makeOffer;

import common.barter.com.barterapp.MessagesString;

/**
 * Created by vikram on 09/07/16.
 */
public class MakeOfferConfig {

    private enum tabs{
        MY_POSTS(0, MessagesString.MY_POSTS),
        HIS_POSTS(1,MessagesString.HIS_POSTS);

        private final int tabId;
        private final String tabName;

        public String getName(){
            return this.tabName;
        }
        tabs(int tabId, String tabName) {
            this.tabId = tabId;
            this.tabName = tabName;
        }
    }


}
