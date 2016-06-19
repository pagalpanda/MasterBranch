package common.barter.com.barterapp.otp;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import common.barter.com.barterapp.CommonUtil;
import common.barter.com.barterapp.IncomingSms;
import common.barter.com.barterapp.LoginDetails;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.manageuser.ManageUserFragment;

/**
 * Created by vikram on 20/09/15.
 */
public class OTPFragment extends Fragment {

    private Activity context;
    private Boolean otpVerified = false;

    private Button btverify;
    private TextView tvmobilenum;
    private EditText etotp;
    private EditText etstatus;
    private ProgressBar pbstatus;
    private TextView tvResendCode;
    GlobalHome activity;
    private OTPPresenter otpPresenter;

    public Boolean getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(Boolean otpVerified) {
        this.otpVerified = otpVerified;
    }

    public String getEnteredOTP() {
        return etotp.getText().toString();
    }

    public void setEnteredOTP(String otp) {
        etotp.setText(otp);
    }

    public void activateVerifyButton(boolean b) {
        btverify.setActivated(b);
        if (b){
            btverify.setText("VERIFY");
        }else {
            btverify.setText("VERIFIED");
        }
    }

    public void setHeading(String s) {
        tvmobilenum.setText(s);
    }

    public void setTextOnVerifyButton(String s) {
        btverify.setText(s);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View dialogFragmentView = inflater.inflate(R.layout.otp_popup, container, false);
        setHasOptionsMenu(true);
        initializeWidgets(dialogFragmentView);
        setPresenter();
        otpPresenter.setinputData();
        otpPresenter.generateOTP();
        btverify.setOnClickListener(otpPresenter.getOtpListener().getVerifyButtonOnClickListener());
        tvResendCode.setOnClickListener(otpPresenter.getOtpListener().getResendButtonOnClickListener());
        return dialogFragmentView;
    }

    private void setPresenter() {
        if(otpPresenter==null){
            otpPresenter=new OTPPresenter();
        }
        otpPresenter.setOtpFragment(this);
    }

    private void initializeWidgets(View dialogFragmentView) {
        activity = (GlobalHome) getActivity();
        activity.getmDrawerToggle().setDrawerIndicatorEnabled(false);
        activity.setActionBarTitle(MessagesString.HEADER_VERIFY_OTP);
        etotp = (EditText) dialogFragmentView.findViewById(R.id.etotp);
        tvmobilenum = (TextView) dialogFragmentView.findViewById(R.id.tvmobilenum);
        etstatus = (EditText) dialogFragmentView.findViewById(R.id.etstatus);
        pbstatus = (ProgressBar) dialogFragmentView.findViewById(R.id.pbstatus);
        btverify = (Button) dialogFragmentView.findViewById(R.id.btContinue);
        tvResendCode= (TextView) dialogFragmentView.findViewById(R.id.tvResendCode);
        tvmobilenum.setText(MessagesString.OTP_NUMBER_MESSAGE + LoginDetails.getInstance().getMobilenum());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.findItem(R.id.action_logout) != null) {
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.removeItem(R.id.action_logout);
        }
        menu.clear();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    void setLayoutForNewRequest() {
        pbstatus.setProgress(0);
        etstatus.setText("Regenerate OTP.");
        btverify.setActivated(true);
        btverify.setText("VERIFY");
        btverify.setBackgroundColor(getResources().getColor(R.color.green));
    }

    void setLayoutForInProgress() {
        pbstatus.setProgress(25);
        etstatus.setText("Waiting for OTP.");
        btverify.setActivated(false);
        btverify.setBackgroundColor(getResources().getColor(R.color.gray));
    }

    public void setProgress(int progress,String status) {
        pbstatus.setProgress(progress);
        etstatus.setText(status);

    }

    void navigateToManageUser() {
        activity.getmDrawerToggle().setDrawerIndicatorEnabled(true);
        activity.setActionBarTitle(MessagesString.HEADER_MY_ACCOUNT);
            Fragment fragment = new ManageUserFragment();
            FragmentManager fragmentManager = ((GlobalHome)getActivity()).getSupportFragmentManager();
            FragmentTransaction ft  = fragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out, R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
            ft.replace(R.id.frame_container, fragment).commit();

    }
    public void setDialogFragment()
    {
        Fragment fragment = new OTPFragment();
        FragmentManager fragmentManager = ((GlobalHome)activity).getSupportFragmentManager();
        FragmentTransaction ft  = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out, R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
        ft.add(R.id.frame_container, fragment).addToBackStack("otp_verify").commit();
    }



}
