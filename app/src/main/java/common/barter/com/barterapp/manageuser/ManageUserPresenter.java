package common.barter.com.barterapp.manageuser;

import android.content.Context;
import android.view.View;
import org.json.JSONObject;
import java.util.HashMap;
import common.barter.com.barterapp.CommonResources;
import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.ModelCallBackListener;


/**
 * Created by vikram on 04/06/16.
 */
public class ManageUserPresenter  implements ModelCallBackListener<JSONObject>{
    private ManageUserFragment manageUserfragment;
    private ManageUserListener manageUserListener;
    private ManageUserModel manageUserModel;

    public ManageUserPresenter() {
    }

    public ManageUserListener getManageUserListener() {
        if (manageUserListener==null){
            manageUserListener=new ManageUserListener(this);
        }
        return manageUserListener;
    }

    public ManageUserModel getManageUserModel() {
        if (manageUserModel==null){
            manageUserModel=new ManageUserModel(this,this.getContext());
        }
        return manageUserModel;
    }

    public ManageUserFragment getManageUserfragment() {
        return manageUserfragment;
    }

    public void setManageUserfragment(ManageUserFragment manageUserfragment) {
        this.manageUserfragment = manageUserfragment;
    }

    private Context getContext() {
        return manageUserfragment.getContext();
    }

    public void onSaveClicked() {
        if(!(this.validateInput())) {
            CommonUtil.flash(this.getContext(), MessagesString.WRONG_DATA_ENTERED);
            return;
        }
        if(!(this.hasDetailsChanged())){
            CommonUtil.flash(this.getContext(), MessagesString.NO_CHANGES_MADE);
        }
        this.saveData();
    }

    private boolean hasMobileNumChanged() {
        String newPhoneEntered = this.getManageUserfragment().getMobileNum();
        if(!newPhoneEntered.equalsIgnoreCase(LoginDetails.getInstance().getMobilenum())) {
            return true;
        }else
            return false;
    }

    private boolean hasDetailsChanged() {
        if( hasGenderChanged()||hasMobileNumChanged()||hasNameChanged())
            return true;
        else
            return false;
    }

    private boolean hasNameChanged() {
        String newName = this.getManageUserfragment().getName();
        if( !newName.equals(LoginDetails.getInstance().getName()))
            return true;
        else
            return false;
    }

    private boolean hasGenderChanged() {
        String newGender = this.getManageUserfragment().getNewGender();
        if(!newGender.equalsIgnoreCase(LoginDetails.getInstance().getGender())){
            return true;
        }else
            return false;
    }

    private void saveData() {
        this.getManageUserModel().saveData(this.getModifiedDataMap());
    }

