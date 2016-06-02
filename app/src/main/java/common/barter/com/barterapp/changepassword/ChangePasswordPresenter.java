package common.barter.com.barterapp.changepassword;


import java.util.HashMap;
import common.barter.com.barterapp.AbstractFragment;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.NetworkCallListener;

/**
 * Created by Panda on 19-05-2016.
 */
public class ChangePasswordPresenter implements NetworkCallListener{

    private AbstractFragment view;
    private ChangePasswordModel model;


    void onLoadView(AbstractFragment view) {
        this.view = view;
        view.setHamburgerIndicator(false);
        view.setActionBarTitle(MessagesString.HEADER_CHANGE_PASSWORD);
        view.setHasOptionsMenu(true);
    }

    void onSavePasswordClick(String existingPassword, String newPassord, String confirmNewPassword) {
        view.hideKeybaord();
        if (isValidInput(existingPassword, newPassord, confirmNewPassword))
        {
            if(isNewPassDifferent(existingPassword, newPassord)) {
                savePassword(existingPassword, newPassord);
            } else {
                CommonUtil.flash(view.getContext(), MessagesString.NEW_PASSWORD_CAN_T_BE_SAME_AS_THE_OLD_ONE);
            }

        }
    }
    private Boolean isValidInput(String existingPassword, String newPassord, String confirmNewPassword) {

        if ( isValidPassword(existingPassword) && isValidPassword(newPassord) && isValidPassword(confirmNewPassword)
                && isValidConfirmPassword(newPassord,confirmNewPassword) )
        {
            return true;
        }
        return false;
    }

    private boolean isValidPassword(String password) {

        if (password != null && !password.equals("") && password.length() > 7) {
            return true;
        }

        if (password.length() < 8)
        {
            CommonUtil.flash(view.getContext(), MessagesString.PASSWORD_SHOULD_BE_8_CHARACTERS_LONG);
        }
        else if (password == null || password.equals(""))
        {
            CommonUtil.flash(view.getContext(), MessagesString.PASSWORD_SHOULD_NOT_BE_BLANK);
        }
        else
        {
            CommonUtil.flash(view.getContext(), MessagesString.INVALID_PASSWORD);
        }
        return false;
    }

    private boolean isValidConfirmPassword(String newPass, String confirmNewPass) {


        if ( newPass.equals(confirmNewPass))
        {
            return true;
        }
        CommonUtil.flash(view.getContext(), MessagesString.NEW_PASSWORDS_DONT_MATCH);
        return false;
    }

    private boolean isNewPassDifferent(String currentPassword, String newPassword) {
        return !currentPassword.equals(newPassword);
    }

    private void savePassword(String existingPassword, String newPassord)
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userid", LoginDetails.getInstance().getUserid());
        params.put("newpassword", newPassord);
        params.put("oldpassword", existingPassword);
        params.put("instruction", "3");

        model = new ChangePasswordModel(this);
        model.savePassword(view.getContext(), params);

    }


    @Override
    public void onNetworkCallSuccess(int returnCode) {

        if (returnCode == 0) {
            CommonUtil.flash(view.getContext(), MessagesString.PASSWORD_UPDATED) ;
            view.navigate();
        }
        else if (returnCode == 11) {
            CommonUtil.flash(view.getContext(), MessagesString.TRY_AGAIN_LATER_MESSAGE);
        }
        else if(returnCode == 12)
        {
            CommonUtil.flash(view.getContext(), MessagesString.PLEASE_ENTER_CORRECT_PASSWORD);
        }else{
            onNetworkCallFailure();
        }

    }

    @Override
    public void onNetworkCallFailure() {
        CommonUtil.flash(view.getContext(),MessagesString.TRY_AGAIN_LATER_MESSAGE);
    }
}
