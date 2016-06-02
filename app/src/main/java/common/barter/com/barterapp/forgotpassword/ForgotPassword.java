package common.barter.com.barterapp.forgotpassword;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import common.barter.com.barterapp.AbstractFragment;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;

/**
 * Created by vikram on 29/09/15.
 */
public class ForgotPassword extends AbstractFragment {

    private Button btnSendPwd;
    private EditText etEmailOrMob;
    GlobalHome holdingActivity;
    ForgotPasswordPresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = getInflatedView(inflater, container);
        initializeWidgets(view);
        this.holdingActivity = (GlobalHome) getActivity();

        if(presenter == null) {
            presenter = new ForgotPasswordPresenter();
        }
        presenter.onLoadView(this);

        btnSendPwd.setOnClickListener(btnSendPasswordOnClick);

        return view;

    }


    public ForgotPassword(){
        super();
    }

    private View getInflatedView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.forgot_password, container, false);
    }

    View.OnClickListener btnSendPasswordOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String emailId = etEmailOrMob.getText().toString();
            presenter.onSendPassword(emailId);
        }
    };



    private void initializeWidgets(View view) {
        etEmailOrMob = (EditText) view.findViewById(R.id.etCurrentPwd);
        btnSendPwd = (Button) view.findViewById(R.id.btSendPwd);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void navigateToLogin() {
        holdingActivity.getSupportFragmentManager().popBackStack();
        setHamburgerIndicator(true);
        setActionBarTitle(MessagesString.HEADER_MY_ACCOUNT);

    }


    @Override
    public void navigate() {
        navigateToLogin();
    }
}