    public HashMap<String, String> getModifiedDataMap() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(MessagesString.USER_ID, LoginDetails.getInstance().getUserid());
        if (this.hasMobileNumChanged()){
            params.put(MessagesString.MOBILENUM, (this.getManageUserfragment().getMobileNum()==null ||
                    "".equalsIgnoreCase(this.getManageUserfragment().getMobileNum())) ?"0":manageUserfragment.getMobileNum());
        }
        if (hasGenderChanged()){
            params.put(MessagesString.GENDER,this.getManageUserfragment().getNewGender());
        }
        if (hasNameChanged()){
            params.put(MessagesString.NAME,this.getManageUserfragment().getName());
        }
        params.put(MessagesString.INSTRUCTION, "4");
        return params;
    }

    public boolean validateInput() {
        if (!(isValidMobileNum())){
            this.getManageUserfragment().setErrorMsgOnName(MessagesString.ENTER_VALID_NAME);
            return false;
        }
        if (!(isValidName())) {
            this.getManageUserfragment().setErrorMsgOnPhone(MessagesString.ENTER_VALID_MOBILENUM);
            return false;
        }
        return true;
    }

    private boolean isValidName() {
        if (this.getManageUserfragment().getName() ==null ||
                "".equalsIgnoreCase(this.getManageUserfragment().getName()))
        {
            return false;
        }
        return true;
    }

    private boolean isValidMobileNum() {
        if (this.getManageUserfragment().getMobileNum() !=null &&
                !("".equalsIgnoreCase(this.getManageUserfragment().getMobileNum())) &&
                !(CommonResources.isValidMobile(this.getManageUserfragment().getMobileNum()) ) )
        {
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(JSONObject json) {
        LoginDetails.getInstance().saveLoginDetailsToDevice(this.getContext(),json);
        CommonUtil.flash(this.getContext(), MessagesString.DATA_SAVED);
    }

    @Override
    public void onFailure(String errorMessage) {
        CommonUtil.flash(this.getContext(), errorMessage);
    }

    public void onLogoutClicked() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                new CommonResources(getContext()).logoutUserFromAllAccounts();
            }
        });
        t.start();
        manageUserfragment.navigateToLogin();
    }

    public void readAndSetUserDetails() {
        String email = LoginDetails.getInstance().getEmail();
        this.getManageUserfragment().setEmail(email);
        String name = LoginDetails.getInstance().getName();
        this.getManageUserfragment().setName(name);
        String phoneNum = LoginDetails.getInstance().getMobilenum();
        if(phoneNum!=null)
            this.getManageUserfragment().setPhoneNum(phoneNum);
        String gender = LoginDetails.getInstance().getGender();
        if(gender!=null)
        {
            this.getManageUserfragment().setGender(gender);
        }
        String mobileNum=LoginDetails.getInstance().getMobilenum();
        if (mobileNum !=null && !"".equalsIgnoreCase(mobileNum)){
            this.getManageUserfragment().setBtVerifyVisibility(View.VISIBLE);
            Boolean isVerified =LoginDetails.getInstance().getMob_verified().equals(MessagesString.MOB_VERIFIED_IS_FALSE)?false:true;
            this.getManageUserfragment().setVerifyImage(isVerified);
        }else{
            this.getManageUserfragment().setBtVerifyVisibility(View.GONE);
        }
    }

    public void onMobileNumTextChanged(CharSequence s) {
        String currentNum = s == null ? null : s.toString();
        if (currentNum != null) {
            if (CommonResources.isValidMobile(currentNum) && !currentNum.equalsIgnoreCase(LoginDetails.getInstance().getMobilenum())) {
                setBtVerifyVisibleAndImageSearch();
            } else if (CommonResources.isValidMobile(currentNum) && currentNum.equalsIgnoreCase(LoginDetails.getInstance().getMobilenum())) {
                if (LoginDetails.getInstance().getMob_verified().equals(MessagesString.MOB_VERIFIED_IS_FALSE)) {
                    setBtVerifyVisibleAndImageSearch();
                } else {
                    setBtVerifyVisibleAndImageHome();
                }
            } else {
                this.getManageUserfragment().setBtVerifyVisibility(View.GONE);
            }
        }
    }

    private void setBtVerifyVisibleAndImageHome() {
        setBtVerifyImage(true);
        this.getManageUserfragment().setBtVerifyClickable(false);
    }

    private void setBtVerifyImage(boolean isVerified) {
        this.getManageUserfragment().setBtVerifyVisibility(View.VISIBLE);
        this.getManageUserfragment().setVerifyImage(isVerified);
    }

    private void setBtVerifyVisibleAndImageSearch() {
        setBtVerifyImage(false);
        this.getManageUserfragment().setBtVerifyClickable(true);
    }

    public void onVerifyButtonClicked() {
        String newPhoneEntered = this.getManageUserfragment().getMobileNum();
        if(newPhoneEntered !=null && CommonResources.isValidMobile(newPhoneEntered)
                && !(newPhoneEntered.equalsIgnoreCase(LoginDetails.getInstance().getMobilenum())
                && LoginDetails.getInstance().getMob_verified().equals(MessagesString.MOB_VERIFIED_IS_TRUE) ))
        {
            LoginDetails.getInstance().setUpForNewOtpRequest();
            LoginDetails.getInstance().setMobilenum(newPhoneEntered);
            this.getManageUserModel().saveMobileNumOnDevice();
            this.getManageUserfragment().navigateToOTPVerification();
        }else {
            this.getManageUserfragment().setPhoneNum(LoginDetails.getInstance().getMobilenum());
        }
    }

    public void OnChangePwdClicked() {
        this.getManageUserfragment().navigateToChangePwd();
    }
}
