package common.barter.com.barterapp.forgotpassword;

import java.util.HashMap;

import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.NetworkCallListener;

/**
 * Created by Panda on 17-05-2016.
 */
public class ForgotPasswordPresenter implements NetworkCallListener{
    ForgotPassword view;
    ForgotPasswordModel model;
    String emailId;
    public void onLoadView(ForgotPassword view) {
        this.view = view;
        view.setHamburgerIndicator(false);
        view.setActionBarTitle(MessagesString.HEADER_FORGOT_PASSWORD);
    }

    protected void onSendPassword(String emailId) {
        view.hideKeybaord();
        if (isValidInput(emailId)) {
            sendPwd(emailId);
        } else {
            CommonUtil.flash(view.getContext(),MessagesString.INVALID_EMAIL);
        }
    }

    public void sendPwd(final String emailId)
    {
        this.emailId = emailId;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("sendTo",emailId);
        params.put("instruction", "0");
        model = new ForgotPasswordModel(this);
        model.sendPassword(view.getContext(),params);
    }


    private boolean isValidInput(String emailId) {
        return  CommonResources.isValidEmail(emailId);
    }


    @Override
    public void onNetworkCallSuccess(int returnCode) {
        if (returnCode == 0) {
            CommonUtil.flash(view.getContext(),MessagesString.RESET_LINK_SENT_MESSAGE.concat(emailId));

            view.navigateToLogin();
        }
        else if (returnCode == 1) {
            CommonUtil.flash(view.getContext(),MessagesString.TRY_AGAIN_LATER_MESSAGE);

        }
        else if (returnCode == 2) {
            CommonUtil.flash(view.getContext(),MessagesString.EMAIL_ID_NOT_REGISTERED_MESSAGE);
        }
        else {
            CommonUtil.flash(view.getContext(),MessagesString.TRY_AGAIN_LATER_MESSAGE);

        }

    }

    @Override
    public void onNetworkCallFailure() {

    }
}
