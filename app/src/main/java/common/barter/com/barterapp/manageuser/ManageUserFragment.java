package common.barter.com.barterapp.manageuser;

/**
 * Created by amitpa on 8/18/2015.
 */
import android.app.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import common.barter.com.barterapp.Login.LoginMode;
import common.barter.com.barterapp.Login.LoginParentFragment;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.changepassword.ChangePassword;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.otp.OTPFragment;

public class ManageUserFragment extends Fragment {

    Activity context;
    private EditText etemail;
    private TextView btchangePwd;
    private EditText etMobileNum;
    private ImageButton btverify;
    private EditText etname;
    private RadioButton rbmale;
    private RadioButton rbfemale;
    private EditText etOTP;
    private Button btSave;
    private ImageView ivVerified;

    private ManageUserPresenter manageUserPresenter;

    public String getName() {
        return etname.getText().toString();
    }

    public String getMobileNum() {
        return etMobileNum.getText().toString();
    }

    public String getNewGender() {
        return rbmale.isChecked() ? "M" : "F";
    }

    public void setEmail(String email) {
        etemail.setText(email);
    }

    public void setName(String name) {
        etname.setText(name);
    }

    public void setGender(String gender) {
        if (gender.equalsIgnoreCase("M")){
            rbmale.setChecked(true);
        }
        if (gender.equalsIgnoreCase("F")){
            rbfemale.setChecked(false);
        }

    }
    public void setPhoneNum(String phoneNum) {
        etMobileNum.setText(phoneNum);
    }

    public int getBtVerifyVisibility() {
        return btverify.getVisibility();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.findItem(R.id.action_search) != null)
            menu.findItem(R.id.action_search).setVisible(false);
        menu.clear();
        inflater.inflate(R.menu.menu_test_db, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            manageUserPresenter.onLogoutClicked();
        }
        return false;
    }

    public ManageUserFragment(){

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.manage_user, container, false);
        setHasOptionsMenu(true);
        initializeWidgets(rootView);
        setPresenter();
        manageUserPresenter.readAndSetUserDetails();
        validateAndSetChangePwdVisibility();
        setListeners();
        return rootView;
    }

    private void setListeners() {
        etMobileNum.addTextChangedListener(manageUserPresenter.getManageUserListener().getMobileNumTextWatcher());
        btverify.setOnClickListener(manageUserPresenter.getManageUserListener().getVerifyButtonOnClickListener());
        btchangePwd.setOnClickListener(manageUserPresenter.getManageUserListener().getChangePwdOnClickListener());
        btSave.setOnClickListener(manageUserPresenter.getManageUserListener().getSaveOnClickListener());
    }

    private void setPresenter() {
        if (manageUserPresenter==null){
            manageUserPresenter=new ManageUserPresenter();
        }
        manageUserPresenter.setManageUserfragment(this);
    }

    private void initializeWidgets(View rootView) {
        etemail = (EditText)rootView.findViewById(R.id.etEmailIdlogin);
        etMobileNum = (EditText)rootView.findViewById(R.id.etMobileNum);
        etname = (EditText)rootView.findViewById(R.id.etName);
        btchangePwd = (TextView)rootView.findViewById(R.id.btChangePwd);
        btverify = (ImageButton)rootView.findViewById(R.id.btVerifyManageUser);
        rbmale = (RadioButton)rootView.findViewById(R.id.rbMale);
        rbfemale = (RadioButton)rootView.findViewById(R.id.rbFemale);
        btSave = (Button)rootView.findViewById(R.id.btSaveManageUser);
        ivVerified= (ImageView)rootView.findViewById(R.id.ivVerified);
    }

    private void validateAndSetChangePwdVisibility() {
        if(LoginMode.MANUALLOGIN.toString().equalsIgnoreCase(LoginDetails.getInstance().getLoginMethod()))
            btchangePwd.setVisibility(View.VISIBLE);
        else
            btchangePwd.setVisibility(View.GONE);
    }

    public void setVerifyImage(boolean isVerified){
        if(isVerified)
            btverify.setImageResource(R.drawable.home);
        else
            btverify.setImageResource(R.drawable.search);
    }
    public void setBtVerifyClickable(boolean isVerified){
        btverify.setClickable(isVerified);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }

    @Override
    public void onResume() {
        super.onResume();
        manageUserPresenter.readAndSetUserDetails();
    }
    public void setErrorMsgOnName(Object enterValidName) {
        etname.setError((CharSequence) enterValidName);
    }

    public void setErrorMsgOnPhone(Object enterValidMobilenum) {
        etMobileNum.setError((CharSequence) enterValidMobilenum);
    }

    void navigateToLogin() {
        Fragment fragment = new LoginParentFragment();
        FragmentManager fragmentManager = ((GlobalHome)getActivity()).getSupportFragmentManager();
        FragmentTransaction ft  = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out, R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
        ft.replace(R.id.frame_container, fragment).commit();
    }

    public void setBtVerifyVisibility(int visible) {
        btverify.setVisibility(visible);
    }

    public void navigateToOTPVerification() {
        Fragment fragment = new OTPFragment();
        FragmentManager fragmentManager = ((GlobalHome)this.getActivity()).getSupportFragmentManager();
        FragmentTransaction ft  = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out,R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
        ft.add(R.id.frame_container, fragment).addToBackStack("otp").commit();
//        OTPVerificationAdapter otpVerificationAdapter = new OTPVerificationAdapter(getActivity(),ManageUserFragment.this);
//        otpVerificationAdapter.generateOTP();
    }

    public void navigateToChangePwd() {
        Fragment fragment = new ChangePassword();
        FragmentManager fragmentManager = ((GlobalHome)manageUserPresenter.getManageUserfragment().getActivity()).getSupportFragmentManager();
        FragmentTransaction ft  = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_bottom,R.anim.abc_fade_out,R.anim.abc_slide_in_bottom,R.anim.abc_fade_out);
        ft.add(R.id.frame_container, fragment).addToBackStack("change_pwd").commit();
    }
}