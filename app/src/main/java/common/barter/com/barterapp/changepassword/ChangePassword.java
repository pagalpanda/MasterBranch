package common.barter.com.barterapp.changepassword;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import common.barter.com.barterapp.AbstractFragment;
import common.barter.com.barterapp.globalhome.GlobalHome;
import common.barter.com.barterapp.MessagesString;
import common.barter.com.barterapp.R;


/**
 * Created by vikram on 02/10/15.
 */
public class ChangePassword extends AbstractFragment {

    private Activity context;
    private Button btSavePwd;
    private EditText etCurrentPwd;
    private EditText etNewPwd;
    private EditText etConfirmPwd;
    private GlobalHome activity;
    private ChangePasswordPresenter presenter;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.findItem(R.id.action_logout) != null) {
            menu.findItem(R.id.action_logout).setVisible(false);
            menu.removeItem(R.id.action_logout);

        }
        menu.clear();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ChangePasswordView = inflater.inflate(R.layout.change_password, container, false);
        initializeWidgets(ChangePasswordView);
        activity = (GlobalHome) getActivity();

        if(presenter == null) {
            presenter = new ChangePasswordPresenter();
        }

        presenter.onLoadView(this);

        btSavePwd.setOnClickListener(savePasswordClickListener);
        return ChangePasswordView;

    }

    View.OnClickListener savePasswordClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String existingPassword = etCurrentPwd.getText().toString();
            String newPassord = etNewPwd.getText().toString();
            String confirmNewPassword = etConfirmPwd.getText().toString();
            presenter.onSavePasswordClick(existingPassword,newPassord,confirmNewPassword);
        }
    };

    private void initializeWidgets(View changePasswordView) {
        etCurrentPwd = (EditText) changePasswordView.findViewById(R.id.etCurrentPwd);
        etNewPwd = (EditText) changePasswordView.findViewById(R.id.etNewPwd);
        etConfirmPwd = (EditText) changePasswordView.findViewById(R.id.etConfirmPwd);

        btSavePwd = (Button) changePasswordView.findViewById(R.id.btSavePwd);
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

    private void navigateToManageUser() {
        getActivity().getSupportFragmentManager().popBackStack();
        activity.getmDrawerToggle().setDrawerIndicatorEnabled(true);
        activity.setActionBarTitle(MessagesString.HEADER_MY_ACCOUNT);

    }
    @Override
    public void navigate() {
        navigateToManageUser();
    }
}